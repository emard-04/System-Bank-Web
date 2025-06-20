package Presentacion;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Daos.*;
import Entidades.*;
import javax.servlet.RequestDispatcher;
/**
 * Servlet implementation class ServletListarCuentas
 */
@WebServlet("/ServletListarCuentas")
public class ServletListarCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private daoCuentas daoCuenta = new daoCuentas();
   
    public ServletListarCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(request.getParameter("openListar")!=null) {
    	windowDefault(request, response);
    	}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void windowDefault(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		try {
			List<Cuenta> listaCuentas = daoCuenta.ListarTodo(); 
			request.setAttribute("cuentas", listaCuentas);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMode/cuentasAdmin.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp"); 
		}
	}
	}

