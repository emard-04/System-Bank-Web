package Interfaces;

import java.time.LocalDate;
import java.util.ArrayList;

import Entidades.Movimiento;

public interface inMovimiento {
boolean Agregar(Movimiento mov);
 ArrayList<Movimiento> Listarxcuentas(Movimiento mov);
 public ArrayList<Movimiento> filtrar(Movimiento mov,String nombre, String operador, LocalDate desde, LocalDate hasta);
}
