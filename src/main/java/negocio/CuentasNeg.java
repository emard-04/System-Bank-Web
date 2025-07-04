package negocio;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
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
        Cuenta cuentaxCbu(String cbu);
        ArrayList<Cuenta> ListarxUsuario(int Id);
        boolean actualizarSaldo(int nroCuenta, BigDecimal monto, Connection conn);
        int obtenerNroCuentaPorIdUsuario(int idUsuario, Connection conn) throws SQLException;

}

