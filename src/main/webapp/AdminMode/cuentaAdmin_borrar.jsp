<%@ page import="Entidades.Usuario"%>
<%@ page import="Entidades.Persona"%>
<%@ page import="Entidades.Cuenta, java.util.List"%>
<%
List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
%>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Borrar Cliente Admin - Tu Banco</title>
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
<body class="bg-gray-100 h-screen overflow-hidden">

	<% String mensaje = (String) session.getAttribute("mensaje"); 
	if (mensaje !=null){
	%>
	<script>alert("<%=mensaje%>")</script>

	<% session.removeAttribute("mensaje");} %>

	<div class="flex h-full">

		<aside
			class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

			<img src="<%=request.getContextPath()%>/img/perfilAdmi.webp"
				alt="Foto de perfil"
				class="w-32 h-32 rounded-full object-cover mb-4 border-4 border-gray-300 shadow-md">

			<h3 class="text-xl font-bold text-gray-800 text-center mb-6">
				<%=usuarioLogueado.getPersona().getNombre()%>
				<%=usuarioLogueado.getPersona().getApellido()%>
			</h3>

			<a href="#" onclick="confirmarLogout(event)"
				class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
				Salir </a>
		</aside>

		<main class="flex-1 flex flex-col overflow-y-auto">

			<header
				class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
				<h1 class="text-xl font-semibold text-gray-800">CUENTAS</h1>
				<div class="flex items-center">
					<img src="<%=request.getContextPath()%>/img/FotoLogo.webp"
						alt="Logo del banco" class="h-12 object-contain"> <span
						class="text-gray-700 font-bold text-lg">Universidad
						Tecnológica Nacional</span>
				</div>
			</header>

			<nav class="bg-gray-50 border-b border-gray-200 p-4">
				<ul class="flex items-center justify-between w-full">
					<li><a
						href="<%=request.getContextPath()%>/AdminMode/HomeAdmin.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Home</a>
					</li>

					<li class="flex space-x-10 mx-auto"><a
						href="/BancoParcial/ServletListarCuentas?openListar=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a>
						<a href="/BancoParcial/ServletAgregarCuentas?openAgregar=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>
						<a href="/BancoParcial/ServletModificarCuentas?openModificar=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>
						<a href="<%=request.getContextPath()%>/ServletBorrarCuenta"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a>
					</li>

					<li></li>
				</ul>
			</nav>

			<div class="p-6 flex-1 flex flex-col justify-center items-center">
				<div class="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
					<form action="/BancoParcial/ServletBorrarCuenta" method="post"
						onsubmit="return confirmarEliminar();"
						class="space-y-6 text-center">
						<p class="text-gray-700 text-lg font-semibold mb-4">Seleccione
							un Nro de cuenta que quieras eliminar:</p>

						<div class="mb-6">
							<label for="dni_eliminar" class="sr-only">Seleccione Nro
								Cuenta</label> <select id="seleccionar_cuenta_dni" name="dni_eliminar"
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white"
								required>
								<option value="" required>Seleccione el numero de
									cuenta</option>
						<%
if (cuentas != null) {
    for (Cuenta c : cuentas) {
        Usuario u = c.getUsuario();
        Persona p = (u != null) ? u.getPersona() : null;

        if (u != null && p != null) {
%>
        <option value="<%= c.getNroCuenta() %>">
            <%= c.getNroCuenta() %> - Cliente DNI: <%= p.getDni() %>
        </option>
<%
        } else {
%>
        <option value="<%= c.getNroCuenta() %>">
            <%= c.getNroCuenta() %> - ⚠ Usuario o Persona no definidos
        </option>
<%
        }
    }
} else {
%>
    <option disabled>No se pudo cargar la lista de cuentas.</option>
<%
}
%>
							</select>
						</div>

						<button type="submit"
							class="w-full bg-red-600 hover:bg-red-700 text-white font-bold py-3 px-4 rounded-md focus:outline-none focus:shadow-outline-red focus:ring-2 focus:ring-red-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl">
							Eliminar</button>
					</form>
					<script>
						function confirmarEliminar() {
							if (confirm("¿Estás segura de que querés eliminar esta cuenta?")) {

								return true;
							} else {
								return false;
							}
						}
					</script>
				</div>
			</div>

			<footer
				class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
				GRUPO_N7 </footer>
		</main>

	</div>

</body>
<script>
$(document).ready(function () {
    $('#seleccionar_cuenta_dni').select2({
        placeholder: "Seleccione o escriba Nº de Cuenta",
        allowClear: true,
        width: 'resolve'
    });
});
</script>
</html>
