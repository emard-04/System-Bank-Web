package negocio;
import Entidades.*;
public interface UsuarioNeg {
	 boolean AgregarConPersona(Usuario usuario);
	    boolean existeDni(String dni);
	    Usuario Login(String nombreUsuario, String contrasena); 
}
