package Presentacion;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.*;
import exceptions.ErrorUserContraseniaException;
import negocio.*;
import negocioImpl.*;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioNeg usuarioNeg = new UsuarioNegImpl();
	private CuentasNeg cuentaNeg = new CuentasNegImpl();

	public ServletLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ErrorUserContraseniaException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			Usuario usuario;
			usuario = usuarioNeg.Login(username, password);

			if (usuario != null && usuario.getIdUsuario() != 0) {
				ArrayList<Cuenta> ListaCuentas = cuentaNeg.ListarxUsuario(usuario.getIdUsuario());

				HttpSession session = request.getSession();
				session.setAttribute("usuarioLogueado", usuario);
				session.setAttribute("cuentasUsuario", ListaCuentas);

				if (!ListaCuentas.isEmpty()) {
					session.setAttribute("cuenta", ListaCuentas.get(0));
				}

				if (usuario.isTipoUsuario()) {
					response.sendRedirect("AdminMode/HomeAdmin.jsp");
				} else {
					response.sendRedirect("ClientMode/homeClient.jsp");
				}
			} else {
				throw new ErrorUserContraseniaException();
			}
		} catch (ErrorUserContraseniaException e) {
			request.getSession().setAttribute("mensaje", "❌ Usuario o contrasenia icorrectos ❌");
			response.sendRedirect(request.getContextPath() + "/ServletLogin?error=Usuario o contrasenia incorrectos");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/ServletLogin?error=Error inesperado");
		}
	}
}
