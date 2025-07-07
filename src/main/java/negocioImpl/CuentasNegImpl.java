package negocioImpl;

import Interfaces.inCuentas;
import negocio.CuentasNeg;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import negocio.*;
import Daos.*;
import Entidades.Cuenta;

public class CuentasNegImpl implements CuentasNeg{
	private inCuentas dao = new daoCuentas();
	private UsuarioNeg nUsuario= new UsuarioNegImpl();
    @Override
    public boolean Agregar(Cuenta cuenta) {
    	PersonaNegImpl negPersona= new PersonaNegImpl();
    	//Perosna no existe dni return false
    	
    	if(!negPersona.existe(cuenta.getUsuario().getPersona().getDni())) return false;
    	//Usuario Administrador return false
    	if(nUsuario.BuscarDni(cuenta.getUsuario().getPersona().getDni()).isTipoUsuario())return false;
    	//persona tiene 3 cuentas return false
    	if(dao.maximoCuentas(cuenta.getUsuario().getIdUsuario())>=3)return false;
        return dao.Agregar(cuenta);
    }
    public ArrayList<Cuenta> ListarxUsuario(int Id){
    	return dao.ListarxUsuario(Id);
    }
    @Override
    public boolean Modificar(Cuenta cuenta) {
        return dao.Modificar(cuenta);
    }

    @Override
    public boolean Eliminar(int nroCuenta) {
        return dao.Eliminar(nroCuenta);
    }

    @Override
    public ArrayList<Cuenta> ListarTodo() {
        return dao.ListarTodo();
    }

    @Override
    public Cuenta BuscarPorNro(int nroCuenta) {
        return dao.BuscarPorNro(nroCuenta);
    }
    public int obtenerId() {
    	return dao.obtenerIdCuenta();
    }

    @Override
    public boolean existe(int nroCuenta) {
        return dao.existe(nroCuenta);
    }
	public String generarCBU() {
		return dao.generarCBUAleatorio();
	}
	public Cuenta cuentaxCbu(String cbu) {
		return dao.cuentaxCbu(cbu);
	}
	@Override
	public boolean actualizarSaldo(int nroCuenta, BigDecimal monto, Connection conn) {
		return dao.actualizarSaldo(nroCuenta, monto, conn);
	}
	@Override
	public int obtenerNroCuentaPorIdUsuario(int idUsuario, Connection conn) throws SQLException {
		return dao.obtenerNroCuentaPorIdUsuario(idUsuario, conn);
	}
	public ArrayList<Cuenta> filtrar(String dniParcial, int idTipoCuenta, String ordenSaldo){
		StringBuilder condicionesExtras = new StringBuilder();
		StringBuilder orden = new StringBuilder();
		ArrayList<Object> parametrosExtras = new ArrayList<>();

		if (dniParcial != null && !dniParcial.isEmpty()) {
		    condicionesExtras.append(" AND usuarios.dni LIKE ? ");
		    parametrosExtras.add("%" + dniParcial + "%");
		}

		if (idTipoCuenta > 0) {
		    condicionesExtras.append(" AND cuentas.IdtipoCuenta = ? ");
		    System.out.println(idTipoCuenta);
		    parametrosExtras.add(idTipoCuenta);
		}

		if (ordenSaldo != null && (ordenSaldo.equalsIgnoreCase("ASC") || ordenSaldo.equalsIgnoreCase("DESC"))) {
		    orden.append(" ORDER BY cuentas.Saldo ").append(ordenSaldo);
		} else {
		    orden.append(" ORDER BY cuentas.Saldo ASC"); // por defecto
		}
		return dao.filtrar(condicionesExtras.toString(), orden.toString(), parametrosExtras);
	}
		
}

