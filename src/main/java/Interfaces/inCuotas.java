package Interfaces;
import java.util.List;
import java.sql.SQLException;
import Entidades.*;
public interface inCuotas {
	List<Cuota> obtenerCuotasPendientesPorUsuario(int idUsuario) throws SQLException;
    boolean pagarCuota(int idCuota, int nroCuenta) throws SQLException;
}
