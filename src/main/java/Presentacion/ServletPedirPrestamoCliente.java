package Presentacion;

import java.io.IOException;
import Entidades.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.*;
import negocioImpl.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import java.math.RoundingMode;
/**
 * Servlet implementation class ServletPedirPrestamoCliente
 */
@WebServlet("/ServletPedirPrestamoCliente")
public class ServletPedirPrestamoCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final PrestamosNeg prestamoNeg = new PrestamosNegImpl();
	 // private final CuotasNeg cuotaNeg = new CuotasNegImpl();
	  private final CuentasNeg cuentaNeg = new CuentasNegImpl();
    public ServletPedirPrestamoCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
        	String cuentaParam = request.getParameter("cuenta");
        	Cuenta cuentaSeleccionada = null;

        	if (cuentaParam != null && !cuentaParam.isEmpty()) {
        	    int nroCuenta = Integer.parseInt(cuentaParam);
        	    cuentaSeleccionada = cuentaNeg.BuscarPorNro(nroCuenta);
        	    if (cuentaSeleccionada != null) {
        	        session.setAttribute("cuenta", cuentaSeleccionada);
        	    }
        	}

        	if (session.getAttribute("cuenta") == null) {
        	    @SuppressWarnings("unchecked")
        	    ArrayList<Cuenta> cuentasUsuario = (ArrayList<Cuenta>) session.getAttribute("cuentasUsuario");
        	    if (cuentasUsuario != null && !cuentasUsuario.isEmpty()) {
        	        session.setAttribute("cuenta", cuentasUsuario.get(0)); // Selecciona la primera cuenta
        	        cuentaSeleccionada = cuentasUsuario.get(0);
        	    }
        	} else {
        	    cuentaSeleccionada = (Cuenta) session.getAttribute("cuenta");
        	}}catch(Exception e) {}
        windowDefault(request, response, "/ClientMode/PrestamosClient.jsp");
        
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    try {
	        BigDecimal montoSolicitado = new BigDecimal(request.getParameter("monto_solicitar"));
	        int cantidadCuotas = Integer.parseInt(request.getParameter("cantidad_cuotas"));
	        HttpSession session = request.getSession(false);

	        if (session == null || session.getAttribute("usuarioLogueado") == null) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp");
	            return;
	        }

	        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
	        Cuenta nroCuentaStr = (Cuenta) session.getAttribute("cuenta");
	        if (nroCuentaStr == null) {
	            request.setAttribute("mensaje", "❌ Debe seleccionar una cuenta.");
	            windowDefault(request, response, "/ClientMode/PrestamosClient.jsp");
	            return;
	        }
	        int nroCuenta = nroCuentaStr.getNroCuenta();
	        boolean puedePedir = prestamoNeg.puedePedirPrestamo(nroCuenta);

	        if (!puedePedir) {
	            request.setAttribute("mensaje", "❌ Esta cuenta ya tiene un préstamo activo.");
	            windowDefault(request, response, "/ClientMode/PrestamosClient.jsp");
	            return;
	        }

	        BigDecimal interes = new BigDecimal("0.20");
	        BigDecimal importeApagar = montoSolicitado.add(montoSolicitado.multiply(interes));
	        BigDecimal montoCuota = importeApagar.divide(new BigDecimal(cantidadCuotas), 2, RoundingMode.HALF_UP);
	        String plazoPago = cantidadCuotas + " cuotas";

	        Prestamos prestamo = new Prestamos();
	        prestamo.setUsuario(usuario);
	        prestamo.setFecha(LocalDate.now());
	        prestamo.setImportePedido(montoSolicitado);
	        prestamo.setImporteApagar(importeApagar);
	        prestamo.setPlazoDePago(plazoPago);
	        prestamo.setMontoCuotasxMes(montoCuota);
	        prestamo.setEstadoSolicitud("Pendiente");
	        prestamo.setEstadoPago("En curso");
	        prestamo.setCuenta(cuentaNeg.BuscarPorNro(nroCuenta));

	       
	        int idPrestamo = prestamoNeg.insertarYObtenerId(prestamo);

	        if (idPrestamo > 0) {
	            request.setAttribute("mensaje", "✅ Solicitud de préstamo enviada. Esperando aprobación del administrador.");
	        } else {
	            request.setAttribute("mensaje", "❌ Error al crear préstamo.");
	        }

	        windowDefault(request, response, "/ClientMode/PrestamosClient.jsp");

	    } catch (Exception e) {
	        e.printStackTrace();
	        windowDefault(request, response, "/ClientMode/PrestamosClient.jsp?error=Excepcion");
	    }
	}
	private void windowDefault(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        rd.forward(request, response);
    }

}
