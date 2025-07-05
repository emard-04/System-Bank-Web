package Presentacion;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.*;
import negocio.LocalidadNeg;
import negocio.PaisNeg;
import negocio.ProvinciaNeg;
import negocio.UsuarioNeg;
import negocioImpl.LocalidadNegImpl;
import negocioImpl.PaisNegImpl;
import negocioImpl.ProvinciaNegImpl;
import negocioImpl.UsuarioNegImpl;

import javax.servlet.RequestDispatcher;
/**
 * Servlet implementation class ServletListarClientes
 */
@WebServlet("/ServletListarClientes")
public class ServletListarClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioNeg nUsuario= new UsuarioNegImpl();
	private PaisNeg nPais= new PaisNegImpl();
  private ProvinciaNeg nProvincia= new ProvinciaNegImpl();
  private LocalidadNeg nLocalidad=new LocalidadNegImpl();
    public ServletListarClientes() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(request.getParameter("openListar")!=null) {
        	windowDefault(request, response);
        	}
    	if (request.getParameter("listarLocalidades") != null) {
	    	int idProvincia = Integer.parseInt(request.getParameter("provinciaId").trim());
	    	List<Localidad> localidades = nLocalidad.listarLocalidadesPorProvincia(idProvincia);

	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");

	        StringBuilder json = new StringBuilder();
	        json.append("[");
	        for (int i = 0; i < localidades.size(); i++) {
	            Localidad loc = localidades.get(i);
	            json.append("{");
	            json.append("\"idLocalidad\":").append(loc.getIdLocalidad()).append(",");
	            json.append("\"nombre\":\"").append(loc.getNombre().replace("\"", "\\\"")).append("\"");
	            json.append("}");
	            if (i < localidades.size() - 1) {
	                json.append(",");
	            }
	        }
	        json.append("]");

	        response.getWriter().write(json.toString());
	        return;
	    }
    	if(request.getParameter("Filtrar")!=null) {
    		String dni=request.getParameter("busqueda");
    		int idProvincia=0;
    		int idLocalidad=0;
    		if(request.getParameter("provincias")!=null && !request.getParameter("provincias").isEmpty())idProvincia=Integer.parseInt(request.getParameter("provincias"));
    		if(request.getParameter("Localidad")!=null && !request.getParameter("Localidad").isEmpty())idLocalidad=Integer.parseInt(request.getParameter("Localidad"));
    		List<Usuario> listaFiltro=nUsuario.filtrar(dni, idProvincia, idLocalidad);
    		Paginar(request, listaFiltro);
    		request.setAttribute("listaProvincias", nProvincia.listarProvincias());
    		RequestDispatcher rd=request.getRequestDispatcher("AdminMode/clientesAdmin.jsp");
    		rd.forward(request, response);
    	}
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void windowDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        List<Usuario> listaPersona = nUsuario.listarTodo();
	        //Lista Provincias
	        request.setAttribute("listaProvincias", nProvincia.listarProvincias());
	        Paginar(request, listaPersona);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMode/clientesAdmin.jsp");
	        dispatcher.forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("error.jsp");
	    }
	}
	private void Paginar(HttpServletRequest request, List<Usuario> listaUsuarios) {
	    int personasPorPagina = 10;
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
	    int totalUsuarios = listaUsuarios.size();
	    int totalPaginas = (int) Math.ceil((double) totalUsuarios / personasPorPagina);
	    int desde = (paginaActual - 1) * personasPorPagina;
	    int hasta = Math.min(desde + personasPorPagina, totalUsuarios);

	    // Sublista de la página actual
	    List<Usuario> usuariosPaginados = listaUsuarios.subList(desde, hasta);

	    // Setear atributos
	    request.setAttribute("personas", usuariosPaginados);
	    request.setAttribute("paginaActual", paginaActual);
	    request.setAttribute("totalPaginas", totalPaginas);
	}
}
