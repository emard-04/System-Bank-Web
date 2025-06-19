package Interfaces;

import java.util.ArrayList;

import Entidades.Persona;

public interface inPersona {
	boolean Agregar(Persona persona);
	boolean Modificar(Persona persona);
	boolean Eliminar(String dni);
	ArrayList<Persona> ListarTodo();
	boolean verificarMail(String mail);
	boolean existe(String dni);
	Persona existeObj(String dni);
}
