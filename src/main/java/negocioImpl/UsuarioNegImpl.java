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
		negPersona=new PersonaNegImpl();
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
			if (negTelefono.existe(telefono.getTelefono())) {
				return false;
			}
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
		negPersona=new PersonaNegImpl();
		negCuenta=new CuentasNegImpl();
		negTelefono=new TelefonoNegImpl();
		negPrestamos= new PrestamosNegImpl();
		negMovimiento=new MovimientoNegImpl();
		Usuario us=BuscarDni(dni);
		if(!negPrestamos.EliminarxUsuario(us.getIdUsuario())) return false;
		if(!negPersona.Eliminar(dni)) return false;
		if(!negCuenta.EliminarCuentas(us.getIdUsuario())) return false;
		if(!negMovimiento.EliminarMovimientos(us.getIdUsuario()))return false;
		if(!negTelefono.EliminarxDni(dni)) return false;
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
