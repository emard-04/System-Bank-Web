package Presentacion;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Daos.*;
import Entidades.*;
import javax.servlet.RequestDispatcher;
/**
 * Servlet implementation class ServletListarCuentas
 */
@WebServlet("/ServletListarCuentas")
public class ServletListarCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private daoCuentas daoCuenta = new daoCuentas();
   
    public ServletListarCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(request.getParameter("openListar")!=null) {
    	windowDefault(request, response);
    	}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void windowDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        List<Cuenta> listaCuentas = daoCuenta.ListarTodo();

	        // Parámetros de paginación
	        int cuentasPorPagina = 10;
	        int paginaActual = 1;

	        // Obtener número de página desde la URL
	        String pagParam = request.getParameter("pagina");
	        if (pagParam != null) {
	            try {
	                paginaActual = Integer.parseInt(pagParam);
	            } catch (NumberFormatException e) {
	                paginaActual = 1;
	            }
	        }

	        // Calcular índices
	        int totalCuentas = listaCuentas.size();
	        int totalPaginas = (int) Math.ceil((double) totalCuentas / cuentasPorPagina);
	        int desde = (paginaActual - 1) * cuentasPorPagina;
	        int hasta = Math.min(desde + cuentasPorPagina, totalCuentas);

	        // Sublista de la página actual
	        List<Cuenta> cuentasPaginadas = listaCuentas.subList(desde, hasta);

	        // Setear atributos
	        request.setAttribute("cuentas", cuentasPaginadas);
	        request.setAttribute("paginaActual", paginaActual);
	        request.setAttribute("totalPaginas", totalPaginas);

	        RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMode/cuentasAdmin.jsp");
	        dispatcher.forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("error.jsp");
	    }
	}
	}

