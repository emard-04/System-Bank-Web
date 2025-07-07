package negocio;

import java.util.ArrayList;

import Entidades.TipoMovimiento;

public interface TipoMovimientoNeg {
public TipoMovimiento buscarXDescripcion(String Descripcion);
ArrayList<TipoMovimiento> listarTodo();
}

