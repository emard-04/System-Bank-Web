package negocio;

import java.util.ArrayList;

import Entidades.Persona;

public interface ClientesNeg {
	boolean Agregar(Persona persona);
	boolean Modificar(Persona persona);
	boolean Eliminar(String dni);
	ArrayList<Persona> ListarTodo();
	boolean existe(String dni);
	Persona Buscardni(String dni);
}
