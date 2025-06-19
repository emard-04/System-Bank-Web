package Presentacion;

import java.io.IOException;
import Daos.*;
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
	private daoCuentas daoCuenta = new daoCuentas();
       
    
    public ServletBorrarCuenta() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Opcional: redirigir o mostrar error si se accede con GET
        response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_borrar.jsp");
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
            int nroCuenta = Integer.parseInt(nroCuentaStr);

            boolean exito = daoCuenta.Eliminar(nroCuenta);

            if (exito) {
                response.sendRedirect(request.getContextPath() + "/AdminMode/cuentasAdmin.jsp?msg=CuentaEliminada");
            } else {
                response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_borrar.jsp?error=Error al eliminar cuenta");
            }

        } catch (NumberFormatException e) {
            // nroCuenta no es válido
            response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_borrar.jsp?error=NroCuenta inválido");
        }
    }

}
