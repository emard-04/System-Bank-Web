package negocio;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import Entidades.Cuota;

public interface CuotasNeg {
	List<Cuota> obtenerCuotasPendientesPorUsuario(int idUsuario) throws SQLException;
    boolean pagarCuota(int idCuota, int nroCuenta) throws SQLException;
     boolean generarCuotasParaPrestamo(int idPrestamo, BigDecimal importeCuota, int cantidadCuotas);
     List<Cuota> obtenerCuotasPendientesPorCuenta(int nroCuenta) throws SQLException;
     Cuota obtenerCuotaPorId(int idCuota);
     boolean existenCuotasPendientesPorPrestamo(int idPrestamo) throws SQLException;
     public boolean EliminarxUsuario(int id);
}
