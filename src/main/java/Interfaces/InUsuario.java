package Interfaces;
import java.util.ArrayList;
import Entidades.*;
public interface InUsuario {
	boolean Agregar(Usuario usuario);
    boolean Eliminar(String nombreUsuario);
    boolean Modificar(Usuario usuario);
    ArrayList<Usuario> ListarTodo();
    boolean existe(String nombreUsuario);
    Usuario BuscarDni(String dni);
    Usuario BuscarIdusuario(int id);
    Usuario Login(String nombreUsuario, String contrasena);
    int obtenerIdUsuarioPorNombre(String nombreUsuario);
}
