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
    List<Prestamos> obtenerPrestamosPendientes();
    List<Prestamos> obtenerPrestamosPendientesPorDni(String dni);
     int cantidadPrestamosActivosPorCuenta(int idCuenta);
     int insertarYObtenerId(Prestamos p);
     int contarPrestamosPendientes();
     int contarPrestamosPendientesPorDni(String dni);
     List<Prestamos> obtenerPrestamosPendientesPaginado(int pagina, int prestamosPorPagina);
     List<Prestamos> obtenerPrestamosPendientesPorDniPaginado(String dni, int pagina, int prestamosPorPagina);
}
