<%@ page import="Entidades.Usuario"%>
<%@ page import="Entidades.Persona"%>
<%@ page import="Entidades.Prestamos"%>
<%@ page import="java.util.List"%>
<%@ page import="java.math.BigDecimal"%>
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
%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Reportes Admin - Tu Banco</title>
<script src="https://cdn.tailwindcss.com"></script>
<style>
body {
	font-family: 'Inter', sans-serif;
}
</style>
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

		<aside
			class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">
			<img src="<%=request.getContextPath()%>/img/perfilAdmi.webp"
				alt="Foto de perfil"
				class="w-32 h-32 rounded-full object-cover mb-4 border-4 border-gray-300 shadow-md">

			<h3 class="text-xl font-bold text-gray-800 text-center mb-6">
				<%= usuarioLogueado.getPersona().getNombre() %>
				<%= usuarioLogueado.getPersona().getApellido() %>
			</h3>

			<a href="#" onclick="confirmarLogout(event)"
				class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full">
				Salir </a>
		</aside>

		<main class="flex-1 flex flex-col overflow-y-auto">
			<header
				class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
				<h1 class="text-xl font-semibold text-gray-800">REPORTES</h1>
				<div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
			</header>



			<nav class="bg-gray-50 border-b border-gray-200 p-4">
				<ul class="flex space-x-10 justify-center">

					<li><a
						href="<%=request.getContextPath()%>/AdminMode/HomeAdmin.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Home</a>
					</li>

					<li><a
						href="<%=request.getContextPath()%>/ServletListarClientes?openListar=1&pagina=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md">
							Clientes</a></li>
					<li><a
						href="<%=request.getContextPath()%>/ServletListarCuentas?openListar=1&pagina=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md">
							Cuentas</a></li>
					<li><a
						href="<%=request.getContextPath()%>/ServletPrestamosAdmi"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md">
							Préstamos</a></li>
					<li><a
						href="<%=request.getContextPath()%>/ServeletReportPrestamos"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md">
							Reportes</a></li>
				</ul>
			</nav>

			<nav class="bg-gray-100 border-b border-gray-200 p-3">
				<ul class="flex space-x-4 justify-start pl-4 justify-center">
					<li><a href="/BancoParcial/AdminMode/reportesAdmin.jsp"
						class="bg-blue-500 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Pedir
							Préstamos</a></li>
					<li><a
						href="<%=request.getContextPath()%>/ServeletReporteCuentas"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md">
							Reporte de Cuentas</a></li>
				</ul>
			</nav>

			<div
				class="p-6 flex-1 flex flex-col justify-start items-center overflow-auto">
				<h2 class="text-2xl font-bold text-gray-700 mb-6">Reporte de
					Préstamos</h2>

				<div class="bg-white p-6 rounded-lg shadow-md w-full max-w-2xl mb-8">
					<form
						action="<%=request.getContextPath()%>/ServeletReportPrestamos"
						method="GET" class="space-y-4">
						<div class="flex flex-wrap -mx-2 mb-4">
							<div class="w-full md:w-1/2 px-2 mb-4 md:mb-0">
								<label for="fechaDesde"
									class="block text-gray-700 text-sm font-bold mb-2">Fecha
									Desde:</label> <input type="date" id="fechaDesde" name="fechaDesde"
									value="<%= request.getAttribute("fechaDesdeStr") != null ? request.getAttribute("fechaDesdeStr") : "" %>"
									class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
							</div>
							<div class="w-full md:w-1/2 px-2">
								<label for="fechaHasta"
									class="block text-gray-700 text-sm font-bold mb-2">Fecha
									Hasta:</label> <input type="date" id="fechaHasta" name="fechaHasta"
									value="<%= request.getAttribute("fechaHastaStr") != null ? request.getAttribute("fechaHastaStr") : "" %>"
									class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
							</div>
						</div>
						<div>
							<label for="estadoPrestamo"
								class="block text-gray-700 text-sm font-bold mb-2">Estado
								de la Solicitud:</label> <select id="estadoPrestamo"
								name="estadoPrestamo"
								class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
								<option value="todos"
									<%= "todos".equals(request.getAttribute("estadoSeleccionado")) ? "selected" : "" %>>Todos</option>
								<option value="Pendiente"
									<%= "Pendiente".equals(request.getAttribute("estadoSeleccionado")) ? "selected" : "" %>>Pendiente</option>
								<option value="Aprobado"
									<%= "Aprobado".equals(request.getAttribute("estadoSeleccionado")) ? "selected" : "" %>>Aprobado</option>
								<option value="Rechazado"
									<%= "Rechazado".equals(request.getAttribute("estadoSeleccionado")) ? "selected" : "" %>>Rechazado</option>
							</select>
						</div>
						<div class="flex justify-center">
							<button type="submit"
								class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-6 rounded focus:outline-none focus:shadow-outline">
								Generar Reporte</button>
						</div>
					</form>
				</div>

				<%
                List<Prestamos> prestamos = (List<Prestamos>) request.getAttribute("prestamos");
                BigDecimal totalImportePedido = (BigDecimal) request.getAttribute("totalImportePedido");
                BigDecimal totalImporteAPagar = (BigDecimal) request.getAttribute("totalImporteAPagar");
                Integer cantidadPrestamos = (Integer) request.getAttribute("cantidadPrestamos");

                if (prestamos != null && !prestamos.isEmpty()) {
            %>
				<h3 class="text-xl font-bold text-gray-700 mb-4">Estadísticas
					del Reporte</h3>
				<div
					class="bg-white p-6 rounded-lg shadow-md w-full max-w-6xl mb-8 flex justify-around items-center text-center">
					<div
						class="p-4 bg-blue-50 border border-blue-200 rounded-md shadow-sm">
						<p class="text-lg font-semibold text-gray-800">Total
							Préstamos:</p>
						<p class="text-3xl font-bold text-blue-700"><%= cantidadPrestamos != null ? cantidadPrestamos : 0 %></p>
					</div>
					<div
						class="p-4 bg-green-50 border border-green-200 rounded-md shadow-sm">
						<p class="text-lg font-semibold text-gray-800">Total Importe
							Pedido:</p>
						<p class="text-3xl font-bold text-green-700">
							$<%= totalImportePedido != null ? String.format("%,.2f", totalImportePedido) : "0.00" %></p>
					</div>
					<div
						class="p-4 bg-purple-50 border border-purple-200 rounded-md shadow-sm">
						<p class="text-lg font-semibold text-gray-800">Total Importe a
							Pagar:</p>
						<p class="text-3xl font-bold text-purple-700">
							$<%= totalImporteAPagar != null ? String.format("%,.2f", totalImporteAPagar) : "0.00" %></p>
					</div>
				</div>

				<h3 class="text-xl font-bold text-gray-700 mb-4">Detalle de
					Préstamos</h3>
				<div
					class="bg-white p-6 rounded-lg shadow-md w-full max-w-6xl overflow-auto">
					<table
						class="min-w-full bg-white text-sm text-gray-700 border border-gray-300 rounded-lg">
						<thead
							class="bg-gray-100 text-gray-600 uppercase text-xs leading-normal">
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
							<%
                            for (Prestamos p : prestamos) {
                        %>
							<tr class="hover:bg-gray-50">
								<td class="py-2 px-4 border-b"><%= p.getIdPrestamo() %></td>
								<td class="py-2 px-4 border-b"><%= p.getFecha() %></td>
								<td class="py-2 px-4 border-b">$<%= String.format("%,.2f", p.getImportePedido()) %></td>
								<td class="py-2 px-4 border-b">$<%= String.format("%,.2f", p.getImporteApagar()) %></td>
								<td class="py-2 px-4 border-b"><%= p.getPlazoDePago() %></td>
								<td class="py-2 px-4 border-b">$<%= String.format("%,.2f", p.getMontoCuotasxMes()) %></td>
								<td class="py-2 px-4 border-b"><%= p.getEstadoSolicitud() %></td>
								<td class="py-2 px-4 border-b"><%= p.getEstadoPago() %></td>
								<td class="py-2 px-4 border-b"><%= p.getUsuario().getPersona().getApellido() %>,
									<%= p.getUsuario().getPersona().getNombre() %></td>
							</tr>
							<%
                            }
                        %>
						</tbody>
					</table>
				</div>
				<%
                } else {
            %>
				<div class="text-gray-600 mt-10 text-lg">No se encontraron
					préstamos con los parámetros seleccionados.</div>
				<%
                }
            %>
			</div>

			<footer
				class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
				GRUPO_N7 </footer>
		</main>

	</div>
</body>
</html>