package Interfaces;

import java.util.ArrayList;

import Entidades.Pais;

public interface inPais {
	 Pais buscarXNombre(String nombre);
	 Pais buscarXID(int id);
	 ArrayList<Pais> listarTodo();
}
