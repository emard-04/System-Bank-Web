package negocio;

import java.util.ArrayList;

import Entidades.TelefonoxPersona;

public interface TelefonoNeg {
	public boolean Modificar(String oldTelefono,TelefonoxPersona telefono);
	public boolean Agregar(TelefonoxPersona telefono);
	public boolean Eliminar(TelefonoxPersona telefono);
	public ArrayList<TelefonoxPersona> listarTelefonos(String dni);
	public TelefonoxPersona buscarPorDni(String dni);
	public boolean existe(String telefono);
	boolean EliminarxDni(String dni);
}
