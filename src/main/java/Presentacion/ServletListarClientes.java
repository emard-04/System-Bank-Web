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
	private UsuarioNeg nUsuario = new UsuarioNegImpl();
	private PaisNeg nPais = new PaisNegImpl();
	private ProvinciaNeg nProvincia = new ProvinciaNegImpl();
	private LocalidadNeg nLocalidad = new LocalidadNegImpl();

	public ServletListarClientes() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("openListar") != null) {
			windowDefault(request, response);
		}
		if (request.getParameter("listarProvincias") != null) {
			int idPais = Integer.parseInt(request.getParameter("idPais").trim());
			List<Provincia> provincia = nProvincia.listarProvinciasxPais(idPais);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			StringBuilder json = new StringBuilder();
			json.append("[");
			for (int i = 0; i < provincia.size(); i++) {
				Provincia prov = provincia.get(i);
				json.append("{");
				json.append("\"idProvincia\":").append(prov.getIdProvincia()).append(",");
				json.append("\"nombre\":\"").append(prov.getNombre().replace("\"", "\\\"")).append("\"");
				json.append("}");
				if (i < provincia.size() - 1) {
					json.append(",");
				}
			}
			json.append("]");

			response.getWriter().write(json.toString());
			return;
		}
		if (request.getParameter("listarLocalidades") != null) {
			int idProvincia = Integer.parseInt(request.getParameter("idProvincia").trim());
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
		if (request.getParameter("Filtrar") != null) {
			String dni = request.getParameter("busqueda");
			int idProvincia = 0;
			int idLocalidad = 0;
			int idPais = 0;
			if (request.getParameter("pais") != null && !request.getParameter("pais").isEmpty())
				idPais = Integer.parseInt(request.getParameter("pais"));
			if (request.getParameter("provincias") != null && !request.getParameter("provincias").isEmpty())
				idProvincia = Integer.parseInt(request.getParameter("provincias"));
			if (request.getParameter("Localidad") != null && !request.getParameter("Localidad").isEmpty())
				idLocalidad = Integer.parseInt(request.getParameter("Localidad"));
			List<Usuario> listaFiltro = nUsuario.filtrar(dni, idProvincia, idLocalidad, idPais);
			Paginar(request, listaFiltro);
			request.setAttribute("listaPais", nPais.listarTodo());
			RequestDispatcher rd = request.getRequestDispatcher("AdminMode/clientesAdmin.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void windowDefault(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Usuario> listaPersona = nUsuario.listarTodo();
			// Lista Provincias
			request.setAttribute("listaPais", nPais.listarTodo());
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

		String pagParam = request.getParameter("pagina");
		if (pagParam != null) {
			try {
				paginaActual = Integer.parseInt(pagParam);
			} catch (NumberFormatException e) {
				paginaActual = 1;
			}
		}

		int totalUsuarios = listaUsuarios.size();
		int totalPaginas = (int) Math.ceil((double) totalUsuarios / personasPorPagina);
		int desde = (paginaActual - 1) * personasPorPagina;
		int hasta = Math.min(desde + personasPorPagina, totalUsuarios);


		List<Usuario> usuariosPaginados = listaUsuarios.subList(desde, hasta);

		request.setAttribute("personas", usuariosPaginados);
		request.setAttribute("paginaActual", paginaActual);
		request.setAttribute("totalPaginas", totalPaginas);
	}
}
