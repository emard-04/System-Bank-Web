package negocioImpl;
import Daos.daoPrestamos;
import Interfaces.InPrestamos;
import negocio.*;
import Entidades.*;
import java.util.List; 
public class PrestamosNegImpl implements PrestamosNeg{
	 private final InPrestamos prestamoDao = new daoPrestamos();

	    @Override
	    public boolean agregarPrestamo(Prestamos p) {
	        return prestamoDao.agregar(p);
	    }

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
}
