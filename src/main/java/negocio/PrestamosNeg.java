package negocio;
import Entidades.*;

import java.sql.Connection;
import java.util.List; 

public interface PrestamosNeg {
	//
    List<Prestamos> listarPrestamosCliente(int idUsuario);
    List<Prestamos> listarTodos();
    boolean aprobarPrestamo(int idPrestamo);
    boolean rechazarPrestamo(int idPrestamo);
    public Prestamos obtenerPrestamoPendientePorUsuario(int idUsuario);
    List<Prestamos> obtenerPrestamosPendientes();
     List<Prestamos> obtenerPrestamosPendientesPorDni(String dni);
     boolean puedePedirPrestamo(int idCuenta);
    int agregarPrestamo(Prestamos prestamo);
    int insertarYObtenerId(Prestamos p);
    int contarPrestamosPendientes();
    int contarPrestamosPendientesPorDni(String dni);
    List<Prestamos> obtenerPrestamosPendientesPaginado(int pagina, int prestamosPorPagina);
    List<Prestamos> obtenerPrestamosPendientesPorDniPaginado(String dni, int pagina, int prestamosPorPagina);
    Prestamos obtenerPorId(int idPrestamo, Connection conn);
    boolean cambiarEstadoPago(int idPrestamo, String nuevoEstadoPago);
    int obtenerIdPrestamoPorCuota(int idCuota);
    int contarCuotasPendientesPorPrestamo(int idPrestamo);
    Prestamos buscarPorId(int idPrestamo);
    boolean EliminarxUsuario(int id);
    List<Prestamos> obtenerPrestamosPorFechaYEstado(java.util.Date desde, java.util.Date hasta, String estado);

}
