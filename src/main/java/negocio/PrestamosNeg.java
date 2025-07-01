package negocio;
import Entidades.*;
import java.util.List; 

public interface PrestamosNeg {
	boolean agregarPrestamo(Prestamos p);
    List<Prestamos> listarPrestamosCliente(int idUsuario);
    List<Prestamos> listarTodos();
    boolean aprobarPrestamo(int idPrestamo);
    boolean rechazarPrestamo(int idPrestamo);
    public Prestamos obtenerPrestamoPendientePorUsuario(int idUsuario);
    List<Prestamos> obtenerPrestamosPendientes();
     List<Prestamos> obtenerPrestamosPendientesPorDni(String dni);
     boolean puedePedirPrestamo(int idCuenta);
}
