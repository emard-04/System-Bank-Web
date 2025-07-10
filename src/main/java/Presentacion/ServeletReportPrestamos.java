package Presentacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entidades.Prestamos;
import negocioImpl.PrestamosNegImpl;

@WebServlet("/ServeletReportPrestamos")
public class ServeletReportPrestamos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fechaDesdeStr = request.getParameter("fechaDesde");
        String fechaHastaStr = request.getParameter("fechaHasta");
        String estadoSolicitud = request.getParameter("estadoPrestamo"); 

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
            e.printStackTrace();
        }

        if (fechaDesde == null || fechaHasta == null) {
            Calendar cal = Calendar.getInstance();
            fechaHasta = cal.getTime(); 
            cal.add(Calendar.DAY_OF_MONTH, -30);
            fechaDesde = cal.getTime(); 
            fechaDesdeStr = sdf.format(fechaDesde);
            fechaHastaStr = sdf.format(fechaHasta);
        }
        
        if (estadoSolicitud == null || estadoSolicitud.isEmpty()) {
            estadoSolicitud = "todos";
        }

        PrestamosNegImpl negocio = new PrestamosNegImpl();
        List<Prestamos> prestamos = negocio.obtenerPrestamosPorFechaYEstado(fechaDesde, fechaHasta, estadoSolicitud);

        BigDecimal totalImportePedido = BigDecimal.ZERO;
        BigDecimal totalImporteAPagar = BigDecimal.ZERO;
        int cantidadPrestamos = 0;

        if (prestamos != null) {
            for (Prestamos p : prestamos) {
                totalImportePedido = totalImportePedido.add(p.getImportePedido());
                totalImporteAPagar = totalImporteAPagar.add(p.getImporteApagar());
                cantidadPrestamos++;
            }
        }

        request.setAttribute("prestamos", prestamos);
        request.setAttribute("totalImportePedido", totalImportePedido);
        request.setAttribute("totalImporteAPagar", totalImporteAPagar);
        request.setAttribute("cantidadPrestamos", cantidadPrestamos);
        

        request.setAttribute("fechaDesdeStr", fechaDesdeStr);
        request.setAttribute("fechaHastaStr", fechaHastaStr);
        request.setAttribute("estadoSeleccionado", estadoSolicitud);

        request.getRequestDispatcher("/AdminMode/reportesAdmin.jsp").forward(request, response);
    }
}