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
import negocio.TipoCuentaNeg;
import negocio.UsuarioNeg;
import negocioImpl.CuentasNegImpl;
import negocioImpl.TipoCuentaNegImpl;
import negocioImpl.UsuarioNegImpl;

/**
 * Servlet implementation class ServletAgregarCuentas
 */
@WebServlet("/ServletAgregarCuentas")
public class ServletAgregarCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final CuentasNegImpl negCuenta= new CuentasNegImpl(); 
	private static final TipoCuentaNeg negTipoCuenta= new TipoCuentaNegImpl(); 
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtener parámetros del formulario
        int nroCuenta = Integer.parseInt(request.getParameter("nro_cuenta"));
        String dniCliente = request.getParameter("dni_cliente");
        LocalDate fechaCreacion = LocalDate.parse(request.getParameter("fecha_creacion"));
        String tipoCuentaTexto = request.getParameter("tipo_cuenta");
        String cbu = request.getParameter("cbu");
        BigDecimal saldo = new BigDecimal(request.getParameter("saldo"));


        // 3. Buscar el usuario por DNI (suponiendo que tenés un método para eso)
        UsuarioNeg negUsuario = new UsuarioNegImpl();
        Usuario usuario = negUsuario.BuscarDni(dniCliente) ;//Este método lo tenés que tener implementado

        // Validación simple por si no se encuentra
        
        if (usuario == null) {
        	String ventana="AdminMode/cuentaAdmin_agregar.jsp?error=Usuario no encontrado ";
        	windowDefault(request, response, ventana);
        }

        // 4. Armar objeto Cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setNroCuenta(nroCuenta);
        cuenta.setUsuario(usuario);
        cuenta.setFechaCreacion(fechaCreacion);
        TipoCuenta tipoCuenta = negTipoCuenta.buscarXDescripcion(tipoCuentaTexto);
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setCbu(cbu);
        cuenta.setSaldo(saldo);

        // 5. Guardar cuenta
        CuentasNegImpl negCuenta=new CuentasNegImpl();
        boolean exito = negCuenta.Agregar(cuenta);

        // 6. Redirigir según resultado
        if (exito) {
        	 String ventana="/AdminMode/cuentaAdmin_agregar.jsp";
            windowDefault(request, response, ventana);
        } else {
            String ventana="/AdminMode/cuentaAdmin_agregar.jsp?error=Error al agregar";
            windowDefault(request, response, ventana);
        }
    }
	private void windowDefault(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException{
		 request.setAttribute("nroCuenta",negCuenta.obtenerId());
		 request.setAttribute("nroCBU", negCuenta.generarCBU());
		 RequestDispatcher rd= request.getRequestDispatcher(jsp);
		 rd.forward(request, response);
	}
	

}
