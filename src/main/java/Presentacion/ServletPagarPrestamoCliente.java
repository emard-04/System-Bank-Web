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
import java.util.ArrayList;
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
            String paramCuenta = request.getParameter("cuenta");
            int cuentaSeleccionada = -1;
            if (paramCuenta != null && !paramCuenta.isEmpty()) {
                cuentaSeleccionada = Integer.parseInt(paramCuenta);
                request.getSession().setAttribute("cuenta", cuentaSeleccionada);
            }
            

            List<Cuenta> cuentas = cuentaNeg.ListarxUsuario(usuario.getIdUsuario());
            List<Cuota> cuotasPendientes;

            if (cuentaSeleccionada > 0) {
                // Filtrar cuotas solo para la cuenta seleccionada
                cuotasPendientes = cuotaNeg.obtenerCuotasPendientesPorCuenta(cuentaSeleccionada);
            } else {
                // Si no hay cuenta seleccionada, podés mostrar todas o ninguna
                cuotasPendientes = new ArrayList<>();
            }
            if (cuentaSeleccionada > 0 && request.getParameter("cuotaSeleccionada") != null) {
                int idCuota = Integer.parseInt(request.getParameter("cuotaSeleccionada"));
                Cuota cuotaElegida = cuotaNeg.obtenerCuotaPorId(idCuota);
                request.setAttribute("cuotaElegida", cuotaElegida);
            }

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

                // 🔥 Obtener la cuota pagada para saber a qué préstamo pertenece
                Cuota cuotaPagada = cuotaNeg.obtenerCuotaPorId(idCuota);
                int idPrestamo = cuotaPagada.getIdPrestamo();

                // 🔍 Verificar si quedan cuotas pendientes de ese préstamo
                boolean quedanCuotasPendientes = cuotaNeg.existenCuotasPendientesPorPrestamo(idPrestamo);

                if (!quedanCuotasPendientes) {
                    prestamosNeg.cambiarEstadoPago(idPrestamo, "Pagado");
                    System.out.println("✔ Estado del préstamo " + idPrestamo + " cambiado a Pagado");
                } 
            }else {
                request.setAttribute("mensaje", "❌ Error al pagar cuota.");
            }

            List<Cuota> cuotasPendientes = cuotaNeg.obtenerCuotasPendientesPorCuenta(nroCuenta);
            System.out.println("Cuotas pendientes encontradas: " + cuotasPendientes.size());
            List<Cuenta> cuentas = cuentaNeg.ListarxUsuario(usuario.getIdUsuario());
            request.getSession().setAttribute("cuentasUsuario", cuentas); 

            request.setAttribute("cuotasPendientes", cuotasPendientes);
            request.setAttribute("cuentasUsuario", cuentas);

            request.getRequestDispatcher("/ClientMode/pagarPrestamoClient.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ServletPagarPrestamoCliente?error=excepcion");
        }
    }
}