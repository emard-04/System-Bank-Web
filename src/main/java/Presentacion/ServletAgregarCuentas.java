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

import Daos.daoCuentas;
import Entidades.*;
import negocioImpl.CuentasNegImpl;
import Daos.*;

/**
 * Servlet implementation class ServletAgregarCuentas
 */
@WebServlet("/ServletAgregarCuentas")
public class ServletAgregarCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final CuentasNegImpl negCuenta= new CuentasNegImpl(); 
    public ServletAgregarCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("openAgregar")!=null) {
			windowDefault(request,response);
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

        // 2. Convertir tipo de cuenta a id (esto depende de cómo estén tus IDs en la tabla TipoCuenta)
        int idTipoCuenta = tipoCuentaTexto.equalsIgnoreCase("Corriente") ? 1 : 2;

        // 3. Buscar el usuario por DNI (suponiendo que tenés un método para eso)
        daoUsuario daoU = new daoUsuario();
        Usuario usuario = daoU.BuscarDni(dniCliente);// Este método lo tenés que tener implementado

        // Validación simple por si no se encuentra
        
        if (usuario == null) {
            response.sendRedirect("/BancoParcial/AdminMode/cuentaAdmin_agregar.jsp?error=Usuario no encontrado ");
            return;
        }

        // 4. Armar objeto Cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setNroCuenta(nroCuenta);
        cuenta.setUsuario(usuario);
        cuenta.setFechaCreacion(fechaCreacion);

        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setIdTipoCuenta(idTipoCuenta);
        cuenta.setTipoCuenta(tipoCuenta);

        cuenta.setCbu(cbu);
        cuenta.setSaldo(saldo);

        // 5. Guardar cuenta
        daoCuentas dao = new daoCuentas();
        boolean exito = dao.Agregar(cuenta);

        // 6. Redirigir según resultado
        String contextPath = request.getContextPath();
        if (usuario == null) {
            response.sendRedirect(contextPath + "/AdminMode/cuentaAdmin_agregar.jsp?error=UsuarioNoEncontrado2");
            return;
        }

        if (exito) {
            response.sendRedirect(contextPath + "/AdminMode/HomeAdmin.jsp?msg=CuentaAgregada");
        } else {
            response.sendRedirect(contextPath + "/AdminMode/cuentaAdmin_agregar.jsp?error=ErrorAlAgregar");
        }
    }
	private void windowDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 request.setAttribute("nroCuenta",negCuenta.obtenerId());
		 RequestDispatcher rd= request.getRequestDispatcher("/AdminMode/cuentaAdmin_agregar.jsp");
		 rd.forward(request, response);
	}
	

}
