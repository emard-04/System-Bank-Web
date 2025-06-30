package negocio;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cuenta;

public interface CuentasNeg {
        boolean Agregar(Cuenta cuenta);
        boolean Modificar(Cuenta cuenta);
        boolean Eliminar(int nroCuenta);
        ArrayList<Cuenta> ListarTodo();
        Cuenta BuscarPorNro(int nroCuenta);
        boolean existe(int nroCuenta);
        String generarCBU();
<<<<<<< Updated upstream
        Cuenta cuentaxCbu(String cbu);
        ArrayList<Cuenta> ListarxUsuario(int Id);
=======
       List<Cuenta> obtenerCuentasPorUsuario(int idUsuario);
        
>>>>>>> Stashed changes
}

