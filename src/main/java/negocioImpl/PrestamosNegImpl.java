package negocioImpl;

import Daos.*;
import java.sql.Connection;

import Interfaces.Conexion;
import Interfaces.InPrestamos;
import negocio.*;
import Entidades.*;
import java.util.List;
import java.sql.SQLException;
import java.math.BigDecimal;
import Daos.*;
import Interfaces.*;

public class PrestamosNegImpl implements PrestamosNeg {
	private final InPrestamos prestamoDao = new daoPrestamos();
	private final inCuentas cuentaDao = new daoCuentas();
	private CuotasNeg negCuota;

	@Override
	public List<Prestamos> listarPrestamosCliente(int idUsuario) {
		return prestamoDao.obtenerPorUsuario(idUsuario);
	}

	@Override
	public List<Prestamos> listarTodos() {
		return prestamoDao.obtenerTodos();
	}

	public boolean EliminarxUsuario(int id) {
		negCuota = new CuotasNegImpl();

		System.out.println("11 lista de prestamos prestamo x us");

		for (Prestamos prestamo : listarPrestamosCliente(id)) {
			System.out.println("13 For prestamoE x us");
			if (!negCuota.EliminarxUsuario(prestamo.getIdPrestamo())) {
				System.out.println("1 Fallo eliminar cuotas us");
				return false;
			}
		}

		System.out.println("1 Elimar prestamo us ");

		return prestamoDao.EliminarxUsuario(id);
	}

	public boolean EliminarxCuenta(int nrocuenta) {
		negCuota = new CuotasNegImpl();

		if (prestamoDao.BuscarxCuenta(nrocuenta) > 0) {
			System.out.println("Hay prestamos");
			negCuota.EliminarxUsuario(prestamoDao.BuscarxCuenta(nrocuenta));
			return prestamoDao.EliminarxCuenta(nrocuenta);
		} else {
			System.out.println("No hay prestamos");
			return true;
		}
	}

	@Override
	public boolean aprobarPrestamo(int idPrestamo) {
		Connection conn = null;
		boolean exito = false;

		try {

			conn = Conexion.getConexion().getSQLConnection();
			conn.setAutoCommit(false);

			Prestamos prestamo = prestamoDao.obtenerPorId(idPrestamo, conn);

			if (prestamo == null)
				return false;

			BigDecimal importe = prestamo.getImportePedido();

			boolean actualizado = prestamoDao.cambiarEstadoSolicitado(idPrestamo, "Aprobado", conn);

			boolean saldoActualizado = cuentaDao.actualizarSaldo(prestamo.getCuenta().getNroCuenta(), importe, conn);

			exito = actualizado && saldoActualizado;

			if (exito) {
				conn.commit();
			} else {
				conn.rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException ignored) {
				}
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException ignored) {
				}
			}
		}

		return exito;
	}

	    @Override
	    public boolean rechazarPrestamo(int idPrestamo) {
	        Connection conn = null;
	        boolean exito = false;

	        try {
	            conn = Conexion.getConexion().getSQLConnection();
	            conn.setAutoCommit(true); 
	            exito = prestamoDao.cambiarEstadoSolicitado(idPrestamo, "Rechazado", conn);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (conn != null) {
	                try { conn.close(); } catch (SQLException ignored) {}
	            }
	        }

	        return exito;
		}

		@Override
		public boolean marcarPrestamoPagado(int idPrestamo, Connection conn) {
			return prestamoDao.cambiarEstadoPago(idPrestamo, "Pagado", conn);
		}

	    @Override
	    public boolean marcarPrestamoEnIncumplimiento(int idPrestamo, Connection conn) {
	        return prestamoDao.cambiarEstadoPago(idPrestamo, "Incumplido", conn);
	    }
	    @Override
	    public Prestamos obtenerPrestamoPendientePorUsuario(int idUsuario) {
	        return prestamoDao.obtenerPendientePorUsuario(idUsuario);
	    }

		@Override
		public List<Prestamos> obtenerPrestamosPendientes() {
			System.out.println("Llamando a obtenerPrestamosPendientes en PrestamosNegImpl");
			return  prestamoDao.obtenerPrestamosPendientes();
		}

		@Override
		public List<Prestamos> obtenerPrestamosPendientesPorDni(String dni) {
			return prestamoDao.obtenerPrestamosPendientesPorDni(dni);
		}

		@Override
		public boolean puedePedirPrestamo(int idCuenta) {
			return prestamoDao.cantidadPrestamosActivosPorCuenta(idCuenta) == 0;
		}

		@Override
		public int agregarPrestamo(Prestamos prestamo) {
			return prestamoDao.insertarYObtenerId(prestamo);
		}
		

		@Override
		public int insertarYObtenerId(Prestamos p) {
			return prestamoDao.insertarYObtenerId(p);
		}

		@Override
		public int contarPrestamosPendientes() {
			
			return prestamoDao.contarPrestamosPendientes();
		}

		@Override
		public int contarPrestamosPendientesPorDni(String dni) {
			// TODO Auto-generated method stub
			return prestamoDao.contarPrestamosPendientesPorDni(dni);
		}

		@Override
		public List<Prestamos> obtenerPrestamosPendientesPaginado(int pagina, int prestamosPorPagina) {
			// TODO Auto-generated method stub
			return prestamoDao.obtenerPrestamosPendientesPaginado(pagina, prestamosPorPagina);
		}

		@Override
		public List<Prestamos> obtenerPrestamosPendientesPorDniPaginado(String dni, int pagina,
				int prestamosPorPagina) {
			// TODO Auto-generated method stub
			return prestamoDao.obtenerPrestamosPendientesPorDniPaginado(dni, pagina, prestamosPorPagina);
		}

		@Override
		public Prestamos obtenerPorId(int idPrestamo, Connection conn) {
			return prestamoDao.obtenerPorId(idPrestamo, conn);
		}

		@Override
		public boolean cambiarEstadoPago(int idPrestamo, String nuevoEstadoPago, Connection conn) {
			// TODO Auto-generated method stub
			return prestamoDao.cambiarEstadoPago(idPrestamo, nuevoEstadoPago,conn);
		}
		@Override
		public boolean cambiarEstadoPago(int idPrestamo, String nuevoEstadoPago) {
			// TODO Auto-generated method stub
			return prestamoDao.cambiarEstadoPago(idPrestamo, nuevoEstadoPago);
		}

		@Override
		public int obtenerIdPrestamoPorCuota(int idCuota) {
			// TODO Auto-generated method stub
			return prestamoDao.obtenerIdPrestamoPorCuota(idCuota);
		}

		@Override
		public int contarCuotasPendientesPorPrestamo(int idPrestamo) {
			// TODO Auto-generated method stub
			return prestamoDao.contarCuotasPendientesPorPrestamo(idPrestamo);
		}
		@Override
		public Prestamos buscarPorId(int idPrestamo) {
		    Prestamos p = null;
		    try (Connection conn = Conexion.getConexion().getSQLConnection()) {
		        p = new daoPrestamos().obtenerPorId(idPrestamo, conn); 
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return p;
		}
		
		@Override
		public List<Prestamos> obtenerPrestamosPorFechaYEstado(java.util.Date desde, java.util.Date hasta, String estado) {
	       
	        return prestamoDao.obtenerPrestamosPorFechaYEstado(desde, hasta, estado);
	    }
		
}
