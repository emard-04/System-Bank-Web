package Presentacion;

import java.io.IOException;
import java.util.List;

import negocioImpl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entidades.*;
import exceptions.ErrorAlEliminarException;

//Borrar usuario / cliente
@WebServlet("/ServletBorrarCliente")
public class ServletBorrarCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioNegImpl negUsuario = new UsuarioNegImpl();

	public ServletBorrarCliente() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Usuario> users = negUsuario.listarTodo();

		request.setAttribute("users", users);

		request.getRequestDispatcher("/AdminMode/clientesAdmin_borrar.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nroDniStr = request.getParameter("dni_eliminar");

		if (nroDniStr == null || nroDniStr.isEmpty()) {
			response.sendRedirect(request.getContextPath()
					+ "/AdminMode/clientesAdmin_borrar.jsp?error=No seleccionó Ningun cliente");
			return;
		}

		try {
			if (negUsuario.Eliminar(nroDniStr)) {
				request.getSession().setAttribute("mensaje", "✅ Se ha eliminado correctamente");
				response.sendRedirect(request.getContextPath() + "/ServletBorrarCliente?msg=CuentaEliminada");
			} else {
				throw new ErrorAlEliminarException();
			}

		} catch (NumberFormatException e) {
			// En caso de que el nroCuenta no es válido
			response.sendRedirect(request.getContextPath() + "/ServletBorrarCliente?error=NroCuenta inválido");
		} catch (ErrorAlEliminarException e) {
			// En caso de que haya un error al eliminar la cuenta
			request.getSession().setAttribute("mensaje", " ❌ Hubo un error al eliminar la cuenta ❌ ");
			response.sendRedirect(request.getContextPath() + "/ServletBorrarCliente?error=Error al eliminar cuenta");
		} catch (Exception e) {
			// En caso de un error por fuera de los exceptions ya marcados
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/ServletBorrarCliente?error=Error inesperado");
		}
	}
}
