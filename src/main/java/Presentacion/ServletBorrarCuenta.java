package Presentacion;

import java.io.IOException;
import Entidades.*;
import exceptions.ErrorAlEliminarException;
import negocioImpl.CuentasNegImpl;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Borrar cuenta
@WebServlet("/ServletBorrarCuenta")
public class ServletBorrarCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CuentasNegImpl negCuenta = new CuentasNegImpl();

	public ServletBorrarCuenta() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Cuenta> cuentas = negCuenta.ListarTodo(); // usa la capa de negocio

		request.setAttribute("cuentas", cuentas);

		request.getRequestDispatcher("/AdminMode/cuentaAdmin_borrar.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ErrorAlEliminarException {

		String nroCuentaStr = request.getParameter("dni_eliminar");

		if (nroCuentaStr == null || nroCuentaStr.isEmpty()) {
			response.sendRedirect(
					request.getContextPath() + "/AdminMode/cuentaAdmin_borrar.jsp?error=No seleccionó cuenta");
			return;
		}

		try {
			System.out.println("Servlet Borrar cuenta");
			if (negCuenta.Eliminar((Integer.parseInt(nroCuentaStr)))) {
				request.getSession().setAttribute("mensaje", "✅ Se ha eliminado correctamente");
				response.sendRedirect(request.getContextPath() + "/ServletBorrarCuenta?msg=CuentaEliminada");
			} else {
				System.out.println("3404 error cuenta");
				throw new ErrorAlEliminarException();
			}

		} catch (NumberFormatException e) {
			// En caso de que el nroCuenta no es válido
			response.sendRedirect(request.getContextPath() + "/ServletBorrarCuenta?error=NroCuenta inválido");
		} catch (ErrorAlEliminarException e) {
			request.getSession().setAttribute("mensaje", " ❌ Hubo un error al eliminar la cuenta ❌ ");
			response.sendRedirect(request.getContextPath() + "/ServletBorrarCuenta?error=Error al eliminar cuenta");
		} catch (Exception e) {
			// En caso de un error por fuera de los exceptions ya marcados
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/ServletBorrarCuenta?error=Error inesperado");
		}
	}
}
