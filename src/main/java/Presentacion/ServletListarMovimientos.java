package Presentacion;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entidades.Cuenta;
import Entidades.Movimiento;
import Entidades.TelefonoxPersona;
import Entidades.Usuario;
import negocio.MovimientoNeg;
import negocioImpl.MovimientoNegImpl;

/**
 * Servlet implementation class ServletListarMovimientos
 */
@WebServlet("/ServletListarMovimientos")
public class ServletListarMovimientos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private final MovimientoNeg nMov=new MovimientoNegImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletListarMovimientos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		windowDefault(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
private void windowDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Cuenta cuenta=new Cuenta();
	cuenta.setNroCuenta(Integer.parseInt(request.getParameter("cuentaSeleccionada")));
	Usuario usuario=(Usuario)(request.getSession().getAttribute("usuarioLogueado"));
	Movimiento mov = new Movimiento();
	mov.setCuentaEmisor(cuenta);
	mov.setUsuario(usuario);
	request.setAttribute("Lista",nMov.Listarxcuentas(mov));
	RequestDispatcher rd= request.getRequestDispatcher("ClientMode/movientosClient.jsp");
	rd.forward(request, response);
}
}
