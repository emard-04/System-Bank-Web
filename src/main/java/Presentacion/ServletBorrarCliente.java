package Presentacion;

import java.io.IOException;
import Daos.*;
import negocioImpl.PersonaNegImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletBorrarCliente")
public class ServletBorrarCliente extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private PersonaNegImpl negPersona = new PersonaNegImpl();
	
	public ServletBorrarCliente () {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/AdminMode/clientesAdmin_borrar.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nroDniStr = request.getParameter("dni_eliminar"); // ojo con el name en el select
		if (nroDniStr == null || nroDniStr.isEmpty()) {
			// Redirigir con error o mensaje
			response.sendRedirect(
					request.getContextPath() + "/AdminMode/clientesAdmin_borrar.jsp?error=No seleccionó cuenta");
			return;
		}

		try {
			System.out.println(nroDniStr);
			boolean exito = negPersona.Eliminar(nroDniStr);

			if (exito) {
				response.sendRedirect(
						request.getContextPath() + "/AdminMode/clientesAdmin_borrar.jsp?msg=CuentaEliminada");
			} else {
				response.sendRedirect(request.getContextPath()
						+ "/AdminMode/clientesAdmin_borrar.jsp?error=Error al eliminar cuenta");
			}

		} catch (NumberFormatException e) {
			// DNI invalido
			response.sendRedirect(
					request.getContextPath() + "/AdminMode/clientesAdmin_borrar.jsp?error=NroCuenta inválido");
		}
	}
}

