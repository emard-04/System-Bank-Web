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

        // Página actual, por defecto 1 si no se pasa o es inválida
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

        final int prestamosPorPagina = 10; // 10 préstamos por página

        // Obtén el total de préstamos para el filtro dado (método que debes implementar)
        int totalPrestamos;
        if (dniFiltro != null && !dniFiltro.isEmpty()) {
            totalPrestamos = prestamoNeg.contarPrestamosPendientesPorDni(dniFiltro);
        } else {
            totalPrestamos = prestamoNeg.contarPrestamosPendientes();
        }

        // Calcula el total de páginas
        int totalPaginas = (int) Math.ceil((double) totalPrestamos / prestamosPorPagina);

        // Ajustar la página actual para que no supere el total de páginas
        if (paginaActual > totalPaginas && totalPaginas > 0) {
            paginaActual = totalPaginas;
        }

        List<Prestamos> prestamosPendientes;
        if (dniFiltro != null && !dniFiltro.isEmpty()) {
            // Obtiene la página de préstamos filtrados por DNI
            prestamosPendientes = prestamoNeg.obtenerPrestamosPendientesPorDniPaginado(dniFiltro, paginaActual, prestamosPorPagina);
            request.setAttribute("filtroDni", dniFiltro);
        } else {
            // Obtiene la página de préstamos sin filtro
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
