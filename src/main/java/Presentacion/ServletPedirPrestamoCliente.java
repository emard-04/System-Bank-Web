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
import javax.servlet.http.HttpSession;
import java.math.RoundingMode;
/**
 * Servlet implementation class ServletPedirPrestamoCliente
 */
@WebServlet("/ServletPedirPrestamoCliente")
public class ServletPedirPrestamoCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final PrestamosNeg prestamoNeg = new PrestamosNegImpl();
  
    public ServletPedirPrestamoCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		try {
			//  Obtiene los parámetros del formulario
			BigDecimal montoSolicitado = new BigDecimal(request.getParameter("monto_solicitar"));
			int cantidadCuotas = Integer.parseInt(request.getParameter("cantidad_cuotas"));

			HttpSession session = request.getSession(false);

	        if (session == null || session.getAttribute("usuarioLogueado") == null) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp");
	            return;
	        }
	        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

			// Calcular los datos del préstamo
			BigDecimal interes = new BigDecimal("0.20");
			BigDecimal importeApagar = montoSolicitado.add(montoSolicitado.multiply(interes));
			BigDecimal montoCuota = importeApagar.divide(new BigDecimal(cantidadCuotas), 2, RoundingMode.HALF_UP);
			//new BigDecimal(cantidadCuotas) — Convierte la cantidad de cuotas a un BigDecimal para poder hacer la división.
			//2 — Indica la cantidad de decimales que queremos en el resultado (por ejemplo, 2 decimales para centavos).
			//RoundingMode.HALF_UP — Especifica la forma de redondear: redondea hacia arriba si el siguiente dígito es 5 o más (redondeo "normal").
			String plazoPago = cantidadCuotas + " cuotas";

			// Se Crea un objeto préstamo
			Prestamos prestamo = new Prestamos();
			prestamo.setUsuario(usuario);
			prestamo.setFecha(LocalDate.now());
			prestamo.setImportePedido(montoSolicitado);
			prestamo.setImporteApagar(importeApagar);
			prestamo.setPlazoDePago(plazoPago);
			prestamo.setMontoCuotasxMes(montoCuota);
			prestamo.setEstadoSolicitud("Pendiente");
			prestamo.setEstadoPago("En curso");// pendiente

			//  Se guarda en la base de datos
			boolean guardado = prestamoNeg.agregarPrestamo(prestamo);

			if (guardado) {
				request.setAttribute("mensaje", "✅ Prestamo generado correctamente.");
				//response.sendRedirect(request.getContextPath() + "/ClientMode/PrestamosClient.jsp?mensaje=exito");
				windowDefault(request, response, "/ClientMode/PrestamosClient.jsp");
			} else {
				request.setAttribute("mensaje", "❌ Error al pedir el prestamo.");
				//response.sendRedirect(request.getContextPath() + "/ClientMode/PrestamosClient.jsp?error=no_guardado");
				windowDefault(request, response, "/ClientMode/PrestamosClient.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			 //response.sendRedirect(request.getContextPath() + "/ClientMode/PrestamosClient.jsp?error=excepcion");
			 windowDefault(request, response, "/ClientMode/PrestamosClient.jsp?error=Excepcion");
		}
	}
	private void windowDefault(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException{
		 
		 RequestDispatcher rd= request.getRequestDispatcher(jsp);
		 rd.forward(request, response);
	}

}
