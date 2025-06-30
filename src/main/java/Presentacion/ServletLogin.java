package Presentacion;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.*;
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
	private CuentasNeg cuentaNeg= new CuentasNegImpl();

    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Simplemente mostrar el formulario de login si se accede con GET
        request.getRequestDispatcher("/login.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Intentar login
        Usuario usuario = usuarioNeg.Login(username, password); // método que debería validar y devolver usuario o null
        ArrayList<Cuenta> ListaCuentas= cuentaNeg.ListarxUsuario(usuario.getIdUsuario());
        if (usuario != null && usuario.getIdUsuario() != 0) {
			// LOGIN CORRECTO: guardamos en sesión
			HttpSession session = request.getSession();
			session.setAttribute("usuarioLogueado", usuario);
			session.setAttribute("cuentasUsuario", ListaCuentas);
			// Redireccionamos según tipo de usuario
			if (usuario.isTipoUsuario()) {
				// Admin
				
				response.sendRedirect("AdminMode/HomeAdmin.jsp");
			} else {
				// Cliente
				
				response.sendRedirect("ClientMode/homeClient.jsp");
			}
		} else {
			// LOGIN FALLIDO: mostramos error
			request.setAttribute("errorMessage", "Usuario o contraseña incorrectos");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	

}
