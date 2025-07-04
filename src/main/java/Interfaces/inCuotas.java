package Interfaces;
import java.util.List;
import java.math.BigDecimal;
import java.sql.SQLException;
import Entidades.*;
public interface inCuotas {
	List<Cuota> obtenerCuotasPendientesPorUsuario(int idUsuario) throws SQLException;
    boolean pagarCuota(int idCuota, int nroCuenta) throws SQLException;
     boolean generarCuotasParaPrestamo(int idPrestamo, BigDecimal importeCuota, int cantidadCuotas);
     List<Cuota> obtenerCuotasPendientesPorCuenta(int nroCuenta) throws SQLException;
     Cuota obtenerCuotaPorId(int idCuota);
     boolean existenCuotasPendientesPorPrestamo(int idPrestamo) throws SQLException;
}
