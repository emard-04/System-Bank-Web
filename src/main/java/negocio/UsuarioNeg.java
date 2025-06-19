package negocio;
import Entidades.*;
public interface UsuarioNeg {
	 boolean AgregarUsuario(Usuario usuario);
	    Usuario Login(String nombreUsuario, String contrasena); 
}
