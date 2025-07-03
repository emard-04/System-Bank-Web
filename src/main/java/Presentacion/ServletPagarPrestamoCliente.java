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
            // Guardar cuenta seleccionada si viene por parámetro
            String paramCuenta = request.getParameter("cuentaSeleccionada");
            if (paramCuenta != null && !paramCuenta.isEmpty()) {
                int cuentaSeleccionada = Integer.parseInt(paramCuenta);
                request.getSession().setAttribute("cuenta", cuentaSeleccionada);
               // request.setAttribute("nroCuenta", cuentaSeleccionada);
            }

            List<Cuota> cuotasPendientes = cuotaNeg.obtenerCuotasPendientesPorUsuario(usuario.getIdUsuario());
          //  System.out.println("Cuentas cargadas:");
            List<Cuenta> cuentas = cuentaNeg.ListarxUsuario(usuario.getIdUsuario());
           // System.out.println("Cuentas encontradas: " + cuentas.size());
	     //   System.out.println("Cuotas pendientes encontradas: " + cuotasPendientes.size());

            request.setAttribute("cuotasPendientes", cuotasPendientes);
            request.setAttribute("cuentasUsuario", cuentas);

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

        try {
            int idCuota = Integer.parseInt(request.getParameter("cuotaSeleccionada"));
            int nroCuenta = Integer.parseInt(request.getParameter("cuenta")); // ✅ CAMBIO

           // System.out.println("ID cuota seleccionada: " + idCuota);
            //System.out.println("Nro cuenta seleccionada: " + nroCuenta);

            boolean exito = cuotaNeg.pagarCuota(idCuota, nroCuenta);
            //System.out.println("Resultado del pago de cuota: " + exito);

            if (exito) {
                request.setAttribute("mensaje", "✅ Cuota pagada correctamente.");
            } else {
                request.setAttribute("mensaje", "❌ Error al pagar cuota.");
            }

            List<Cuota> cuotasPendientes = cuotaNeg.obtenerCuotasPendientesPorUsuario(usuario.getIdUsuario());
            List<Cuenta> cuentas = cuentaNeg.ListarxUsuario(usuario.getIdUsuario());

            request.setAttribute("cuotasPendientes", cuotasPendientes);
            request.setAttribute("cuentasUsuario", cuentas);

            request.getRequestDispatcher("/ClientMode/pagarPrestamoClient.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ServletPagarPrestamoCliente?error=excepcion");
        }
    }
   

}