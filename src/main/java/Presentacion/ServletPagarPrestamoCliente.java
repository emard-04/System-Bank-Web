package Presentacion;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import negocio.*;
import negocioImpl.*;
import javax.servlet.RequestDispatcher;
import java.util.List; 

/**
 * Servlet implementation class ServletPagarPrestamoCliente
 */
@WebServlet("/ServletPagarPrestamoCliente")
public class ServletPagarPrestamoCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final PrestamosNeg prestamoNeg = new PrestamosNegImpl();
	private final CuentasNeg cuentaNeg = new CuentasNegImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPagarPrestamoCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			// Obtener usuario logueado
			HttpSession session = request.getSession();
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			if (usuario == null) {
				response.sendRedirect("login.jsp");
				return;
			}

			// Obtener cuentas del usuario
			List<Cuenta> cuentas = cuentaNeg.obtenerCuentasPorUsuario(usuario.getIdUsuario());
			request.setAttribute("cuentas", cuentas);

			// Obtener préstamo pendiente (si hay) y su cuota
			Prestamos prestamo = prestamoNeg.obtenerPrestamoPendientePorUsuario(usuario.getIdUsuario());
			if (prestamo != null) {
				request.setAttribute("cuota", prestamo.getMontoCuotasxMes());
			}

			// Redirige a la vista
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ClientMode/pagarPrestamosClient.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ClientMode/pagarPrestamosClient.jsp?error=excepcion");
		}
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");

	    try {
	        HttpSession session = request.getSession();
	        Usuario usuario = (Usuario) session.getAttribute("usuario");
	        if (usuario == null) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp");
	            return;
	        }

	        String cuenta = request.getParameter("cuenta_a_debitar");
	        int nroCuota = Integer.parseInt(request.getParameter("nro_cuota_a_pagar"));
	        BigDecimal importe = new BigDecimal(request.getParameter("importe_hardcodeado"));

	        // Aquí va la lógica para registrar el pago, por ejemplo:
	        // boolean pagoExitoso = prestamosNeg.pagarCuota(usuario, cuenta, nroCuota, importe);

	        boolean pagoExitoso = true; // reemplaza con lógica real

	        if (pagoExitoso) {
	            response.sendRedirect(request.getContextPath() + "/ClientMode/pagarPrestamosClient.jsp?mensaje=exito");
	        } else {
	            response.sendRedirect(request.getContextPath() + "/ClientMode/pagarPrestamosClient.jsp?error=falla_pago");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect(request.getContextPath() + "/ClientMode/pagarPrestamosClient.jsp?error=excepcion");
	    }
	}

}
