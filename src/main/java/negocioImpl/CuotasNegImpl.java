package negocioImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import Entidades.Cuota;
import negocio.CuotasNeg;
import Daos.*;
import Interfaces.*;

public class CuotasNegImpl implements CuotasNeg {
	private inCuotas dao = new daoCuotas();

	@Override
	public List<Cuota> obtenerCuotasPendientesPorUsuario(int idUsuario) throws SQLException {
		return dao.obtenerCuotasPendientesPorUsuario(idUsuario);
	}

	@Override
	public boolean pagarCuota(int idCuota, int nroCuenta) throws SQLException {
		return dao.pagarCuota(idCuota, nroCuenta);
	}

	public boolean EliminarxUsuario(int id) {
		System.out.println("14 eliminar cuotas");
		return dao.EliminarxUsuario(id);
	}

	@Override
	public boolean generarCuotasParaPrestamo(int idPrestamo, BigDecimal importeCuota, int cantidadCuotas) {
		return dao.generarCuotasParaPrestamo(idPrestamo, importeCuota, cantidadCuotas);
	}

	@Override
	public List<Cuota> obtenerCuotasPendientesPorCuenta(int nroCuenta) throws SQLException {
		return dao.obtenerCuotasPendientesPorCuenta(nroCuenta);
	}

	@Override
	public Cuota obtenerCuotaPorId(int idCuota) {
		// TODO Auto-generated method stub
		return dao.obtenerCuotaPorId(idCuota);
	}

	@Override
	public boolean existenCuotasPendientesPorPrestamo(int idPrestamo) throws SQLException {
		// TODO Auto-generated method stub
		return dao.existenCuotasPendientesPorPrestamo(idPrestamo);
	}
}
