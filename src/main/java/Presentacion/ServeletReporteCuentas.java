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
                // *** INICIO DE AJUSTE CLAVE PARA FECHA HASTA CUANDO VIENE DEL FORMULARIO ***
                Calendar calHasta = Calendar.getInstance();
                calHasta.setTime(fechaHasta);
                calHasta.set(Calendar.HOUR_OF_DAY, 23);
                calHasta.set(Calendar.MINUTE, 59);
                calHasta.set(Calendar.SECOND, 59);
                calHasta.set(Calendar.MILLISECOND, 999);
                fechaHasta = calHasta.getTime();
                // *** FIN DE AJUSTE CLAVE ***
            }
        } catch (ParseException e) {
            e.printStackTrace(); // Es buena idea loggear esto para ver si hay errores de formato de fecha
            // Podrías mostrar un mensaje de error al usuario si la fecha es inválida
        }

        // Si las fechas no se proporcionaron o fueron inválidas, establece un rango por defecto (últimos 30 días)
        if (fechaDesde == null || fechaHasta == null) {
            Calendar cal = Calendar.getInstance();
            fechaHasta = cal.getTime(); // Obtiene la fecha y hora actual

            // *** INICIO DE AJUSTE CLAVE PARA FECHA HASTA CUANDO ES POR DEFECTO ***
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            fechaHasta = cal.getTime(); // Asigna el final del día actual a fechaHasta
            // *** FIN DE AJUSTE CLAVE ***

            cal.add(Calendar.DAY_OF_MONTH, -30); // Resta 30 días a la fecha del calendario
            fechaDesde = cal.getTime(); // La fechaDesde será 30 días antes de la fechaHasta ajustada

            // Actualiza los strings para que el formulario los muestre correctamente (solo la parte de la fecha)
            fechaDesdeStr = sdf.format(fechaDesde);
            fechaHastaStr = sdf.format(fechaHasta);
        }

        CuentasNegImpl negocioCuentas = new CuentasNegImpl();

        // --- Obtención de datos de estadísticas ---
        int cantidadCuentasNuevas = negocioCuentas.contarCuentasCreadasEnRango(fechaDesde, fechaHasta);
        BigDecimal promedioSaldoInicial = negocioCuentas.obtenerPromedioSaldoInicialEnRango(fechaDesde, fechaHasta);
        String tipoCuentaMasCreada = negocioCuentas.obtenerTipoCuentaMasCreadaEnRango(fechaDesde, fechaHasta);

        // --- Obtención de la lista detallada de cuentas ---
        List<Cuenta> cuentasCreadas = negocioCuentas.obtenerCuentasCreadasEnRango(fechaDesde, fechaHasta);

        // AÑADE ESTAS LÍNEAS PARA DEPURACIÓN:
        System.out.println("DEBUG Servlet: Obtenidas " + (cuentasCreadas != null ? cuentasCreadas.size() : "0 (NULL)") + " cuentas del negocio.");
        if (cuentasCreadas != null && !cuentasCreadas.isEmpty()) {
            System.out.println("DEBUG Servlet: Primera cuenta en la lista: NroCuenta=" + cuentasCreadas.get(0).getNroCuenta() + ", Usuario=" + cuentasCreadas.get(0).getUsuario().getPersona().getNombre());
        }


        // --- Establecer atributos para el JSP ---
        request.setAttribute("cantidadCuentasNuevas", cantidadCuentasNuevas);
        request.setAttribute("promedioSaldoInicial", promedioSaldoInicial);
        request.setAttribute("tipoCuentaMasCreada", tipoCuentaMasCreada);
        request.setAttribute("cuentasCreadas", cuentasCreadas);

        // Para mantener los valores en el formulario después del envío
        request.setAttribute("cuentaFechaDesdeStr", fechaDesdeStr);
        request.setAttribute("cuentaFechaHastaStr", fechaHastaStr);
        
        // Para mantener la pestaña activa en el JSP
        request.setAttribute("activeReport", "cuentas");
        
        System.out.println("DEBUG: ServeletReporteCuentas va a reenviar a /AdminMode/reportesAdminCuentas.jsp");
        
        // Redirigir al JSP
        request.getRequestDispatcher("/AdminMode/reportesAdminCuentas.jsp").forward(request, response);
    }
}
