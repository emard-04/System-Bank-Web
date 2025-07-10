package Presentacion;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import Entidades.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocioImpl.*;

/**
 * Servlet implementation class ServletHomeCliente
 */
@WebServlet("/ServletHomeCliente")
public class ServletHomeCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CuentasNegImpl cuentaNeg = new CuentasNegImpl();

	public ServletHomeCliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		String cuentaParam = request.getParameter("cuentaSeleccionada");
		if (cuentaParam != null && !cuentaParam.isEmpty()) {
			int nroCuenta = Integer.parseInt(cuentaParam);
			Cuenta cuenta = new CuentasNegImpl().BuscarPorNro(nroCuenta);
			if (cuenta != null) {
				session.setAttribute("cuenta", cuenta);
			}
		}

		if (session.getAttribute("cuenta") == null) {
			@SuppressWarnings("unchecked")
			ArrayList<Cuenta> cuentasUsuario = (ArrayList<Cuenta>) session.getAttribute("cuentasUsuario");
			if (cuentasUsuario != null && !cuentasUsuario.isEmpty()) {
				session.setAttribute("cuenta", cuentasUsuario.get(0));
			}
		}

		response.sendRedirect(request.getContextPath() + "/ClientMode/homeClient.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
