<%@ page import="Entidades.Usuario" %>
<%@ page import="Entidades.Persona" %>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List, Entidades.Usuario" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Borrar Cliente Admin - Tu Banco</title>
<script src="https://cdn.tailwindcss.com"></script>
<style>
body {
	font-family: 'Inter', sans-serif;
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
<body class="bg-gray-100 h-screen overflow-hidden">
	<div class="flex h-full">

		<aside
			class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

			<div class="w-full h-48 bg-gray-300 mb-4 profile-photo-placeholder">
			</div>

			    <h3 class="text-xl font-bold text-gray-800 text-center mb-6">
    <%= usuarioLogueado.getPersona().getNombre() %> <%= usuarioLogueado.getPersona().getApellido() %>
</h3>

			<a href="#" onclick="confirmarLogout(event)"
				class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
				Salir </a>
		</aside>

		<main class="flex-1 flex flex-col overflow-y-auto">

			<header
				class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
				<h1 class="text-xl font-semibold text-gray-800">CLIENTES</h1>
				 <div class="flex items-center">
				<img src="<%=request.getContextPath()%>/img/FotoLogo.webp" alt="Logo del banco" class="h-12 object-contain">
				<span class="text-gray-700 font-bold text-lg">Universidad Tecnológica Nacional</span>
				</div>
			</header>

			<nav class="bg-gray-50 border-b border-gray-200 p-4">
				<ul class="flex items-center justify-between w-full">
					<li><a
						href="<%=request.getContextPath()%>/AdminMode/HomeAdmin.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Home</a>
					</li>

					<li class="flex space-x-10 mx-auto"><a
						href="<%=request.getContextPath()%>/ServletListarClientes?openListar=1&pagina=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a>
						<a href="<%=request.getContextPath()%>/ServletAgregarCliente?openAgregarUsu=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>
						<a
						href="<%=request.getContextPath()%>/ServletModificarCliente?openModificar=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>
						<a href="<%=request.getContextPath()%>/ServletBorrarCliente?openBorrar=1"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a>
					</li>

					<li></li>
				</ul>
			</nav>

			<div class="p-6 flex-1 flex flex-col justify-center items-center">
				<div class="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
					<form action="/BancoParcial/ServletBorrarCliente" method="post"
						 class="space-y-6 text-center">
						<p class="text-gray-700 text-lg font-semibold mb-4">Seleccione
							el DNI del usuario que quieras eliminar:</p>

						<div class="mb-6">
							<label for="dni_eliminar" class="sr-only">Seleccione DNI</label>
							<select id="dni_eliminar" name="dni_eliminar"
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white"
								required>
								<option value="" required>Seleccione DNI</option>
								<% if(request.getAttribute("ListarUsuario")!=null){
									ArrayList<Usuario> usuarios=(ArrayList<Usuario>)request.getAttribute("ListarUsuario");
								for (Usuario u : usuarios) {%>
								<option value="<%=u.getPersona().getDni()%>">Dni =
									<%=u.getPersona().getDni()%>
								</option>
								<%}%>
								<%}%>

							</select>
						</div>

						<button type="submit"
							class="w-full bg-red-600 hover:bg-red-700 text-white font-bold py-3 px-4 rounded-md focus:outline-none focus:shadow-outline-red focus:ring-2 focus:ring-red-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl">
							Eliminar</button>
					</form>
				</div>
			</div>

			<footer
				class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
				GRUPO_N7 </footer>
		</main>

	</div>

</body>
</html>