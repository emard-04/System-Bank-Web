package Presentacion;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import negocio.*;
import negocioImpl.*;
import javax.servlet.RequestDispatcher;
import java.util.List; 

/**
 * Servlet implementation class ServletPagarPrestamoCliente
 */
@WebServlet("/ServletPagarPrestamoCliente")
public class ServletPagarPrestamoCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final PrestamosNeg prestamosNeg = new PrestamosNegImpl();
	private final CuentasNeg cuentaNeg = new CuentasNegImpl();
	private final CuotasNeg cuotaNeg = new CuotasNegImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPagarPrestamoCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
    	    if (usuario == null) {
    	        response.sendRedirect("login.jsp");
    	        return;
    	    }

    	    try {
    	        List<Cuota> cuotasPendientes = cuotaNeg.obtenerCuotasPendientesPorUsuario(usuario.getIdUsuario());
    	        System.out.println("Cuentas cargadas:");
    	        List<Cuenta> cuentas = cuentaNeg.obtenerCuentasPorUsuario(usuario.getIdUsuario());
    	        System.out.println("Cuentas encontradas: " + cuentas.size());
    	        System.out.println("Cuotas pendientes encontradas: " + cuotasPendientes.size());

    	        request.setAttribute("cuotasPendientes", cuotasPendientes);
    	        request.setAttribute("cuentas", cuentas);

    	        request.getRequestDispatcher("/ClientMode/pagarPrestamoClient.jsp").forward(request, response);
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        response.sendRedirect(request.getContextPath() + "/ClientMode/pagarPrestamoClient.jsp?error=excepcion");
    	    }
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idCuota = Integer.parseInt(request.getParameter("cuotaSeleccionada"));
        int nroCuenta = Integer.parseInt(request.getParameter("cuenta_a_debitar"));

        try {
            boolean exito = cuotaNeg.pagarCuota(idCuota, nroCuenta);
            if (exito) {
            	request.setAttribute("mensaje", "✅ Cuota pagada correctamente.");
                //response.sendRedirect(request.getContextPath() + "/ServletPagarPrestamoCliente?mensaje=exito");
                windowDefault(request, response, "/ServletPagarPrestamoCliente?mensaje=exito");
            } else {
            	request.setAttribute("mensaje", "❌ Error al pagar cuota.");
                //response.sendRedirect(request.getContextPath() + "/ServletPagarPrestamoCliente?error=fallo");
               windowDefault(request, response, "/ServletPagarPrestamoCliente?error=fallo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ServletPagarPrestamoCliente?error=excepcion");
        }
    }
    private void windowDefault(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException{
		 
		 RequestDispatcher rd= request.getRequestDispatcher(jsp);
		 rd.forward(request, response);
	}

}