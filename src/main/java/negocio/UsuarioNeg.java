package negocio;
import java.util.ArrayList;

import Entidades.*;
public interface UsuarioNeg {
	 boolean AgregarUsuario(Usuario usuario);
	    Usuario Login(String nombreUsuario, String contrasena); 
	    public Usuario BuscarDni(String dni);
	    public ArrayList<Usuario> listarTodo();
	    public boolean Modificar(Usuario usuario);
}
