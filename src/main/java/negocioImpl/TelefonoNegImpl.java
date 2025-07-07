package negocioImpl;

import java.util.ArrayList;

import Daos.daoTelefono;
import Entidades.TelefonoxPersona;
import negocio.TelefonoNeg;

public class TelefonoNegImpl implements TelefonoNeg{
private daoTelefono dTelefono=new daoTelefono();
	@Override
	public boolean Modificar(String oldTelefono, TelefonoxPersona telefono) {
		if(dTelefono.Modificar(oldTelefono, telefono))return true;
		return false;
	}

	@Override
	public boolean Agregar(TelefonoxPersona telefono) {
		if(dTelefono.existe(telefono.getTelefono())) return false;
		if(dTelefono.maximoTelefonos(telefono)>2)return false;
		if(dTelefono.Agregar(telefono))return true;
		return false;
	}
	public ArrayList<TelefonoxPersona> listarTelefonos(String dni){
		return dTelefono.listarTelefonos(dni);
	}
	@Override
	public boolean Eliminar(TelefonoxPersona telefono) {
		if(dTelefono.Eliminar(telefono))return true;
		return false;
	}

    public TelefonoxPersona buscarPorDni(String dni) {
        return dTelefono.buscarPorDni(dni);
    }
    public boolean existe(String telefono) {
    	return dTelefono.existe(telefono);
    }

}
