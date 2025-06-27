package negocioImpl;
import negocio.*;
import Interfaces.*;

import java.util.ArrayList;

import Daos.*;
import Entidades.*;
public class UsuarioNegImpl implements UsuarioNeg {

    private InUsuario daoUsuario = new daoUsuario();
    private PersonaNegImpl negPersona = new PersonaNegImpl();

    @Override
    public boolean AgregarUsuario(Usuario usuario) {
        Persona persona = usuario.getPersona();
        if (negPersona.existe(persona.getDni()))return false;
        if(negPersona.verificarMail(persona.getCorreoElectronico()))return false;
        if(daoUsuario.existe(usuario.getNombreUsuario())) return false;
        boolean exitoPersona=negPersona.Agregar(persona);
        if(!exitoPersona) {
        	System.out.println("No se agrego persona");
        	return false;
        }
        boolean exitoUsuario=daoUsuario.Agregar(usuario);
        if(!exitoUsuario) {
        	return false;
        }
        return true;
    }
    public boolean Existe(String nombreUsuaario) {
    	return daoUsuario.existe(nombreUsuaario);
    }
    public Usuario BuscarDni(String dni) {
    	return daoUsuario.BuscarDni(dni);
    }
    @Override
    public Usuario Login(String nombreUsuario, String contrasena) {
        return daoUsuario.Login(nombreUsuario, contrasena);
    }
    @Override
    public ArrayList<Usuario> listarTodo(){
    	return daoUsuario.ListarTodo();
    }
    public boolean Modificar(Usuario usuario) {
    	return daoUsuario.Modificar(usuario);
    }
    @Override
    public boolean Eliminar(String nombreUsuario) {
        return daoUsuario.Eliminar(nombreUsuario);
    }
}
