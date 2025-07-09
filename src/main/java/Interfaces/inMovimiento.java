package Interfaces;

import java.time.LocalDate;
import java.util.ArrayList;

import Entidades.Movimiento;
import java.util.List;
public interface inMovimiento {
boolean Agregar(Movimiento mov);
 ArrayList<Movimiento> Listarxcuentas(Movimiento mov);
 ArrayList<Movimiento> Filtrar(int idUsuario, int cuentaEmisor, String nombreUsuario, String condicionesExtras, ArrayList<Object> parametrosExtras);
 boolean EliminarMovimientos(int idUsuario);
 boolean EliminarxCuenta(int idCuenta);
 int contarMovimientos(Movimiento mov) ;
 List<Movimiento> ListarxcuentasPaginado(Movimiento mov, int offset, int limite);
}
