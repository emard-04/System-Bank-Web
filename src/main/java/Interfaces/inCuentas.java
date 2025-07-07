package Interfaces;

import Entidades.*;

import java.util.List;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface inCuentas {

	boolean Agregar(Cuenta cuenta);

	boolean Modificar(Cuenta cuenta);

	boolean Eliminar(int nroCuenta);

	ArrayList<Cuenta> ListarTodo();

	Cuenta BuscarPorNro(int nroCuenta);

	int obtenerIdCuenta();

	boolean existe(int nroCuenta);

	String generarCBUAleatorio();

	int maximoCuentas(int Id);

	Cuenta cuentaxCbu(String cbu);

	ArrayList<Cuenta> ListarxUsuario(int Id);

	boolean actualizarSaldo(int nroCuenta, BigDecimal monto, Connection conn);

	int obtenerNroCuentaPorIdUsuario(int idUsuario, Connection conn) throws SQLException;

	public ArrayList<Cuenta> filtrar(String condicionesExtras, String orden, ArrayList<Object> parametrosExtras);

	 int contarCuentasCreadasEnRango(Date desde, Date hasta);
	    BigDecimal obtenerPromedioSaldoInicialEnRango(Date desde, Date hasta);
	    String obtenerTipoCuentaMasCreadaEnRango(Date desde, Date hasta);
	    List<Cuenta> obtenerCuentasCreadasEnRango(Date desde, Date hasta);
}
