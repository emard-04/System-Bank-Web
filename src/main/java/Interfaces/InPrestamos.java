package Interfaces;
import Entidades.*;

import java.sql.Connection;
import java.util.List; 
public interface InPrestamos {
	boolean agregar(Prestamos p);
    List<Prestamos> obtenerTodos(); 
    List<Prestamos> obtenerPorUsuario(int idUsuario); 
    boolean cambiarEstadoPago(int idPrestamo, String nuevoEstadoPago,Connection conn);
    boolean cambiarEstadoPago(int idPrestamo, String nuevoEstadoPago);
    public Prestamos obtenerPendientePorUsuario(int idUsuario) ;
 
    boolean cambiarEstadoSolicitado(int idPrestamo, String nuevoEstado, Connection conn);
    List<Prestamos> obtenerPrestamosPendientes();
    List<Prestamos> obtenerPrestamosPendientesPorDni(String dni);
     int cantidadPrestamosActivosPorCuenta(int idCuenta);
     int insertarYObtenerId(Prestamos p);
     int contarPrestamosPendientes();
     int contarPrestamosPendientesPorDni(String dni);
     List<Prestamos> obtenerPrestamosPendientesPaginado(int pagina, int prestamosPorPagina);
     List<Prestamos> obtenerPrestamosPendientesPorDniPaginado(String dni, int pagina, int prestamosPorPagina);
     Prestamos obtenerPorId(int idPrestamo, Connection conn);
     int contarCuotasPendientesPorPrestamo(int idPrestamo);
     int obtenerIdPrestamoPorCuota(int idCuota);
     boolean EliminarxUsuario(int id);
     List<Prestamos> obtenerPrestamosPorFechaYEstado(java.util.Date desde, java.util.Date hasta, String estado);
     boolean EliminarxCuenta(int nrocuenta);
     int BuscarxCuenta(int nrocuenta);
}
