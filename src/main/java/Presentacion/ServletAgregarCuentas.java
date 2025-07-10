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
import Entidades.*;
import negocio.MovimientoNeg;
import negocio.TipoCuentaNeg;
import negocio.UsuarioNeg;
import negocioImpl.CuentasNegImpl;
import negocioImpl.MovimientoNegImpl;
import negocioImpl.TipoCuentaNegImpl;
import negocioImpl.UsuarioNegImpl;

/**
 * Servlet implementation class ServletAgregarCuentas
 */
@WebServlet("/ServletAgregarCuentas")
public class ServletAgregarCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final UsuarioNeg negUsuario= new UsuarioNegImpl(); 
	private static final CuentasNegImpl negCuenta= new CuentasNegImpl(); 
	private static final TipoCuentaNeg negTipoCuenta= new TipoCuentaNegImpl(); 
	private static final MovimientoNeg negMovimiento= new MovimientoNegImpl(); 
    public ServletAgregarCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("openAgregar")!=null) {
			String ventana="AdminMode/cuentaAdmin_agregar.jsp";
			windowDefault(request,response, ventana);//Cargar ventana agregar cuenta
			}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int nroCuenta = Integer.parseInt(request.getParameter("nro_cuenta"));
        String dniCliente = request.getParameter("dni_cliente");
        LocalDate fechaCreacion = LocalDate.parse(request.getParameter("fecha_creacion"));
        String tipoCuentaTexto = request.getParameter("tipo_cuenta");
        String cbu = request.getParameter("cbu");
        BigDecimal saldo = new BigDecimal(request.getParameter("saldo"));


        UsuarioNeg negUsuario = new UsuarioNegImpl();
        Usuario usuario = negUsuario.BuscarDni(dniCliente) ;

        
        if (usuario == null) {
        	String ventana="AdminMode/cuentaAdmin_agregar.jsp?error=Usuario no encontrado ";
        	windowDefault(request, response, ventana);
        }

        Cuenta cuenta = new Cuenta();
        cuenta.setNroCuenta(nroCuenta);
        cuenta.setUsuario(usuario);
        cuenta.setFechaCreacion(fechaCreacion);
        TipoCuenta tipoCuenta = negTipoCuenta.buscarXDescripcion(tipoCuentaTexto);
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setCbu(cbu);
        cuenta.setSaldo(saldo);


        CuentasNegImpl negCuenta=new CuentasNegImpl();
        boolean exito = negCuenta.Agregar(cuenta);


        if (exito) {
        	agregarMovimiento(cuenta);
        	request.setAttribute("mensaje", "✅ Cuenta agregada correctamente.");
        	 String ventana="/AdminMode/cuentaAdmin_agregar.jsp";

            RequestDispatcher rd = request.getRequestDispatcher(ventana);
            request.setAttribute("nroCuenta",negCuenta.obtenerId());
   		 request.setAttribute("nroCBU", negCuenta.generarCBU());
    		rd.forward(request, response);
        } else {
        	request.setAttribute("mensaje", "❌ Error al agregar cuenta.");
            String ventana="/AdminMode/cuentaAdmin_agregar.jsp?error=Error al agregar";

            RequestDispatcher rd = request.getRequestDispatcher(ventana);
    		rd.forward(request, response);
        }
    }
	private void windowDefault(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException{
		 request.setAttribute("nroCuenta",negCuenta.obtenerId());
		 request.setAttribute("nroCBU", negCuenta.generarCBU());
		 RequestDispatcher rd= request.getRequestDispatcher(jsp);
		 rd.forward(request, response);
	}
	private void agregarMovimiento(Cuenta cuenta) {
		Movimiento movimiento=new Movimiento();
		TipoMovimiento tm= new TipoMovimiento();
		movimiento.setCuentaEmisor(cuenta);
		movimiento.setDetalle("Creacion de cuenta");
		movimiento.setCuentaReceptor(negCuenta.BuscarPorNro(1));
		tm.setIdTipoMovimiento(4);
		movimiento.setTipoMovimiento(tm);
		movimiento.setImporte(cuenta.getSaldo());
		movimiento.setFecha(LocalDate.now());
		movimiento.setUsuario(cuenta.getUsuario());
		negMovimiento.movimiento(movimiento);
	}

}
