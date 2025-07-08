package negocio;

import java.math.BigDecimal;

import java.sql.Connection;
import java.util.Date;
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
        boolean EliminarCuentas(int idUsuario);
        ArrayList<Cuenta> filtrar(String dniParcial, int idTipoCuenta, String ordenSaldo);
		List<Cuenta> obtenerCuentasCreadasEnRango(Date desde, Date hasta);
		String obtenerTipoCuentaMasCreadaEnRango(Date desde, Date hasta);
		BigDecimal obtenerPromedioSaldoInicialEnRango(Date desde, Date hasta);
		int contarCuentasCreadasEnRango(Date desde, Date hasta);
        

}

