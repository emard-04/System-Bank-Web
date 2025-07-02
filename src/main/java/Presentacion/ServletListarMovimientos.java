package Presentacion;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import Daos.daoMovimiento;
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
		if(request.getParameter("Actualizar")!=null) {
		windowDefault(request, response);
		}
		if(request.getParameter("Filtrar")!=null) {
		String nombre = request.getParameter("busqueda");
		String operador = request.getParameter("tipoOperacion");
		String desdeStr = request.getParameter("fechaDesde");
		String hastaStr = request.getParameter("fechaHasta");
		Cuenta cuenta=new Cuenta();
		System.out.println("Cuenta numero"+ request.getSession().getAttribute("cuenta")+"xd");
		cuenta.setNroCuenta((Integer)request.getSession().getAttribute("cuenta"));
		Usuario usuario=(Usuario)(request.getSession().getAttribute("usuarioLogueado"));
		Movimiento mov = new Movimiento();
		mov.setCuentaEmisor(cuenta);
		mov.setUsuario(usuario);
		// Validar formatos y convertir
		LocalDate desde = (desdeStr != null && !desdeStr.isEmpty()) ? LocalDate.parse(desdeStr) : null;
		LocalDate hasta = (hastaStr != null && !hastaStr.isEmpty()) ? LocalDate.parse(hastaStr) : null;

		// Llamás a la capa de negocio
		request.setAttribute("ListaFiltra",nMov.filtrar(mov, nombre, operador, desde, hasta));
		RequestDispatcher rd= request.getRequestDispatcher("ClientMode/movientosClient.jsp");
		rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
private void windowDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Cuenta cuenta=new Cuenta();
	if(request.getParameter("cuentaSeleccionada")!=null) {
	cuenta.setNroCuenta((Integer.parseInt(request.getParameter("cuentaSeleccionada"))));}
	else{
		cuenta.setNroCuenta((Integer)request.getSession().getAttribute("cuenta"));
	}
	Usuario usuario=(Usuario)(request.getSession().getAttribute("usuarioLogueado"));
	if(cuenta.getNroCuenta()!=0) {
		request.getSession().setAttribute("cuenta", cuenta.getNroCuenta());
	}
	Movimiento mov = new Movimiento();
	mov.setCuentaEmisor(cuenta);
	mov.setUsuario(usuario);
	request.setAttribute("Lista",nMov.Listarxcuentas(mov));
	RequestDispatcher rd= request.getRequestDispatcher("ClientMode/movientosClient.jsp");
	rd.forward(request, response);
}
}
