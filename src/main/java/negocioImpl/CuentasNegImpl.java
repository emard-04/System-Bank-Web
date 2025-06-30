package negocioImpl;

import Interfaces.inCuentas;
import negocio.CuentasNeg;
import java.util.ArrayList;
import Daos.*;
import Entidades.Cuenta;

public class CuentasNegImpl implements CuentasNeg{
	private inCuentas dao = new daoCuentas();
    @Override
    public boolean Agregar(Cuenta cuenta) {
    	PersonaNegImpl negPersona= new PersonaNegImpl();
    	if(negPersona.existe(cuenta.getUsuario().getPersona().getDni())) {
    		if(dao.maximoCuentas(cuenta.getUsuario().getIdUsuario())<3)
        return dao.Agregar(cuenta);
        }
    	return false;
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
}

