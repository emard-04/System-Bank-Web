package negocio;

import java.sql.SQLException;
import java.util.List;

import Entidades.Cuota;

public interface CuotasNeg {
	List<Cuota> obtenerCuotasPendientesPorUsuario(int idUsuario) throws SQLException;
    boolean pagarCuota(int idCuota, int nroCuenta) throws SQLException;
}
