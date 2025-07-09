package Presentacion;

import java.io.IOException;
import Entidades.*;
import negocio.ClientesNeg;
import negocio.CuentasNeg;
import negocio.TipoCuentaNeg;
import negocio.UsuarioNeg;
import negocioImpl.CuentasNegImpl;
import negocioImpl.TipoCuentaNegImpl;
import negocioImpl.UsuarioNegImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jdi.request.InvalidRequestStateException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Formatter.BigDecimalLayoutForm;
/**
 * Servlet implementation class ServletModificarCuentas
 */
@WebServlet("/ServletModificarCuentas")
public class ServletModificarCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CuentasNeg cuentaNeg= new CuentasNegImpl();
	private UsuarioNeg usuarioNeg= new UsuarioNegImpl();
	private TipoCuentaNeg TipocuentaNeg= new TipoCuentaNegImpl();
   
    public ServletModificarCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("openModificar")!=null) {
			windowDefault(request, response);
		}
		if(request.getParameter("nroCuenta")!=null) {
			Cuenta cuenta= new Cuenta();
			int nroCuenta=Integer.parseInt(request.getParameter("nroCuenta"));
			cuenta=cuentaNeg.BuscarPorNro(nroCuenta);
			 String json = "{"
                     + "\"nroCuenta\": \"" + cuenta.getNroCuenta() + "\","
                     + "\"dniCliente\": \"" + cuenta.getUsuario().getPersona().getDni() + "\","
                     + "\"fechaCreacion\": \"" + cuenta.getFechaCreacion() + "\","
                     + "\"tipoCuenta\": \"" + cuenta.getTipoCuenta().getIdTipoCuenta() + "\","
                     + "\"tipoCuentaDescripcion\": \"" + cuenta.getTipoCuenta().getDescripcion() + "\","
                     + "\"cbu\": \"" + cuenta.getCbu() + "\","
                     + "\"saldo\": \"" + cuenta.getSaldo() + "\""
                     + "}";
			 response.setContentType("application/json");
             response.setCharacterEncoding("UTF-8");
             response.getWriter().write(json);
			
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		request.getSession().removeAttribute("mensaje");
        try {
            int nroCuenta = Integer.parseInt(request.getParameter("nro_cuenta_mod"));
            String dniCliente = request.getParameter("dni_cliente_mod");
            LocalDate fechaCreacion = LocalDate.parse(request.getParameter("fecha_creacion_mod"));
            String tipoCuentaTexto = request.getParameter("tipo_cuenta_mod");
            String cbu = request.getParameter("cbu_mod");
            BigDecimal saldo = new BigDecimal(request.getParameter("saldo_mod"));
            Usuario usuario = usuarioNeg.BuscarDni(dniCliente);
            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_modificar.jsp?error=Usuario no encontrado");
                return;
            }
            Cuenta cuenta = new Cuenta();
            cuenta.setNroCuenta(nroCuenta);
            cuenta.setUsuario(usuario);
            cuenta.setFechaCreacion(fechaCreacion);
            TipoCuenta tipoCuenta = TipocuentaNeg.buscarXDescripcion(tipoCuentaTexto);
            cuenta.setTipoCuenta(tipoCuenta);
            cuenta.setCbu(cbu);
            cuenta.setSaldo(saldo);
            boolean exito = cuentaNeg.Modificar(cuenta);
            if (exito) {
            	request.setAttribute("ListaCuenta", cuentaNeg.ListarTodo());
            	request.setAttribute("mensaje", "✅ Cuenta modificada correctamente.");
           	 String ventana="/AdminMode/cuentaAdmin_modificar.jsp";
              // windowDefault(request, response, ventana);
               RequestDispatcher rd = request.getRequestDispatcher(ventana);
       		rd.forward(request, response);
                //response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_modificar.jsp");
            } else {
            	request.setAttribute("ListaCuenta", cuentaNeg.ListarTodo());
                //response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_modificar.jsp?error=Error al modificar la cuenta");
                request.setAttribute("mensaje", "❌ Error al modificar cuenta.");
                String ventana="/AdminMode/cuentaAdmin_modificar.jsp?error=Error al modificar la cuenta";
                //windowDefault(request, response, ventana);
                RequestDispatcher rd = request.getRequestDispatcher(ventana);
        		rd.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_modificar.jsp?error=Error inesperado");
        }
    }
	private void windowDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("ListaCuenta", cuentaNeg.ListarTodo());
		RequestDispatcher rd= request.getRequestDispatcher("/AdminMode/cuentaAdmin_modificar.jsp");
		rd.forward(request, response);
		
	}

}
	