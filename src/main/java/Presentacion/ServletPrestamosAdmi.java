package Presentacion;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocioImpl.*;
import negocio.*;
import java.util.List;
import Entidades.*;
import javax.servlet.RequestDispatcher;
/**
 * Servlet implementation class ServletPrestamosAdmi
 */
@WebServlet("/ServletPrestamosAdmi")
public class ServletPrestamosAdmi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private PrestamosNeg prestamoNeg = new PrestamosNegImpl();
       
  
    public ServletPrestamosAdmi() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	

    	String dniFiltro = request.getParameter("dni");
    	List<Prestamos> prestamosPendientes;

    	if (dniFiltro != null && !dniFiltro.isEmpty()) {
    		prestamosPendientes = prestamoNeg.obtenerPrestamosPendientesPorDni(dniFiltro);
    		request.setAttribute("filtroDni", dniFiltro);
    	} else {
    		prestamosPendientes = prestamoNeg.obtenerPrestamosPendientes();
    	}

    	
    	request.setAttribute("prestamosPendientes", prestamosPendientes);
    	request.getRequestDispatcher("/AdminMode/prestamosAdmin.jsp").forward(request, response);
    }
   

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		String idPrestamoStr = request.getParameter("idPrestamo");

		if (accion != null && idPrestamoStr != null) {
			int idPrestamo = Integer.parseInt(idPrestamoStr);

			if (accion.equalsIgnoreCase("aceptar")) {
				prestamoNeg.aprobarPrestamo(idPrestamo);
			} else if (accion.equalsIgnoreCase("rechazar")) {
				prestamoNeg.rechazarPrestamo(idPrestamo);
			}
		}
		// Redirige al mismo servlet para actualizar la tabla
		response.sendRedirect(request.getContextPath() + "/ServletPrestamosAdmi");
	}
}
