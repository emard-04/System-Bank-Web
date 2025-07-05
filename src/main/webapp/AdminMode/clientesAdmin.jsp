<%@page import="Entidades.Provincia"%>
<%@ page import="Entidades.Usuario" %>
<%@ page import="Entidades.Persona" %>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
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
<title>Clientes Admin - Tu Banco</title>
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
</head>
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
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a>

						<a href="AdminMode/clientesAdmin_agregar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>

						<a
						href="<%=request.getContextPath()%>/ServletModificarCliente?openModificar=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>

						<a
						href="<%=request.getContextPath()%>/ServletBorrarCliente?openBorrar=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a>
					</li>
				</ul>
			</nav>
			<%ArrayList<Provincia> listaProvincias=null;
			if(request.getAttribute("listaProvincias")!=null){
				listaProvincias=(ArrayList<Provincia>)request.getAttribute("listaProvincias");}%>
			
			
			<div
				class="bg-gray-50 border-b border-gray-200 p-4 flex flex-col space-y-4 md:flex-row md:space-x-4 md:space-y-0 items-center">
				<form action="ServletListarClientes" method="get"
					class="flex flex-wrap items-center space-x-2">
					<input type="text" name="busqueda" placeholder="Buscar Dni"
						class="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 w-48">
								<input type="hidden" 
								name="Filtrar" value="1"> 
					<select name="provincias" id="provincias"
						class="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 w-40">
						<option value="" >Seleccionar provincia</option>
						<% for(Provincia provincia: listaProvincias){%>
						<option value="<%=provincia.getIdProvincia()%>"><%=provincia.getNombre()%></option>
						<%} %>
						</select>
					<select name="Localidad" id="localidad"
						class="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 w-40">
						<option value="">Seleccione una localidad</option>
						</select>
					<button type="submit"
						class="bg-blue-600 hover:bg-blue-700 text-white font-bold p-2 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 w-10 h-10 flex items-center justify-center">
						🔍</button>

				</form>
				</div>
			<div class="p-6 flex-1 overflow-y-auto">
				<div class="bg-white rounded-lg shadow overflow-hidden">
					<div class="overflow-x-auto">
						<table class="min-w-full divide-y divide-gray-200">
							<thead class="bg-red-700 text-white">
								<tr>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">DNI</th>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">CUIL</th>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Nombre</th>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Apellido</th>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Correo</th>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Teléfono</th>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Localidad</th>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Provincia</th>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Nacionalidad</th>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Sexo</th>
									<th scope="col"
										class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Fecha
										Nacimiento</th>
								</tr>
							</thead>
							<tbody class="bg-white divide-y divide-gray-200">
							<%
								 if (request.getAttribute("personas")!=null) {
									 List<Usuario> usuario=(List<Usuario>)request.getAttribute("personas");
    							 for (Usuario u : usuario) { %>
								<tr>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= u.getPersona().getDni() %></td>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= u.getPersona().getCuil() %></td>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= u.getPersona().getNombre() %></td>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= u.getPersona().getApellido() %></td>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= u.getPersona().getCorreoElectronico() %></td>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">
										<%
										List<String> telefonos = u.getPersona().getTelefonos();
										for (int i = 0; i < telefonos.size(); i++) {
											out.print(telefonos.get(i));
											if (i < telefonos.size() - 1)
												out.print("<br>");
										}
										%>
									</td>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= u.getPersona().getLocalidad().getNombre() %></td>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= u.getPersona().getProvincia().getNombre()%></td>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= u.getPersona().getPais().getNombre() %></td>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= u.getPersona().getSexo() %></td>
									<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= u.getPersona().getFechaNacimiento() %></td>
								</tr>
								<% }}
							%>
							</tbody>
						</table>
					</div>
				</div>

				<%
    Integer paginaActual = (Integer) request.getAttribute("paginaActual");
    Integer totalPaginas = (Integer) request.getAttribute("totalPaginas");
    if (paginaActual == null) paginaActual = 1;
    if (totalPaginas == null) totalPaginas = 1;
%>
				<div
					class="bg-red-700 p-3 rounded-b-lg shadow-lg flex justify-center items-center mt-0">
					<% if (paginaActual > 1) { %>
					<a
						href="ServletListarClientes?openListar=1&pagina=<%= paginaActual - 1 %>"
						class="p-2 mx-1 text-white">&larr;</a>
					<% } %>

					<% for (int i = 1; i <= totalPaginas; i++) { %>
					<a href="ServletListarClientes?openListar=1&pagina=<%= i %>"
						class="p-2 mx-1 <%= (i == paginaActual) ? "bg-red-500" : "text-white" %> rounded-md">
						<%= i %>
					</a>
					<% } %>

					<% if (paginaActual < totalPaginas) { %>
					<a
						href="ServletListarClientes?openListar=1&pagina=<%= paginaActual + 1 %>"
						class="p-2 mx-1 text-white">&rarr;</a>
					<% } %>
				</div>

				<footer
					class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
					GRUPO_N7 </footer>
		</main>

	</div>
</body>
<script>
document.getElementById('provincias').addEventListener('change', function () {
    var provinciaId = this.value;
    cargarLocalidades(provinciaId);
});
function cargarLocalidades(provinciaId, localidadIdSeleccionada = null) {
    const localidadSelect = document.getElementById('localidad');
    localidadSelect.innerHTML = '<option value="">Cargando...</option>';

    if (!provinciaId) {
        localidadSelect.innerHTML = '<option value="">Seleccione una localidad</option>';
        return;
    }

    const contextPath = "<%= request.getContextPath() %>";
    const url = contextPath + "/ServletListarClientes?listarLocalidades=1&provinciaId=" + provinciaId;

    fetch(url)
        .then(response => response.json())
        .then(localidades => {
            localidadSelect.innerHTML = '<option value="">Seleccione una localidad</option>';
            localidades.forEach(localidad => {
                const option = document.createElement('option');
                option.value = localidad.idLocalidad;
                option.textContent = localidad.nombre;
                localidadSelect.appendChild(option);
            });
        })
}
</script>
</html>