package Presentacion;

import java.io.IOException;
import Entidades.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Daos.*;
import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * Servlet implementation class ServletModificarCuentas
 */
@WebServlet("/ServletModificarCuentas")
public class ServletModificarCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private daoCuentas daoCuenta = new daoCuentas();
   
    public ServletModificarCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Obtener parámetros del formulario
            int nroCuenta = Integer.parseInt(request.getParameter("nro_cuenta_mod"));
            String dniCliente = request.getParameter("dni_cliente_mod");
            LocalDate fechaCreacion = LocalDate.parse(request.getParameter("fecha_creacion_mod"));
            String tipoCuentaTexto = request.getParameter("tipo_cuenta_mod");
            String cbu = request.getParameter("cbu_mod");
            BigDecimal saldo = new BigDecimal(request.getParameter("saldo_mod"));

            // 2. Obtener idTipoCuenta según texto
            int idTipoCuenta = tipoCuentaTexto.equalsIgnoreCase("Corriente") ? 1 : 2;

            // 3. Buscar usuario por DNI (asumiendo que tienes daoUsuario y método BuscarDni)
            daoUsuario daoU = new daoUsuario();
            Usuario usuario = daoU.BuscarDni(dniCliente);
            if (usuario == null) {
                // Si no existe el usuario, redirigir con error
                response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_modificar.jsp?error=Usuario no encontrado");
                return;
            }

            // 4. Armar objeto Cuenta con los datos actualizados
            Cuenta cuenta = new Cuenta();
            cuenta.setNroCuenta(nroCuenta);
            cuenta.setUsuario(usuario);
            cuenta.setFechaCreacion(fechaCreacion);

            TipoCuenta tipoCuenta = new TipoCuenta();
            tipoCuenta.setIdTipoCuenta(idTipoCuenta);
            cuenta.setTipoCuenta(tipoCuenta);

            cuenta.setCbu(cbu);
            cuenta.setSaldo(saldo);

            // 5. Actualizar cuenta en base de datos (debe estar implementado daoCuentas.Modificar o similar)
            boolean exito = daoCuenta.Modificar(cuenta);

            // 6. Redirigir según resultado
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/AdminMode/cuentasAdmin.jsp?msg=CuentaModificada");
            } else {
                response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_modificar.jsp?error=Error al modificar la cuenta");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/AdminMode/cuentaAdmin_modificar.jsp?error=Error inesperado");
        }
    }

}
