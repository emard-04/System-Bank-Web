package Presentacion;

import java.io.IOException;
import Entidades.*;
import Daos.*;
import negocioImpl.CuentasNegImpl;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletBorrarCuenta
 */
@WebServlet("/ServletBorrarCuenta")
public class ServletBorrarCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CuentasNegImpl negCuenta = new CuentasNegImpl();
       
    
    public ServletBorrarCuenta() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Opcional: redirigir o mostrar error si se accede con GET
        //response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_borrar.jsp");
    	List<Cuenta> cuentas = negCuenta.ListarTodo(); // usa tu capa de negocio

        request.setAttribute("cuentas", cuentas);

        request.getRequestDispatcher("/AdminMode/cuentaAdmin_borrar.jsp").forward(request, response);
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String nroCuentaStr = request.getParameter("dni_eliminar"); // ojo con el name en el select
        if (nroCuentaStr == null || nroCuentaStr.isEmpty()) {
            // Redirigir con error o mensaje
            response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_borrar.jsp?error=No seleccionó cuenta");
            return;
        }

        try {
        	System.out.println(nroCuentaStr);
            int nroCuenta = Integer.parseInt(nroCuentaStr);
            boolean exito = negCuenta.Eliminar(nroCuenta);

            if (exito) {
                response.sendRedirect(request.getContextPath() +  "/ServletBorrarCuenta?msg=CuentaEliminada");
            } else {
                response.sendRedirect(request.getContextPath() + "/ServletBorrarCuenta?error=Error al eliminar cuenta");
            }

        } catch (NumberFormatException e) {
            // nroCuenta no es válido
            response.sendRedirect(request.getContextPath() + "/ServletBorrarCuenta?error=NroCuenta inválido");
        }
    }

}
