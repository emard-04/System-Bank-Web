package Interfaces;

import java.util.ArrayList;

import Entidades.TipoMovimiento;

public interface inTipoMovimiento {
	public TipoMovimiento buscarXDescripcion(String Descripcion);
	public TipoMovimiento buscarXID(int id);
	public ArrayList<TipoMovimiento> listarTodo();
	}

