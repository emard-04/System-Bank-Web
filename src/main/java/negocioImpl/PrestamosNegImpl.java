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
public class PrestamosNegImpl implements PrestamosNeg{
	 private final InPrestamos prestamoDao = new daoPrestamos();
	 private final inCuentas cuentaDao = new daoCuentas();
private  CuotasNeg negCuota;
	    

	    @Override
	    public List<Prestamos> listarPrestamosCliente(int idUsuario) {
	        return prestamoDao.obtenerPorUsuario(idUsuario);
	    }

	    @Override
	    public List<Prestamos> listarTodos() {
	        return prestamoDao.obtenerTodos();
	    }
public boolean EliminarxUsuario(int id) {
	negCuota=new CuotasNegImpl();
	for(Prestamos prestamo: listarPrestamosCliente(id)) {
		if(!negCuota.EliminarxUsuario(prestamo.getIdPrestamo())) {
			return false;
		}
	}
	return prestamoDao.EliminarxUsuario(id);
}
	    @Override
	    public boolean aprobarPrestamo(int idPrestamo) {
	    	Connection conn = null;
	        boolean exito = false;

	        try {
	        	  System.out.println("Intentando aprobar préstamo id: " + idPrestamo);
	            conn = Conexion.getConexion().getSQLConnection();
	            conn.setAutoCommit(false); // Iniciar transacción

	            // 1. Obtener préstamo con importe y usuario
	            Prestamos prestamo = prestamoDao.obtenerPorId(idPrestamo, conn);
	           // System.out.println("Usuario del préstamo: " + (prestamo.getUsuario() != null ? prestamo.getUsuario().getIdUsuario() : "null"));
	           // System.out.println("Préstamo encontrado: " + prestamo);
	            if (prestamo == null) return false;

	            int idUsuario = prestamo.getUsuario().getIdUsuario();
	            BigDecimal importe = prestamo.getImportePedido();
	           // System.out.println("Id Usuario: " + idUsuario + ", Importe: " + importe);

	            // 2. Obtener cuenta del usuario
	            //System.out.println("Nro Cuenta: " + nroCuenta);

	            // 3. Cambiar estado del préstamo a 'Aceptado'
	            boolean actualizado = prestamoDao.cambiarEstadoSolicitado(idPrestamo, "Aprobado", conn);
	           // System.out.println("Estado actualizado: " + actualizado);

	            // 4. Acreditar importe a la cuenta del usuario
	            boolean saldoActualizado = cuentaDao.actualizarSaldo(prestamo.getCuenta().getNroCuenta(), importe, conn);
	           // System.out.println("Saldo actualizado: " + saldoActualizado);

	            exito = actualizado && saldoActualizado;

	            if (exito) {
	                conn.commit();
	            } else {
	                conn.rollback();
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            if (conn != null) try { conn.rollback(); } catch (SQLException ignored) {}
	        } finally {
	            if (conn != null) {
	                try {
	                    conn.setAutoCommit(true);
	                    conn.close();
	                } catch (SQLException ignored) {}
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
	    public boolean marcarPrestamoPagado(int idPrestamo) {
	        return prestamoDao.cambiarEstadoPago(idPrestamo, "Pagado");
	    }
	    public boolean marcarPrestamoEnIncumplimiento(int idPrestamo) {
	        return prestamoDao.cambiarEstadoPago(idPrestamo, "Incumplido");
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
		        p = new daoPrestamos().obtenerPorId(idPrestamo, conn); // <- Usás tu función existente
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return p;
		}
		
		@Override
		public List<Prestamos> obtenerPrestamosPorFechaYEstado(java.util.Date desde, java.util.Date hasta, String estado) {
	        // Simplemente delega la llamada al DAO, ya que la lógica de consulta está allí
	        return prestamoDao.obtenerPrestamosPorFechaYEstado(desde, hasta, estado);
	    }
		
}
