package negocioImpl;

import Daos.daoCuentas;
import Interfaces.inCuentas;
import negocio.CuentasNeg;
import java.util.ArrayList;
import Daos.*;
import Entidades.Cuenta;

public class CuentasNegImpl implements CuentasNeg{
	private inCuentas dao = new daoCuentas();

    @Override
    public boolean Agregar(Cuenta cuenta) {
        return dao.Agregar(cuenta);
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
}

