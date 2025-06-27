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
        if (!negPersona.existe(persona.getDni()))return false;
        if(daoUsuario.existe(usuario.getNombreUsuario())) return false;
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
    	Usuario us=new Usuario();
    	boolean existe= false;
    	us=daoUsuario.BuscarDni(usuario.getPersona().getDni());
    	if(!us.getNombreUsuario().equals(usuario.getNombreUsuario())) {
    		existe=daoUsuario.existe(usuario.getNombreUsuario());
    	}
    	if(existe)return false;
    	return daoUsuario.Modificar(usuario);
    }
    @Override
    public boolean Eliminar(String nombreUsuario) {
        return daoUsuario.Eliminar(nombreUsuario);
    }
}
