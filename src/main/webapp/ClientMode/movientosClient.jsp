<%@page import="negocioImpl.CuentasNegImpl"%>
<%@page import="negocio.CuentasNeg"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="Entidades.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Movimientos - Tu Banco</title>
<script src="https://cdn.tailwindcss.com"></script>
<style>
body {
	font-family: 'Inter', sans-serif; /* O la fuente que prefieras */
}

.profile-photo-placeholder {
	background-size: cover;
	background-position: center;
}
</style>
<script>
function confirmarLogout(e) {
    e.preventDefault(); // Detiene la acción por defecto del enlace

    if (confirm("¿Estás seguro de que quieres cerrar sesión?")) {
        // Si el usuario confirma, redirige al servlet de logout
        window.location.href = "<%=request.getContextPath()%>
	/ServletLogout";
		}
	}
</script>
</head>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
%>
<body class="bg-gray-100 h-screen overflow-hidden">
	<div class="flex h-full">

		<aside
			class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

		<%
    String sexo = usuario.getPersona().getSexo();
    String rutaFoto = "";

    if (sexo != null) {
        if (sexo.equalsIgnoreCase("F") || sexo.equalsIgnoreCase("Femenino")) {
            rutaFoto = request.getContextPath() + "/img/perfilMujer.avif";
        } else if (sexo.equalsIgnoreCase("M") || sexo.equalsIgnoreCase("Masculino")) {
            rutaFoto = request.getContextPath() + "/img/perfilHombre.avif";
        }else {
            rutaFoto = request.getContextPath() + "/img/perfilAdmi.webp";
        }
    }
%>

<img src="<%= rutaFoto %>"
     alt="Foto de perfil"
     class="w-32 h-32 rounded-full object-cover mb-4 border-4 border-gray-300 shadow-md">
		

			<h3 class="text-xl font-bold text-gray-800 text-center mb-1">
				<%=usuario.getPersona().getNombre()%>
				<%=usuario.getPersona().getApellido()%>
			</h3>
			<% Cuenta cuenta = null;
			if(request.getSession().getAttribute("cuenta")!=null){
			    cuenta =  (Cuenta)session.getAttribute("cuenta");
			}
			%>
			<% if (cuenta != null) { %>
    <p class="text-md text-gray-600 text-center mb-6">
        <% if(cuenta.getSaldo() != null) { %>
            Saldo: $<span id="saldoActual"><%=cuenta.getSaldo()%></span>
        <% } else { %>
            Saldo: $<span id="saldoActual">---</span>
        <% } %>
    </p>
<% } %>

			<div class="relative w-full mb-6">
			<form id="formCuenta"
    action="<%=request.getContextPath()%>/ServletListarMovimientos"
    method="get">
    <input type="hidden" name="Actualizar" value="1">
    
    <select name="cuentaSeleccionada" id="cuenta"
        class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-semibold py-2 px-4 rounded-md w-full text-left focus:outline-none focus:shadow-outline flex items-center justify-between"
        onchange="document.getElementById('formCuenta').submit();">
        
        <% if (cuenta != null) { %>
            <option value="<%=cuenta.getNroCuenta()%>" selected>CBU: <%=cuenta.getCbu()%></option>
        <% } else { %>
            <option value="" disabled selected>Cuentas</option>
        <% } %>

        <%
        int contador = 0;
        ArrayList<Cuenta> listaCuenta = (ArrayList<Cuenta>) session.getAttribute("cuentasUsuario");
        for (Cuenta c : listaCuenta) {
            contador++;
            if (cuenta == null || cuenta.getNroCuenta() != c.getNroCuenta()) {
        %>
            <option value="<%=c.getNroCuenta()%>" data-saldo="<%=c.getSaldo()%>">
                Cuenta <%=contador%> - CBU: <%=c.getCbu()%>
            </option>
        <% 
            }
        }
        %>
    </select>
