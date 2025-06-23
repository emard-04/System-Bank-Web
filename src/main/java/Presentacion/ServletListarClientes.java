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
 * Servlet implementation class ServletListarClientes
 */
@WebServlet("/ServletListarClientes")
public class ServletListarClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private daoPersonas dao = new daoPersonas();  
  
    public ServletListarClientes() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	        List<Persona> listaPersona = dao.ListarTodo();

	        // Parámetros de paginación
	        int PersonaPorPagina = 10;
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
	        int totalPersonas = listaPersona.size();
	        int totalPaginas = (int) Math.ceil((double) totalPersonas / PersonaPorPagina);
	        int desde = (paginaActual - 1) * PersonaPorPagina;
	        int hasta = Math.min(desde + PersonaPorPagina, totalPersonas);

	        // Sublista de la página actual
	        List<Persona> personasPaginadas = listaPersona.subList(desde, hasta);

	        // Setear atributos
	        request.setAttribute("personas", personasPaginadas);
	        request.setAttribute("paginaActual", paginaActual);
	        request.setAttribute("totalPaginas", totalPaginas);

	        RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMode/clientesAdmin.jsp");
	        dispatcher.forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("error.jsp");
	    }
	}

}
