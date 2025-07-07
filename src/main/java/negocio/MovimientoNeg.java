package negocio;

import java.time.LocalDate;
import java.util.ArrayList;

import Entidades.Cuenta;
import Entidades.Movimiento;

public interface MovimientoNeg {
boolean Agregar(Movimiento movReceptor, Movimiento movEmisor);
ArrayList<Movimiento> Listarxcuentas(Movimiento mov);
ArrayList<Movimiento> filtrar(Movimiento mov,String nombre, String operador, LocalDate desde, LocalDate hasta);
boolean movimientoPagarCuota(Movimiento mov);
}
