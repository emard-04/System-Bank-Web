package negocioImpl;

import negocio.*;
import Interfaces.*;

import java.util.ArrayList;
import java.util.List;

import Daos.*;
import Entidades.*;

public class UsuarioNegImpl implements UsuarioNeg {

	private InUsuario daoUsuario = new daoUsuario();
	private PersonaNegImpl negPersona;
	private TelefonoNeg negTelefono;
	private CuentasNeg negCuenta;
	private MovimientoNeg negMovimiento;
	private PrestamosNeg negPrestamos;

	@Override
	public boolean AgregarUsuario(Usuario usuario, Persona persona, List<TelefonoxPersona> listaTelefonos) {
		negPersona = new PersonaNegImpl();
		negTelefono = new TelefonoNegImpl();
		// Validaciones de persona
		if (negPersona.verificarMail(persona.getCorreoElectronico()))
			return false;
		if (negPersona.existe(persona.getDni()))
			return false;
		// Validacion usuario
		if (daoUsuario.existe(usuario.getNombreUsuario()))
			return false;
		// Validacion telefono
		for (TelefonoxPersona telefono : listaTelefonos) {
			if (telefono.getTelefono() != null) {
				if (negTelefono.existe(telefono.getTelefono())) {
					return false;
				}
			} else
				break;
		}
		if (!negPersona.Agregar(persona))
			return false;
		if (!daoUsuario.Agregar(usuario))
			return false;
		for (TelefonoxPersona telefonos : listaTelefonos) {
			if (!negTelefono.Agregar(telefonos))
				return false;
		}
		return true;
	}

	public boolean Existe(String nombreUsuaario) {
		return daoUsuario.existe(nombreUsuaario);
	}

	public Usuario BuscarDni(String dni) {
		return daoUsuario.BuscarDni(dni);
	}

	@Override
	public Usuario Login(String nombreUsuario, String contrasena) {
		return daoUsuario.Login(nombreUsuario, contrasena);
	}

	@Override
	public ArrayList<Usuario> listarTodo() {
		return daoUsuario.ListarTodo();
	}

	public boolean Modificar(Usuario usuario) {
		Usuario us = new Usuario();
		boolean existe = false;
		us = daoUsuario.BuscarDni(usuario.getPersona().getDni());
		if (!us.getNombreUsuario().equals(usuario.getNombreUsuario())) {
			existe = daoUsuario.existe(usuario.getNombreUsuario());
		}
		if (existe)
			return false;
		return daoUsuario.Modificar(usuario);
	}

	@Override
	public boolean Eliminar(String dni) {
		negPersona = new PersonaNegImpl();
		negCuenta = new CuentasNegImpl();
		negTelefono = new TelefonoNegImpl();
		Usuario us = BuscarDni(dni);

		for (Cuenta c : negCuenta.ListarxUsuario(us.getIdUsuario())) {
			System.out.println(" Esto se deberia repetir varias veces");	
			negCuenta.Eliminar(c.getNroCuenta());
		}

		System.out.println("4 Eliminar telefono");
		if (!negTelefono.EliminarxDni(dni)) {
			System.out.println("4404 error telefono");
			return false;
		}
		
		System.out.println("5 Eliminar persona");
		if (!negPersona.Eliminar(dni)) {
			System.out.println("5404 error persona");
			return false;
		}

		System.out.println("6 Eliminar usuario");
		return daoUsuario.Eliminar(dni);
	}

	public ArrayList<Usuario> filtrar(String dniParcial, int idProvincia, int idLocalidad, int idPais) {
		StringBuilder condicionesExtras = new StringBuilder();
		ArrayList<Object> parametrosExtras = new ArrayList<>();
		if (idPais > 0) {
			condicionesExtras.append(" and pais.idPais = ? ");
			parametrosExtras.add(idPais);
		}
		// Agregar condición por provincia
		if (idProvincia > 0) {
			condicionesExtras.append(" AND provincia.idProvincia = ? ");
			parametrosExtras.add(idProvincia);
		}

		// Agregar condición por localidad
		if (idLocalidad > 0) {
			condicionesExtras.append(" AND localidad.idLocalidad = ? ");
			parametrosExtras.add(idLocalidad);
		}

		// Agregar condición por DNI (si viene algo escrito)
		if (dniParcial != null && !dniParcial.trim().isEmpty()) {
			condicionesExtras.append(" AND usuarios.dni LIKE ? ");
			parametrosExtras.add("%" + dniParcial + "%");
		}
		return daoUsuario.filtrar(condicionesExtras.toString(), parametrosExtras);
	}

	public Usuario buscarId(int id) {
		return daoUsuario.BuscarIdusuario(id);
	}
}
