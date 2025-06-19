package Presentacion;

import java.io.IOException;
import Entidades.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Daos.*;
import java.io.PrintWriter;
/**
 * Servlet implementation class ServletCargarCuentas
 */
@WebServlet("/ServletCargarCuentas")
public class ServletCargarCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private daoCuentas daoCuenta = new daoCuentas();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCargarCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nroCuentaStr = request.getParameter("nroCuenta");
        if (nroCuentaStr == null || nroCuentaStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int nroCuenta = Integer.parseInt(nroCuentaStr);
        Cuenta cuenta = daoCuenta.BuscarPorNro(nroCuenta); // método que busca cuenta completa

        if (cuenta == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Construir JSON manual o con librería (ejemplo manual):
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String json = "{"
            + "\"nroCuenta\":\"" + cuenta.getNroCuenta() + "\","
            + "\"dniCliente\":\"" + cuenta.getUsuario().getPersona().getDni() + "\","
            + "\"fechaCreacion\":\"" + cuenta.getFechaCreacion().toString() + "\","
            + "\"tipoCuenta\":\"" + cuenta.getTipoCuenta().getIdTipoCuenta() + "\","
            + "\"cbu\":\"" + cuenta.getCbu() + "\","
            + "\"saldo\":\"" + cuenta.getSaldo().toPlainString() + "\""
            + "}";
        out.print(json);
        out.flush();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
