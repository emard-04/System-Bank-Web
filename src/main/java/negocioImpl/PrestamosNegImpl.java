package negocioImpl;
import Daos.daoPrestamos;
import Interfaces.InPrestamos;
import negocio.*;
import Entidades.*;
import java.util.List; 
public class PrestamosNegImpl implements PrestamosNeg{
	 private final InPrestamos prestamoDao = new daoPrestamos();

	    

	    @Override
	    public List<Prestamos> listarPrestamosCliente(int idUsuario) {
	        return prestamoDao.obtenerPorUsuario(idUsuario);
	    }

	    @Override
	    public List<Prestamos> listarTodos() {
	        return prestamoDao.obtenerTodos();
	    }

	    @Override
	    public boolean aprobarPrestamo(int idPrestamo) {
	        return prestamoDao.cambiarEstadoSolicitado(idPrestamo, "Aceptado");
	    }

	    @Override
	    public boolean rechazarPrestamo(int idPrestamo) {
	        return prestamoDao.cambiarEstadoSolicitado(idPrestamo, "Rechazado");
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
}
