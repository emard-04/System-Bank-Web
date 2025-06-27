package Presentacion;

import java.io.IOException;
import negocioImpl.*;
import Daos.*;
import negocioImpl.PersonaNegImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.*;

@WebServlet("/ServletBorrarCliente")
public class ServletBorrarCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PersonaNegImpl negPersona = new PersonaNegImpl();
	private UsuarioNegImpl negUsuario = new UsuarioNegImpl();

	public ServletBorrarCliente() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/AdminMode/clientesAdmin_borrar.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nroDniStr = request.getParameter("dni_eliminar");

		if (nroDniStr == null || nroDniStr.isEmpty()) {
			response.sendRedirect(
					request.getContextPath() + "/AdminMode/clientesAdmin_borrar.jsp?error=No seleccionó DNI");
			return;
		}

		try {
			// Buscar el usuario por DNI
			Usuario usuario = negUsuario.BuscarDni(nroDniStr);

			if (usuario == null || usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()) {
				response.sendRedirect(
						request.getContextPath() + "/AdminMode/clientesAdmin_borrar.jsp?error=Usuario no encontrado");
				return;
			}

			String nombreUsuario = usuario.getNombreUsuario();

			// Eliminar usuario y marcar persona como inactiva
			boolean exitoPersona = negPersona.Eliminar(nroDniStr);
			boolean exitoUsuario = negUsuario.Eliminar(nombreUsuario);

			if (exitoPersona && exitoUsuario) {
				response.sendRedirect(
						request.getContextPath() + "/AdminMode/clientesAdmin_borrar.jsp?msg=CuentaEliminada");
			} else {
				response.sendRedirect(request.getContextPath()
						+ "/AdminMode/clientesAdmin_borrar.jsp?error=Error al eliminar cuenta");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + "/AdminMode/clientesAdmin_borrar.jsp?error=Error inesperado");
		}
	}
}
