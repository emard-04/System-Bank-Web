package negocio;

import java.util.ArrayList;

import Entidades.Pais;

public interface PaisNeg {
	Pais buscarXNombre(String nombre);
	Pais buscarXID(int id);
	ArrayList<Pais> listarTodo();
}
