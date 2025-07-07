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
            }
        } catch (ParseException e) {
            e.printStackTrace(); // Es buena idea loggear esto para ver si hay errores de formato de fecha
            // Podrías mostrar un mensaje de error al usuario si la fecha es inválida
        }

        // Si las fechas no se proporcionaron o fueron inválidas, establece un rango por defecto
        if (fechaDesde == null || fechaHasta == null) {
            Calendar cal = Calendar.getInstance();
            fechaHasta = cal.getTime(); // Hoy
            cal.add(Calendar.DAY_OF_MONTH, -30); // Resta 30 días
            fechaDesde = cal.getTime(); // Hace 30 días

            // Actualiza los strings para que el formulario los muestre correctamente
            fechaDesdeStr = sdf.format(fechaDesde);
            fechaHastaStr = sdf.format(fechaHasta);
        }

        CuentasNegImpl negocioCuentas = new CuentasNegImpl();

        // --- Obtención de datos de estadísticas ---
        int cantidadCuentasNuevas = negocioCuentas.contarCuentasCreadasEnRango(fechaDesde, fechaHasta);
        BigDecimal promedioSaldoInicial = negocioCuentas.obtenerPromedioSaldoInicialEnRango(fechaDesde, fechaHasta);
        String tipoCuentaMasCreada = negocioCuentas.obtenerTipoCuentaMasCreadaEnRango(fechaDesde, fechaHasta);

        // --- Obtención de la lista detallada de cuentas ---
        // ¡ESTO FALTABA!
        List<Cuenta> cuentasCreadas = negocioCuentas.obtenerCuentasCreadasEnRango(fechaDesde, fechaHasta);
        System.out.println("Cuentas creadas en el rango: " + (cuentasCreadas != null ? cuentasCreadas.size() : "0")); // Para depurar


        // --- Establecer atributos para el JSP ---
        request.setAttribute("cantidadCuentasNuevas", cantidadCuentasNuevas);
        request.setAttribute("promedioSaldoInicial", promedioSaldoInicial);
        request.setAttribute("tipoCuentaMasCreada", tipoCuentaMasCreada);
        request.setAttribute("cuentasCreadas", cuentasCreadas); // ¡AGREGADO ESTO!

        // Para mantener los valores en el formulario después del envío
        request.setAttribute("cuentaFechaDesdeStr", fechaDesdeStr);
        request.setAttribute("cuentaFechaHastaStr", fechaHastaStr);
        
        // Para mantener la pestaña activa en el JSP
        request.setAttribute("activeReport", "cuentas");
        
        System.out.println("DEBUG: ServeletReporteCuentas va a reenviar a /AdminMode/reportesAdminCuentas.jsp"); // <-- AÑADE ESTA LÍNEA
        
        // Redirigir al JSP
        request.getRequestDispatcher("/AdminMode/reportesAdminCuentas.jsp").forward(request, response);
    }
}
