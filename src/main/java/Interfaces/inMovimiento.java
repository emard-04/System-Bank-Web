package Interfaces;

import java.time.LocalDate;
import java.util.ArrayList;

import Entidades.Movimiento;

public interface inMovimiento {
boolean Agregar(Movimiento mov);
 ArrayList<Movimiento> Listarxcuentas(Movimiento mov);
 ArrayList<Movimiento> Filtrar(int idUsuario, int cuentaEmisor, String nombreUsuario, String condicionesExtras, ArrayList<Object> parametrosExtras);
 boolean EliminarMovimientos(int idUsuario);
}
