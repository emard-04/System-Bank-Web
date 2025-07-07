<%@ page import="Entidades.Usuario" %>
<%@ page import="Entidades.Persona" %>
<%@ page import="Entidades.Prestamos" %> <%-- ¡IMPORTANTE! Asegúrate que tu clase sea Prestamo, no Prestamos --%>
<%@ page import="Entidades.Cuenta" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // AHORA DECLARAMOS E INICIALIZAMOS currentActiveReportType UNA SOLA VEZ AQUÍ.
    // Estará disponible para el resto del contenido del body.
    String currentActiveReportType = (String) request.getAttribute("activeReport");
    if (currentActiveReportType == null || currentActiveReportType.isEmpty()) {
        currentActiveReportType = "prestamos"; // Valor por defecto
    }
%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reportes Admin - Tu Banco</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        function confirmarLogout(e) {
            e.preventDefault();
            if (confirm("¿Estás seguro de que quieres cerrar sesión?")) {
                window.location.href = "<%=request.getContextPath()%>/ServletLogout";
            }
        }
    </script>
</head>
<body class="bg-gray-100 h-screen overflow-hidden">
    <div class="flex h-full">

        <aside class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">
            
           <img src="<%=request.getContextPath()%>/img/perfilAdmi.webp"
     alt="Foto de perfil"
     class="w-32 h-32 rounded-full object-cover mb-4 border-4 border-gray-300 shadow-md">
            
             <h3 class="text-xl font-bold text-gray-800 text-center mb-6">
    <%= usuarioLogueado.getPersona().getNombre() %> <%= usuarioLogueado.getPersona().getApellido() %>
</h3>
            
            	<a href="#"
   onclick="confirmarLogout(event)"
   class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
   Salir
