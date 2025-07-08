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
import java.time.LocalDate;

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
	private final MovimientoNeg movNeg = new MovimientoNegImpl();
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
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        try {
        	String cuentaParam = request.getParameter("cuenta");
        	Cuenta cuentaSeleccionada = null;

        	if (cuentaParam != null && !cuentaParam.isEmpty()) {
        	    int nroCuenta = Integer.parseInt(cuentaParam);
        	    cuentaSeleccionada = cuentaNeg.BuscarPorNro(nroCuenta);
        	    if (cuentaSeleccionada != null) {
        	        session.setAttribute("cuenta", cuentaSeleccionada);
        	    }
        	}

        	if (session.getAttribute("cuenta") == null) {
        	    @SuppressWarnings("unchecked")
        	    ArrayList<Cuenta> cuentasUsuario = (ArrayList<Cuenta>) session.getAttribute("cuentasUsuario");
        	    if (cuentasUsuario != null && !cuentasUsuario.isEmpty()) {
        	        session.setAttribute("cuenta", cuentasUsuario.get(0)); // Selecciona la primera cuenta
        	        cuentaSeleccionada = cuentasUsuario.get(0);
        	    }
        	} else {
        	    cuentaSeleccionada = (Cuenta) session.getAttribute("cuenta");
        	}

            List<Cuenta> cuentas = cuentaNeg.ListarxUsuario(usuario.getIdUsuario());
            List<Cuota> cuotasPendientes;

            if (cuentaSeleccionada != null) {
                cuotasPendientes = cuotaNeg.obtenerCuotasPendientesPorCuenta(cuentaSeleccionada.getNroCuenta());
            } else {
                cuotasPendientes = new ArrayList<>();
            }

            if (cuentaSeleccionada != null && request.getParameter("cuotaSeleccionada") != null) {
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
            // Obtener parámetros y validar que no estén vacíos
            String paramCuota = request.getParameter("cuotaSeleccionada");
            String paramCuenta = request.getParameter("cuenta");

            if (paramCuota == null || paramCuota.trim().isEmpty() ||
                paramCuenta == null || paramCuenta.trim().isEmpty()) {

                request.getSession().setAttribute("mensaje", "❌ Debe seleccionar una cuenta y una cuota válidas.");
                response.sendRedirect(request.getContextPath() + "/ServletPagarPrestamoCliente");
                return;
            }

            // Parsear parámetros
            int idCuota = Integer.parseInt(paramCuota);
            int nroCuenta = Integer.parseInt(paramCuenta);
            System.out.println("NumeroCuenta= "+nroCuenta);
            boolean exito = cuotaNeg.pagarCuota(idCuota, nroCuenta);

            if (exito) {
            	
                request.getSession().setAttribute("mensaje", "✅ Cuota pagada correctamente.");
                agregarMovimiento(request, idCuota, nroCuenta);

                Cuota cuotaPagada = cuotaNeg.obtenerCuotaPorId(idCuota);
                int idPrestamo = cuotaPagada.getIdPrestamo();

                boolean quedanCuotasPendientes = cuotaNeg.existenCuotasPendientesPorPrestamo(idPrestamo);

                if (!quedanCuotasPendientes) {
                    prestamosNeg.cambiarEstadoPago(idPrestamo, "Pagado");
                    System.out.println("✔ Estado del préstamo " + idPrestamo + " cambiado a Pagado");
                }
            } else {
                request.getSession().setAttribute("mensaje", "❌ Error al pagar la cuota.");
            }

            // Recargar datos actualizados
            List<Cuota> cuotasPendientes = cuotaNeg.obtenerCuotasPendientesPorCuenta(nroCuenta);
            List<Cuenta> cuentas = cuentaNeg.ListarxUsuario(usuario.getIdUsuario());
            Cuenta cuentaActualizada = cuentaNeg.BuscarPorNro(nroCuenta);

            request.getSession().setAttribute("cuentasUsuario", cuentas);
            request.getSession().setAttribute("cuenta", cuentaActualizada);
            request.setAttribute("cuotasPendientes", cuotasPendientes);
            request.setAttribute("cuentasUsuario", cuentas);

            request.getRequestDispatcher("/ClientMode/pagarPrestamoClient.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println("⚠️ Error de formato numérico: " + e.getMessage());
            request.getSession().setAttribute("mensaje", "❌ Error: datos inválidos. Asegúrese de seleccionar una cuenta y una cuota.");
            response.sendRedirect(request.getContextPath() + "/ServletPagarPrestamoCliente");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ServletPagarPrestamoCliente?error=excepcion");
        }
    }
    public void agregarMovimiento(HttpServletRequest request,int idCuota,int nroCuenta)throws ServletException, IOException {
    	Cuenta cuenta = new Cuenta();
    	Cuota cuota = new Cuota();
    	cuota= cuotaNeg.obtenerCuotaPorId(idCuota);
    	cuenta=cuentaNeg.BuscarPorNro(nroCuenta);
    	Movimiento mov= new Movimiento();
    	mov.setCuentaEmisor(cuenta);
    	mov.setDetalle("Pagar cuota");
    	mov.setFecha(LocalDate.now());
    	mov.setImporte(cuota.getImporte().negate());
    	mov.setCuentaReceptor(cuentaNeg.BuscarPorNro(22));
    	mov.setUsuario(mov.getCuentaEmisor().getUsuario());
    	TipoMovimiento tm= new TipoMovimiento();
    	tm.setIdTipoMovimiento(1);
    	mov.setTipoMovimiento(tm);
    	movNeg.movimiento(mov);
    }
}