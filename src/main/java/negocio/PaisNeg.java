package negocio;

import java.util.ArrayList;
import java.util.List;

import Entidades.Pais;
import Entidades.Provincia;

public interface PaisNeg {
	Pais buscarXNombre(String nombre);
	Pais buscarXID(int id);
	ArrayList<Pais> listarTodo();
	
}
