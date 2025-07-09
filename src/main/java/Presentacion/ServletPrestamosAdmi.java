package Presentacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocioImpl.*;
import negocio.*;
import java.util.List;
import Entidades.*;
/**
 * Servlet implementation class ServletPrestamosAdmi
 */
@WebServlet("/ServletPrestamosAdmi")
public class ServletPrestamosAdmi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private PrestamosNeg prestamoNeg = new PrestamosNegImpl();
	 private final CuotasNeg cuotaNeg = new CuotasNegImpl();
	 private final CuentasNeg cuentaNeg = new CuentasNegImpl();
	 private final MovimientoNeg movimientoNeg = new MovimientoNegImpl();
    public ServletPrestamosAdmi() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dniFiltro = request.getParameter("dni");
        int paginaActual = 1;
        String paginaParam = request.getParameter("pagina");
        if (paginaParam != null) {
            try {
                paginaActual = Integer.parseInt(paginaParam);
                if (paginaActual < 1) paginaActual = 1;
            } catch (NumberFormatException e) {
                paginaActual = 1;
            }
        }

        final int prestamosPorPagina = 10; 
        int totalPrestamos;
        if (dniFiltro != null && !dniFiltro.isEmpty()) {
            totalPrestamos = prestamoNeg.contarPrestamosPendientesPorDni(dniFiltro);
        } else {
            totalPrestamos = prestamoNeg.contarPrestamosPendientes();
        }

        
        int totalPaginas = (int) Math.ceil((double) totalPrestamos / prestamosPorPagina);

       
        if (paginaActual > totalPaginas && totalPaginas > 0) {
            paginaActual = totalPaginas;
        }

        List<Prestamos> prestamosPendientes;
        if (dniFiltro != null && !dniFiltro.isEmpty()) {
            
            prestamosPendientes = prestamoNeg.obtenerPrestamosPendientesPorDniPaginado(dniFiltro, paginaActual, prestamosPorPagina);
            request.setAttribute("filtroDni", dniFiltro);
        } else {
           
            prestamosPendientes = prestamoNeg.obtenerPrestamosPendientesPaginado(paginaActual, prestamosPorPagina);
        }

        request.setAttribute("prestamosPendientes", prestamosPendientes);
        request.setAttribute("paginaActual", paginaActual);
        request.setAttribute("totalPaginas", totalPaginas);
        request.getRequestDispatcher("/AdminMode/prestamosAdmin.jsp").forward(request, response);
    }
   

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	String accion = request.getParameter("accion");
        String idPrestamoStr = request.getParameter("idPrestamo");

        String mensaje = "error"; 

        if (accion != null && idPrestamoStr != null) {
            int idPrestamo = Integer.parseInt(idPrestamoStr);

            if (accion.equalsIgnoreCase("aceptar")) {
                if (prestamoNeg.aprobarPrestamo(idPrestamo)) {
                    
                    Prestamos prestamo = prestamoNeg.buscarPorId(idPrestamo); 
                    
                    agregarMovimiento(prestamo);
                    if (prestamo != null) {
                        BigDecimal montoCuota = prestamo.getMontoCuotasxMes();
                        int cantidadCuotas = Integer.parseInt(prestamo.getPlazoDePago().split(" ")[0]); // Extraer número de cuotas
                        boolean cuotasGeneradas = cuotaNeg.generarCuotasParaPrestamo(idPrestamo, montoCuota, cantidadCuotas);

                        if (cuotasGeneradas) {
                            mensaje = "aprobado";
                        } else {
                            mensaje = "cuotasError";
                        }
                    }
                }
            } else if (accion.equalsIgnoreCase("rechazar")) {
                if (prestamoNeg.rechazarPrestamo(idPrestamo)) {
                    mensaje = "rechazado";
                }
            }
        }

       
        response.sendRedirect(request.getContextPath() + "/ServletPrestamosAdmi?mensaje=" + mensaje);

	}
    private void agregarMovimiento(Prestamos prestamo) {
    	Movimiento movimiento=new Movimiento();
		TipoMovimiento tm= new TipoMovimiento();
		movimiento.setCuentaEmisor(prestamo.getCuenta());
		movimiento.setDetalle("Aprobacion de Prestamo");
		movimiento.setCuentaReceptor(cuentaNeg.BuscarPorNro(1));
		tm.setIdTipoMovimiento(2);
		movimiento.setTipoMovimiento(tm);
		movimiento.setImporte(prestamo.getImportePedido());
		movimiento.setFecha(LocalDate.now());
		movimiento.setUsuario(prestamo.getUsuario());
		movimientoNeg.movimiento(movimiento);
    }
}