</a>
        </aside>

        <main class="flex-1 flex flex-col overflow-y-auto">
            
            <header class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
                <h1 class="text-xl font-semibold text-gray-800">REPORTES</h1>
                <div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
            </header>

            <nav class="bg-gray-50 border-b border-gray-200 p-4">
                <ul class="flex space-x-10 justify-center">
                   <li><a
						href="<%=request.getContextPath()%>/AdminMode/HomeAdmin.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Home</a>
					</li>
                    <li><a href="<%=request.getContextPath()%>/ServletListarClientes?openListar=1&pagina=1" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Clientes</a></li>
                    <li><a href="<%=request.getContextPath()%>/ServletListarCuentas?openListar=1&pagina=1" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Cuentas</a></li>
                    <li><a href="<%=request.getContextPath()%>/ServletPrestamosAdmi" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Préstamos</a></li>
                    <li><a href="<%=request.getContextPath()%>/ServeletReportPrestamos?activeReport=prestamos" class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Reportes</a></li>
                </ul>
            </nav>

            <nav class="bg-gray-100 border-b border-gray-200 p-3">
				<ul class="flex space-x-4 justify-center">
                    <li>
                        <a href="<%=request.getContextPath()%>/ServeletReportPrestamos?activeReport=prestamos"
                           class="font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out
                           <%= "prestamos".equals(currentActiveReportType) ? "bg-blue-500 text-white" : "hover:bg-gray-200 text-gray-700" %>">
                            Reporte Préstamos
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/ServeletReporteCuentas?activeReport=cuentas"
                           class="font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out
                           <%= "cuentas".equals(currentActiveReportType) ? "bg-blue-500 text-white" : "hover:bg-gray-200 text-gray-700" %>">
                            Reporte Cuentas
                        </a>
                    </li>
				</ul>
			</nav>

            <div class="p-6 flex-1 flex flex-col items-center overflow-auto">
                <h2 class="text-2xl font-bold text-gray-700 mb-6">
                    <%
                        if ("cuentas".equals(currentActiveReportType)) {
                            out.print("Reporte de Cuentas Creadas");
                        } else {
                            out.print("Reporte de Préstamos"); 
                        }
                    %>
                </h2>

                <div id="prestamos-report-content" class="w-full max-w-6xl report-content-section <%= "prestamos".equals(currentActiveReportType) ? "" : "hidden" %>">
                    <form action="<%=request.getContextPath()%>/ServeletReportPrestamos" method="GET" class="space-y-4">
                        <input type="hidden" name="activeReport" value="prestamos">
                        <div class="flex flex-wrap -mx-2 mb-4">
                            <div class="w-full md:w-1/2 px-2 mb-4 md:mb-0">
                                <label for="fechaDesdePrestamos" class="block text-gray-700 text-sm font-bold mb-2">Fecha Desde:</label>
                                <input type="date" id="fechaDesdePrestamos" name="fechaDesde"
                                       value="<%= request.getAttribute("prestamosFechaDesdeStr") != null ? request.getAttribute("prestamosFechaDesdeStr") : "" %>"
                                       class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                            </div>
                            <div class="w-full md:w-1/2 px-2">
                                <label for="fechaHastaPrestamos" class="block text-gray-700 text-sm font-bold mb-2">Fecha Hasta:</label>
                                <input type="date" id="fechaHastaPrestamos" name="fechaHasta"
                                       value="<%= request.getAttribute("prestamosFechaHastaStr") != null ? request.getAttribute("prestamosFechaHastaStr") : "" %>"
                                       class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                            </div>
                        </div>
                        <div>
                            <label for="estadoPrestamo" class="block text-gray-700 text-sm font-bold mb-2">Estado de la Solicitud:</label>
                            <select id="estadoPrestamo" name="estadoPrestamo"
                                    class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                                <option value="todos" <%= "todos".equals(request.getAttribute("prestamosEstadoSeleccionado")) ? "selected" : "" %>>Todos</option>
                                <option value="Pendiente" <%= "Pendiente".equals(request.getAttribute("prestamosEstadoSeleccionado")) ? "selected" : "" %>>Pendiente</option>
                                <option value="Aprobado" <%= "Aprobado".equals(request.getAttribute("prestamosEstadoSeleccionado")) ? "selected" : "" %>>Aprobado</option>
                                <option value="Rechazado" <%= "Rechazado".equals(request.getAttribute("prestamosEstadoSeleccionado")) ? "selected" : "" %>>Rechazado</option>
                                <option value="Cancelado" <%= "Cancelado".equals(request.getAttribute("prestamosEstadoSeleccionado")) ? "selected" : "" %>>Cancelado</option>
                            </select>
                        </div>
                        <div class="flex justify-center">
                            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-6 rounded focus:outline-none focus:shadow-outline">
                                Generar Reporte
                            </button>
                        </div>
                    </form>
                </div>

                <%-- Resultados del Reporte de Préstamos --%>
                <%
                    List<Entidades.Prestamos> listaPrestamos = (List<Entidades.Prestamos>) request.getAttribute("listaPrestamos");
                    BigDecimal cantidadPrestamos = (BigDecimal) request.getAttribute("cantidadPrestamosOtorgados");
                    BigDecimal montoTotalPrestamos = (BigDecimal) request.getAttribute("montoTotalPrestamos");
                    BigDecimal promedioMontoPrestamos = (BigDecimal) request.getAttribute("promedioMontoPrestamos");
                    BigDecimal montoMaximoPrestamo = (BigDecimal) request.getAttribute("montoMaximoPrestamo");
                    BigDecimal montoMinimoPrestamo = (BigDecimal) request.getAttribute("montoMinimoPrestamo");
                    Integer cantidadPrestamosCancelados = (Integer) request.getAttribute("cantidadPrestamosCancelados");
                    Integer cantidadPrestamosActivos = (Integer) request.getAttribute("cantidadPrestamosActivos");


                    if (listaPrestamos != null && !listaPrestamos.isEmpty()) {
                %>
                <h3 class="text-xl font-bold text-gray-700 mb-4 mt-6">Estadísticas del Reporte de Préstamos</h3>
                <div class="bg-white p-6 rounded-lg shadow-md w-full max-w-6xl mb-8 flex justify-around items-center text-center">
                    <div class="p-4 bg-blue-50 border border-blue-200 rounded-md shadow-sm">
                        <p class="text-lg font-semibold text-gray-800">Cantidad Préstamos Otorgados:</p>
                        <p class="text-3xl font-bold text-blue-700"><%= cantidadPrestamos != null ? cantidadPrestamos.intValue() : 0 %></p>
                    </div>
                    <div class="p-4 bg-green-50 border border-green-200 rounded-md shadow-sm">
                        <p class="text-lg font-semibold text-gray-800">Monto Total Pedido:</p>
                        <p class="text-3xl font-bold text-green-700">$<%= montoTotalPrestamos != null ? String.format("%,.2f", montoTotalPrestamos) : "0.00" %></p>
                    </div>
                    <div class="p-4 bg-purple-50 border border-purple-200 rounded-md shadow-sm">
                        <p class="text-lg font-semibold text-gray-800">Promedio Monto:</p>
                        <p class="text-3xl font-bold text-purple-700">$<%= promedioMontoPrestamos != null ? String.format("%,.2f", promedioMontoPrestamos) : "0.00" %></p>
                    </div>
                </div>
                 <div class="bg-white p-6 rounded-lg shadow-md w-full max-w-6xl mb-8 flex justify-around items-center text-center">
                     <div class="p-4 bg-yellow-50 border border-yellow-200 rounded-md shadow-sm">
                        <p class="text-lg font-semibold text-gray-800">Monto Máximo:</p>
                        <p class="text-3xl font-bold text-yellow-700">$<%= montoMaximoPrestamo != null ? String.format("%,.2f", montoMaximoPrestamo) : "0.00" %></p>
                    </div>
                    <div class="p-4 bg-red-50 border border-red-200 rounded-md shadow-sm">
                        <p class="text-lg font-semibold text-gray-800">Monto Mínimo:</p>
                        <p class="text-3xl font-bold text-red-700">$<%= montoMinimoPrestamo != null ? String.format("%,.2f", montoMinimoPrestamo) : "0.00" %></p>
                    </div>
                     <div class="p-4 bg-gray-50 border border-gray-200 rounded-md shadow-sm">
                        <p class="text-lg font-semibold text-gray-800">Préstamos Cancelados:</p>
                        <p class="text-3xl font-bold text-gray-700"><%= cantidadPrestamosCancelados != null ? cantidadPrestamosCancelados : 0 %></p>
                    </div>
                    <div class="p-4 bg-cyan-50 border border-cyan-200 rounded-md shadow-sm">
                        <p class="text-lg font-semibold text-gray-800">Préstamos Activos:</p>
                        <p class="text-3xl font-bold text-cyan-700"><%= cantidadPrestamosActivos != null ? cantidadPrestamosActivos : 0 %></p>
                    </div>
                </div>

                <h3 class="text-xl font-bold text-gray-700 mb-4">Detalle de Préstamos</h3>
                <div class="bg-white p-6 rounded-lg shadow-md w-full max-w-6xl overflow-auto">
                    <table class="min-w-full bg-white text-sm text-gray-700 border border-gray-300 rounded-lg">
                        <thead class="bg-gray-100 text-gray-600 uppercase text-xs leading-normal">
                            <tr>
                                <th class="py-2 px-4 border-b">ID</th>
                                <th class="py-2 px-4 border-b">Fecha</th>
                                <th class="py-2 px-4 border-b">Importe Pedido</th>
                                <th class="py-2 px-4 border-b">Importe a Pagar</th>
                                <th class="py-2 px-4 border-b">Plazo</th>
                                <th class="py-2 px-4 border-b">Monto Cuotas</th>
                                <th class="py-2 px-4 border-b">Estado Solicitud</th>
                                <th class="py-2 px-4 border-b">Estado Pago</th>
                                <th class="py-2 px-4 border-b">Cliente</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Entidades.Prestamos p : listaPrestamos) { %> <%-- Usa Prestamo o Prestamos según tu clase --%>
                            <tr class="hover:bg-gray-50">
                                <td class="py-2 px-4 border-b"><%= p.getIdPrestamo() %></td> <%-- Corregí de getNroPrestamo() a getIdPrestamo() si es el ID --%>
                                <td class="py-2 px-4 border-b"><%= p.getFecha() %></td> <%-- Corregí de getFechaPedido() a getFecha() --%>
                                <td class="py-2 px-4 border-b">$<%= String.format("%,.2f", p.getImportePedido()) %></td>
                                <td class="py-2 px-4 border-b">$<%= String.format("%,.2f", p.getImporteApagar()) %></td>
                                <td class="py-2 px-4 border-b"><%= p.getPlazoDePago() %></td> <%-- Corregí de getCuotas() a getPlazoDePago() --%>
                                <td class="py-2 px-4 border-b">$<%= String.format("%,.2f", p.getMontoCuotasxMes()) %></td> <%-- Corregí de getCuotaMensual() a getMontoCuotasxMes() --%>
                                <td class="py-2 px-4 border-b"><%= p.getEstadoSolicitud() %></td> <%-- ¡CORREGIDO! Usa getEstadoSolicitud() --%>
                                <td class="py-2 px-4 border-b"><%= p.getEstadoPago() %></td> <%-- ¡CORREGIDO! Usa getEstadoPago() --%>
                                <td class="py-2 px-4 border-b"><%= p.getUsuario().getPersona().getApellido() %>,
                                    <%= p.getUsuario().getPersona().getNombre() %></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                <% } else if (request.getAttribute("montoTotalPrestamos") != null) { %>
                    <div class="text-gray-600 mt-10 text-lg">No se encontraron préstamos en el rango de fechas seleccionado.</div>
                <% } %>


                <div id="cuentas-report-content" class="w-full max-w-6xl report-content-section <%= "cuentas".equals(currentActiveReportType) ? "" : "hidden" %>">
                    <form action="<%=request.getContextPath()%>/ServeletReporteCuentas" method="GET" class="space-y-4">
                        <input type="hidden" name="activeReport" value="cuentas">
                        <div class="flex flex-wrap -mx-2 mb-4">
                            <div class="w-full md:w-1/2 px-2 mb-4 md:mb-0">
                                <label for="fechaDesdeCuentas" class="block text-gray-700 text-sm font-bold mb-2">Fecha Desde:</label>
                                <input type="date" id="fechaDesdeCuentas" name="fechaDesde"
                                       value="<%= request.getAttribute("cuentaFechaDesdeStr") != null ? request.getAttribute("cuentaFechaDesdeStr") : "" %>"
                                       class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                            </div>
                            <div class="w-full md:w-1/2 px-2">
                                <label for="fechaHastaCuentas" class="block text-gray-700 text-sm font-bold mb-2">Fecha Hasta:</label>
                                <input type="date" id="fechaHastaCuentas" name="fechaHasta"
                                       value="<%= request.getAttribute("cuentaFechaHastaStr") != null ? request.getAttribute("cuentaFechaHastaStr") : "" %>"
                                       class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                            </div>
                        </div>
                        <div class="flex justify-center">
                            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-6 rounded focus:outline-none focus:shadow-outline">
                                Generar Reporte
                            </button>
                        </div>
                    </form>
                </div>

                <div class="bg-white p-6 rounded-lg shadow-md w-full max-w-6xl mb-8 mt-6">
                    <h3 class="text-xl font-bold text-gray-800 mb-4">Estadísticas de Cuentas</h3>
                    <div class="flex justify-around items-center text-center">
                        <div class="p-4 bg-blue-50 border border-blue-200 rounded-md shadow-sm">
                            <p class="text-lg font-semibold text-gray-800">Cantidad de cuentas nuevas:</p>
                            <p class="text-3xl font-bold text-blue-700">
                                <%= request.getAttribute("cantidadCuentasNuevas") != null ? request.getAttribute("cantidadCuentasNuevas") : "N/A" %>
                            </p>
                        </div>
                        <div class="p-4 bg-green-50 border border-green-200 rounded-md shadow-sm">
                            <p class="text-lg font-semibold text-gray-800">Promedio de saldo inicial:</p>
                            <p class="text-3xl font-bold text-green-700">
                                $<%= request.getAttribute("promedioSaldoInicial") != null ? String.format("%,.2f", request.getAttribute("promedioSaldoInicial")) : "N/A" %>
                            </p>
                        </div>
                        <div class="p-4 bg-purple-50 border border-purple-200 rounded-md shadow-sm">
                            <p class="text-lg font-semibold text-gray-800">Tipo de cuenta más creada:</p>
                            <p class="text-3xl font-bold text-purple-700">
                                <%= request.getAttribute("tipoCuentaMasCreada") != null ? request.getAttribute("tipoCuentaMasCreada") : "N/A" %>
                            </p>
                        </div>
                    </div>
                </div>

                <%-- Tabla de Cuentas Detalladas (Si deseas mostrarla) --%>
                <%
                    List<Entidades.Cuenta> cuentasCreadas = (List<Entidades.Cuenta>) request.getAttribute("cuentasCreadas");
                    if (cuentasCreadas != null && !cuentasCreadas.isEmpty()) {
                %>
                <h3 class="text-xl font-bold text-gray-700 mb-4 mt-6">Detalle de Cuentas Creadas</h3>
                <div class="bg-white p-6 rounded-lg shadow-md w-full max-w-6xl overflow-auto">
                    <table class="min-w-full bg-white text-sm text-gray-700 border border-gray-300 rounded-lg">
                        <thead class="bg-gray-100 text-gray-600 uppercase text-xs leading-normal">
                            <tr>
                                <th class="py-2 px-4 border-b">Nro Cuenta</th>
                                <th class="py-2 px-4 border-b">Usuario</th>
                                <th class="py-2 px-4 border-b">Fecha Creación</th>
                                <th class="py-2 px-4 border-b">Tipo Cuenta</th>
                                <th class="py-2 px-4 border-b">CBU</th>
                                <th class="py-2 px-4 border-b">Saldo</th>
                                <th class="py-2 px-4 border-b">Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Entidades.Cuenta c : cuentasCreadas) { %>
                            <tr class="hover:bg-gray-50">
                                <td class="py-2 px-4 border-b"><%= c.getNroCuenta() %></td>
                                <td class="py-2 px-4 border-b"><%= c.getUsuario().getPersona().getApellido() %>, <%= c.getUsuario().getPersona().getNombre() %></td>
                                <td class="py-2 px-4 border-b"><%= c.getFechaCreacion() %></td>
                                <td class="py-2 px-4 border-b"><%= c.getTipoCuenta().getDescripcion() %></td>
                                <td class="py-2 px-4 border-b"><%= c.getCbu() %></td>
                                <td class="py-2 px-4 border-b">$<%= String.format("%,.2f", c.getSaldo()) %></td>
                                <td class="py-2 px-4 border-b"><%= c.getEstado() %></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                <% } else if (request.getAttribute("cantidadCuentasNuevas") != null) { %>
                    <div class="text-gray-600 mt-10 text-lg">No se encontraron cuentas en el rango de fechas seleccionado.</div>
                <% } %>
            </div>

            <footer class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
                GRUPO_N7
            </footer>
        </main>

    </div>

    </body>
</html>