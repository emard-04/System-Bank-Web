package Interfaces;

import java.util.ArrayList;

import Entidades.TelefonoxPersona;


public interface inTelefono {
	public TelefonoxPersona buscarXTelefono(String telefono);
	public boolean Modificar(String oldTelefono,TelefonoxPersona telefono);
	public boolean Agregar(TelefonoxPersona telefono);
	public boolean Eliminar(TelefonoxPersona telefono);
	public boolean existe(TelefonoxPersona telefono);
	public ArrayList<TelefonoxPersona> listarTelefonos(String dni);
	public TelefonoxPersona buscarPorDni(String dni);
}
