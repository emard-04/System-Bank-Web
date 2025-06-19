package negocio;

import java.util.ArrayList;

import Entidades.Cuenta;

public interface CuentasNeg {
        boolean Agregar(Cuenta cuenta);
        boolean Modificar(Cuenta cuenta);
        boolean Eliminar(int nroCuenta);
        ArrayList<Cuenta> ListarTodo();
        Cuenta BuscarPorNro(int nroCuenta);
        boolean existe(int nroCuenta);
        String generarCBU();
}

