package Presentacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entidades.Cuenta;
import Entidades.Movimiento;
import Entidades.TipoMovimiento;
import Entidades.Usuario;
import negocio.MovimientoNeg;
import negocioImpl.CuentasNegImpl;
import negocioImpl.MovimientoNegImpl;

/**
 * Servlet implementation class ServletTransferencia
 */
@WebServlet("/ServletTransferencia")
public class ServletTransferencia extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CuentasNegImpl nCuenta= new CuentasNegImpl();
	private final MovimientoNeg nMovimiento= new MovimientoNegImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTransferencia() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Movimiento movEmisor= new Movimiento();
		movEmisor.setDetalle(request.getParameter("referencia"));
		TipoMovimiento tm= new TipoMovimiento();
		tm.setIdTipoMovimiento(3);
		movEmisor.setTipoMovimiento(tm);
		movEmisor.setFecha(LocalDate.now());
		movEmisor.setCuentaEmisor(nCuenta.BuscarPorNro(Integer.parseInt(request.getParameter("cuentaSeleccionada"))));
		Cuenta c=nCuenta.cuentaxCbu(request.getParameter("alias_cbu"));
		if(c==null)return;
		movEmisor.setCuentaReceptor(c);
		BigDecimal monto = new BigDecimal(request.getParameter("monto"));
		movEmisor.setImporte(monto);
		movEmisor.setUsuario((Usuario)request.getSession().getAttribute("usuarioLogueado"));
		System.out.println(movEmisor.getUsuario());
		boolean exitoEmisor=nMovimiento.Agregar(movEmisor);
		Movimiento movReceptor= new Movimiento();
		movReceptor.setImporte(monto);
		movReceptor.setDetalle(request.getParameter("referencia"));
		movReceptor.setCuentaEmisor(c);
		movReceptor.setCuentaReceptor(nCuenta.BuscarPorNro(Integer.parseInt(request.getParameter("cuentaSeleccionada"))));
		movReceptor.setUsuario(c.getUsuario());
		movReceptor.setTipoMovimiento(tm);
		movReceptor.setFecha(LocalDate.now());
		boolean exitoReceptor=nMovimiento.Agregar(movReceptor);
		if(exitoEmisor&&exitoReceptor) {
			request.setAttribute("mensaje", "✅ Transferencia realizada");
			RequestDispatcher rd=request.getRequestDispatcher("ClientMode/TransferenciaClient.jsp");
			rd.forward(request, response);
			return;
		}
		request.setAttribute("mensaje", "❌ Error al realizar la transferencia.");
		RequestDispatcher rd=request.getRequestDispatcher("ClientMode/TransferenciaClient.jsp");
		rd.forward(request, response);
		doGet(request, response);
	}

}
