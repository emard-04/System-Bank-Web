package Interfaces;

import Entidades.TelefonoxPersona;


public interface inTelefono {
	public TelefonoxPersona buscarXTelefono(String telefono);
	public boolean Modificar(String oldTelefono,TelefonoxPersona telefono);
	public boolean Agregar(TelefonoxPersona telefono);
	public boolean Eliminar(TelefonoxPersona telefono);
}
