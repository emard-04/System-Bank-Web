package Presentacion;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entidades.Cuenta;
import negocioImpl.CuentasNegImpl;

/**
 * Servlet implementation class ServeletReporteCuentas
 */
@WebServlet("/ServeletReporteCuentas")
public class ServeletReporteCuentas extends HttpServlet {

	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fechaDesdeStr = request.getParameter("fechaDesde");
        String fechaHastaStr = request.getParameter("fechaHasta");

        Date fechaDesde = null;
        Date fechaHasta = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (fechaDesdeStr != null && !fechaDesdeStr.isEmpty()) {
                fechaDesde = sdf.parse(fechaDesdeStr);
            }
            if (fechaHastaStr != null && !fechaHastaStr.isEmpty()) {
                fechaHasta = sdf.parse(fechaHastaStr);
                Calendar calHasta = Calendar.getInstance();
                calHasta.setTime(fechaHasta);
                calHasta.set(Calendar.HOUR_OF_DAY, 23);
                calHasta.set(Calendar.MINUTE, 59);
                calHasta.set(Calendar.SECOND, 59);
                calHasta.set(Calendar.MILLISECOND, 999);
                fechaHasta = calHasta.getTime();
             
            }
        } catch (ParseException e) {
            e.printStackTrace(); 
        }

        if (fechaDesde == null || fechaHasta == null) {
            Calendar cal = Calendar.getInstance();
            fechaHasta = cal.getTime(); 

            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            fechaHasta = cal.getTime();


            cal.add(Calendar.DAY_OF_MONTH, -30); 
            fechaDesde = cal.getTime(); 

            fechaDesdeStr = sdf.format(fechaDesde);
            fechaHastaStr = sdf.format(fechaHasta);
        }

        CuentasNegImpl negocioCuentas = new CuentasNegImpl();

        int cantidadCuentasNuevas = negocioCuentas.contarCuentasCreadasEnRango(fechaDesde, fechaHasta);
        BigDecimal promedioSaldoInicial = negocioCuentas.obtenerPromedioSaldoInicialEnRango(fechaDesde, fechaHasta);
        String tipoCuentaMasCreada = negocioCuentas.obtenerTipoCuentaMasCreadaEnRango(fechaDesde, fechaHasta);

        List<Cuenta> cuentasCreadas = negocioCuentas.obtenerCuentasCreadasEnRango(fechaDesde, fechaHasta);

        System.out.println("DEBUG Servlet: Obtenidas " + (cuentasCreadas != null ? cuentasCreadas.size() : "0 (NULL)") + " cuentas del negocio.");
        if (cuentasCreadas != null && !cuentasCreadas.isEmpty()) {
            System.out.println("DEBUG Servlet: Primera cuenta en la lista: NroCuenta=" + cuentasCreadas.get(0).getNroCuenta() + ", Usuario=" + cuentasCreadas.get(0).getUsuario().getPersona().getNombre());
        }

        request.setAttribute("cantidadCuentasNuevas", cantidadCuentasNuevas);
        request.setAttribute("promedioSaldoInicial", promedioSaldoInicial);
        request.setAttribute("tipoCuentaMasCreada", tipoCuentaMasCreada);
        request.setAttribute("cuentasCreadas", cuentasCreadas);

        request.setAttribute("cuentaFechaDesdeStr", fechaDesdeStr);
        request.setAttribute("cuentaFechaHastaStr", fechaHastaStr);
        

        request.setAttribute("activeReport", "cuentas");
        
        System.out.println("DEBUG: ServeletReporteCuentas va a reenviar a /AdminMode/reportesAdminCuentas.jsp");
        
        request.getRequestDispatcher("/AdminMode/reportesAdminCuentas.jsp").forward(request, response);
    }
}
