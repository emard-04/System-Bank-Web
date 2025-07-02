package negocioImpl;

import java.sql.SQLException;
import java.util.List;
import Entidades.Cuota;
import negocio.CuotasNeg;
import Daos.*;
import Interfaces.*;

public class CuotasNegImpl implements CuotasNeg{
	private inCuotas dao = new daoCuotas();
	@Override
	public List<Cuota> obtenerCuotasPendientesPorUsuario(int idUsuario) throws SQLException {
		return dao.obtenerCuotasPendientesPorUsuario(idUsuario);
	}

	@Override
	public boolean pagarCuota(int idCuota, int nroCuenta) throws SQLException {
		return dao.pagarCuota(idCuota, nroCuenta);
	}

}
