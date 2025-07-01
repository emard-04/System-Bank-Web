<%@page import="java.util.ArrayList"%>
<%@ page import="Entidades.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Transferencias - Tu Banco</title>
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
        window.location.href = "<%=request.getContextPath()%>/ServletLogout";
    }
}
</script>
</head>
<%Usuario usuario= (Usuario)session.getAttribute("usuarioLogueado"); %>
<body class="bg-gray-100 h-screen overflow-hidden">
	<div class="flex h-full">

		<aside
			class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

			<div class="w-full h-48 bg-gray-300 mb-4 profile-photo-placeholder">
			</div>

			<h3 class="text-xl font-bold text-gray-800 text-center mb-1">
				<%= usuario.getPersona().getNombre() %>
				<%= usuario.getPersona().getApellido() %>
			</h3>

			<p class="text-md text-gray-600 text-center mb-6">
				Saldo: $<span id="saldoActual">---</span>
			</p>

			<div class="relative w-full mb-6">
				<select name="cuentaSeleccionada" id="cuenta"
					class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-semibold py-2 px-4 rounded-md w-full text-left focus:outline-none focus:shadow-outline flex items-center justify-between">
					<option value="">Cuentas</option>
					<%
                        int contador = 0;
                        ArrayList<Cuenta> listaCuenta = (ArrayList<Cuenta>) session.getAttribute("cuentasUsuario");
                        for (Cuenta c : listaCuenta) {
                            contador++;
                    %>
					<option value="<%= c.getNroCuenta() %>"
						data-saldo="<%= c.getSaldo() %>">Cuenta
						<%= contador %> - CBU:
						<%= c.getCbu() %>
					</option>
					<%} %>
				</select>
			</div>

			<a href="#" onclick="confirmarLogout(event)"
				class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
				Salir </a>
		</aside>

		<main class="flex-1 flex flex-col overflow-y-auto">

			<header
				class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
				<h1 class="text-xl font-semibold text-gray-800">TRANSFERENCIAS</h1>
				<div class="flex items-center">
        <img src="<%=request.getContextPath()%>/img/FotoLogo.webp" alt="Logo del banco" class="h-12 object-contain">
        <span class="text-gray-700 font-bold text-lg ml-2">Universidad Tecnológica Nacional</span>
    </div>
			</header>

			<nav class="bg-gray-50 border-b border-gray-200 p-4">
				<%
    String paginaActual = "transferencia"; // Ejemplo: "movimientos", "transferencia", "prestamos", "personal", "home"
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
						class="<%= paginaActual.equals("transferencia") ? "bg-blue-600 text-white" : "hover:bg-blue-600 hover:text-white text-gray-700" %> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
							Transferencia </a> <a
						href="/BancoParcial/ClientMode/movientosClient.jsp"
						class="<%= paginaActual.equals("movimientos") ? "bg-blue-600 text-white" : "hover:bg-blue-600 hover:text-white text-gray-700" %> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
							Movimientos </a> <a
						href="/BancoParcial/ClientMode/PrestamosClient.jsp"
						class="<%= paginaActual.equals("prestamos") ? "bg-blue-600 text-white" : "hover:bg-blue-600 hover:text-white text-gray-700" %> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
							Prestamos </a> <a
						href="<%=request.getContextPath()%>/ServletPersonalCliente"
						class="<%= paginaActual.equals("personal") ? "bg-blue-600 text-white" : "hover:bg-blue-600 hover:text-white text-gray-700" %> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
							Personal </a></li>

					<li></li>
				</ul>
			</nav>
			<div class="p-6 flex-1 flex flex-col items-center justify-center">
				<div class="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
					<% if (request.getAttribute("mensaje") != null) { %>
					<div
						class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4 text-center font-semibold">
						<%= request.getAttribute("mensaje") %>
					</div>
					<% } %>
					<form action="/BancoParcial/ServletTransferencia" method="post"
						class="space-y-6">
						<div class="mb-4">
							<input type="hidden" id="cuentaSeleccionadaHidden"
								name="cuentaSeleccionada"> 
								<label for="monto"
								class="block text-gray-700 text-lg font-semibold mb-2">Monto
								a transferir</label> <input type="number" id="monto" name="monto"
								placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
								required>
						</div>
						<div class="mb-4">
							<label for="alias_cbu"
								class="block text-gray-700 text-lg font-semibold mb-2">Alias
								/ CBU</label> <input type="text" id="alias_cbu" name="alias_cbu"
								placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
								required>
						</div>
						<div class="mb-6">
							<label for="referencia"
								class="block text-gray-700 text-lg font-semibold mb-2">Referencia</label>
							<input type="text" id="referencia" name="referencia"
								placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg">
						</div>

						<button type="submit"
							class="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded-md focus:outline-none focus:shadow-outline-blue focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl">
							Transferir</button>
					</form>
				</div>
			</div>

			<footer
				class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
				GRUPO_N7 </footer>
		</main>

	</div>

	<script>

document.getElementById("cuenta").addEventListener("change", function () {
	const selectedOption = this.options[this.selectedIndex];
	const saldo = selectedOption.getAttribute("data-saldo");
	document.getElementById("saldoActual").textContent = saldo !== null ? saldo : "---";
});

window.addEventListener("DOMContentLoaded", function () {
	const select = document.getElementById("cuenta");
	if (select && select.options.length > 0) {
		const selectedOption = select.options[select.selectedIndex];
		const saldo = selectedOption.getAttribute("data-saldo");
		document.getElementById("saldoActual").textContent = saldo !== null ? saldo : "---";
	}
});
   const selectCuenta = document.getElementById("cuenta");
    const hiddenInput = document.getElementById("cuentaSeleccionadaHidden");

    selectCuenta.addEventListener("change", function () {
        hiddenInput.value = this.value;
    });

    // Por si ya viene preseleccionado:
    window.addEventListener("DOMContentLoaded", function () {
        if (selectCuenta.value) {
            hiddenInput.value = selectCuenta.value;
        }
    });
</script>
</body>
</html>