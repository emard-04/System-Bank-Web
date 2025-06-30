package Interfaces;
import Entidades.*;
import java.util.List; 
public interface InPrestamos {
	boolean agregar(Prestamos p);
    List<Prestamos> obtenerTodos(); // para el admin
    List<Prestamos> obtenerPorUsuario(int idUsuario); // si querés mostrar historial al cliente
    boolean cambiarEstadoPago(int idPrestamo, String nuevoEstadoPago);
    public Prestamos obtenerPendientePorUsuario(int idUsuario) ;
    boolean cambiarEstadoSolicitado(int idPrestamo, String nuevoEstadoSoli);
    
}