</form>
</div>

			<a href="#" onclick="confirmarLogout(event)"
				class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
				Salir </a>
		</aside>

		<main class="flex-1 flex flex-col overflow-y-auto">

			<header
				class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
				<h1 class="text-xl font-semibold text-gray-800">MOVIMIENTOS</h1>
				<div class="flex items-center">
					<img src="<%=request.getContextPath()%>/img/FotoLogo.webp"
						alt="Logo del banco" class="h-12 object-contain"> <span
						class="text-gray-700 font-bold text-lg ml-2">Universidad
						Tecnológica Nacional</span>
				</div>
			</header>

			<nav class="bg-gray-50 border-b border-gray-200 p-4">

				<%
				String paginaActual = "movimientos"; // Ejemplo: "movimientos", "transferencia", "prestamos", "personal", "home"
				%>

				<ul
					class="flex items-center justify-between w-full px-4 py-2 bg-white shadow">

					<!-- Home -->
					<li><a
						href="<%=request.getContextPath()%>/ClientMode/homeClient.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
							Home </a></li>

					<!-- Opciones centradas -->
					<li class="flex space-x-6 mx-auto"><a
						href="/BancoParcial/ClientMode/TransferenciaClient.jsp"
						class="<%=paginaActual.equals("transferencia")
		? "bg-blue-600 text-white"
		: "hover:bg-blue-600 hover:text-white text-gray-700"%> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
							Transferencia </a> <a
						href="/BancoParcial/ClientMode/movientosClient.jsp"
						class="<%=paginaActual.equals("movimientos")
		? "bg-blue-600 text-white"
		: "hover:bg-blue-600 hover:text-white text-gray-700"%> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
							Movimientos </a> <a
						href="/BancoParcial/ClientMode/PrestamosClient.jsp"
						class="<%=paginaActual.equals("prestamos")
		? "bg-blue-600 text-white"
		: "hover:bg-blue-600 hover:text-white text-gray-700"%> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
							Prestamos </a> <a
						href="<%=request.getContextPath()%>/ServletPersonalCliente"
						class="<%=paginaActual.equals("personal")
		? "bg-blue-600 text-white"
		: "hover:bg-blue-600 hover:text-white text-gray-700"%> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
							Personal </a></li>

					<li></li>
				</ul>
			</nav>
			<div
				class="bg-gray-50 border-b border-gray-200 p-4 flex flex-col space-y-4 md:flex-row md:space-x-4 md:space-y-0 items-center">
				<form action="ServletListarMovimientos" method="get"
					class="flex flex-wrap items-center space-x-2">
					<input type="text" name="busqueda" placeholder="Buscar"
						class="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 w-48">
						<input type="hidden" id="cuentaSeleccionadaHidden"
								name="cuentaSelect"> 
								<input type="hidden" 
								name="Filtrar" value="1"> 
					<select name="tipoOperacion"
						class="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 w-40">
						<option value="">Operación</option>
						<option value=">">Entrada</option>
						<option value="<">Salida</option>
					</select> <input type="date" name="fechaDesde"
						class="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 w-40">

					<input type="date" name="fechaHasta"
						class="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 w-40">

					<button type="submit"
						class="bg-blue-600 hover:bg-blue-700 text-white font-bold p-2 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 w-10 h-10 flex items-center justify-center">
						🔍</button>

				</form>
			</div>
			<div class="p-6 flex-1 overflow-y-auto">
				<div class="bg-white rounded-lg shadow overflow-hidden">
					<table class="min-w-full divide-y divide-gray-200">
						<thead class="bg-red-700 text-white">
							<tr>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Cuenta</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Usuario
									Externo</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Importe</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Detalle</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Fecha</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Tipo
									Movimiento</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Código
									Movimiento</th>
							</tr>
						</thead>
						<tbody class="bg-white divide-y divide-gray-200">
							<%
							if(request.getAttribute("Lista") != null || request.getAttribute("ListaFiltra") != null){
								ArrayList<Movimiento> listaMovimiento=null;
							if (request.getAttribute("Lista") != null) {
								  listaMovimiento = (ArrayList<Movimiento>) request.getAttribute("Lista");
							}
							else if(request.getAttribute("ListaFiltra") != null){
							  listaMovimiento = (ArrayList<Movimiento>) request.getAttribute("ListaFiltra");
							}
								for (Movimiento mov : listaMovimiento) {
							%>
							<tr>
								<td
									class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900"><%=mov.getCuentaEmisor().getNroCuenta()%></td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%=mov.getCuentaReceptor().getUsuario().getNombreUsuario()%></td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">$<%=mov.getImporte()%></td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%=mov.getDetalle()%></td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%=mov.getFecha()%></td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%=mov.getTipoMovimiento().getDescripcion()%></td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%=mov.getIdMovimiento()%></td>
							</tr>
							<%
							}
							}
							%>

						</tbody>
					</table>
				</div>

				<div
					class="bg-red-700 p-3 rounded-b-lg shadow-lg flex justify-end items-center mt-0">
					<button
						class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
						&larr;</button>
					<button
						class="bg-red-500 text-white font-bold p-2 mx-1 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
						1</button>
					<button
						class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
						2</button>
					<button
						class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
						3</button>
					<button
						class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
						&rarr;</button>
				</div>
			</div>

			<footer
				class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
				GRUPO_N7 </footer>
		</main>

	</div>

	<script>
		document
				.getElementById("cuenta")
				.addEventListener(
						"change",
						function() {
							const selectedOption = this.options[this.selectedIndex];
							const saldo = selectedOption
									.getAttribute("data-saldo");
							document.getElementById("saldoActual").textContent = saldo !== null ? saldo
									: "---";
						});

		window
				.addEventListener(
						"DOMContentLoaded",
						function() {
							const select = document.getElementById("cuenta");
							if (select && select.options.length > 0) {
								const selectedOption = select.options[select.selectedIndex];
								const saldo = selectedOption
										.getAttribute("data-saldo");
								document.getElementById("saldoActual").textContent = saldo !== null ? saldo
										: "---";
							}
						});
		const selectCuenta = document.getElementById("cuenta");
		const hiddenInput = document.getElementById("cuentaSeleccionadaHidden");

		selectCuenta.addEventListener("change", function() {
			if(this.value!=null){
			hiddenInput.value = this.value;

		});

		// Por si ya viene preseleccionado:
		window.addEventListener("DOMContentLoaded", function() {
			if (selectCuenta.value) {
				hiddenInput.value = selectCuenta.value;
			}
		});
	</script>
</body>
</html>