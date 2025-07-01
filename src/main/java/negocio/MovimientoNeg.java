package negocio;

import java.util.ArrayList;

import Entidades.Movimiento;

public interface MovimientoNeg {
boolean Agregar(Movimiento movReceptor, Movimiento movEmisor);
ArrayList<Movimiento> Listarxcuentas(Movimiento mov);
}
