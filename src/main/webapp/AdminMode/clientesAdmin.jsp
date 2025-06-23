<%@ page import="java.util.List" %>
<%@ page import="Entidades.Persona" %>
<%
    List<Persona> personas = (List<Persona>) request.getAttribute("personas");
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
<body class="bg-gray-100 h-screen overflow-hidden">
    <div class="flex h-full">

        <aside class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">
            
            <div class="w-full h-48 bg-gray-300 mb-4 profile-photo-placeholder">
                </div>
            
            <h3 class="text-xl font-bold text-gray-800 text-center mb-6">ADMIN</h3>
            
            <a href="<%=request.getContextPath()%>/ServletLogout"
   		class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
   			Salir
</a>
        </aside>

        <main class="flex-1 flex flex-col overflow-y-auto">
            
            <header class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
                <h1 class="text-xl font-semibold text-gray-800">CLIENTES</h1>
                <div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
            </header>

            <nav class="bg-gray-50 border-b border-gray-200 p-4">
				<ul class="flex items-center justify-between w-full">
					<li><a href="HomeAdmin.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Home</a>
					</li>

					<li class="flex space-x-10 mx-auto"><a
						href="/BancoParcial/ServletListarClientes?openListar=1&pagina=1"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a>
						<a href="clientesAdmin_agregar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>
						<a href="clienteAdmin_modificar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>
						<a href="clientesAdmin_borrar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a>
					</li>

					<li></li>
				</ul>
			</nav>

            <div class="p-6 flex-1 overflow-y-auto">
                <div class="bg-white rounded-lg shadow overflow-hidden">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-red-700 text-white">
                            <tr>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">DNI</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">CUIL</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Nombre</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Apellido</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Correo</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Teléfono</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Localidad</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Provincia</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Nacionalidad</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Sexo</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Fecha Nacimiento</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <% if (personas != null) {
     for (Persona p : personas) { %>
    <tr>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getDni() %></td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getCuil() %></td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getNombre() %></td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getApellido() %></td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getCorreoElectronico() %></td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getTelefono() %></td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getLocalidad() %></td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getProvincia() %></td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getNacionalidad() %></td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getSexo() %></td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getFechaNacimiento() %></td>
    </tr>
<% } } %>
                            </tbody>
                    </table>
                </div>

                <%
    Integer paginaActual = (Integer) request.getAttribute("paginaActual");
    Integer totalPaginas = (Integer) request.getAttribute("totalPaginas");
    if (paginaActual == null) paginaActual = 1;
    if (totalPaginas == null) totalPaginas = 1;
%>
<div class="bg-red-700 p-3 rounded-b-lg shadow-lg flex justify-center items-center mt-0">
    <% if (paginaActual > 1) { %>
        <a href="ServletListarClientes?openListar=1&pagina=<%= paginaActual - 1 %>" class="p-2 mx-1 text-white">&larr;</a>
    <% } %>

    <% for (int i = 1; i <= totalPaginas; i++) { %>
        <a href="ServletListarClientes?openListar=1&pagina=<%= i %>" class="p-2 mx-1 <%= (i == paginaActual) ? "bg-red-500" : "text-white" %> rounded-md">
            <%= i %>
        </a>
    <% } %>

    <% if (paginaActual < totalPaginas) { %>
        <a href="ServletListarClientes?openListar=1&pagina=<%= paginaActual + 1 %>" class="p-2 mx-1 text-white">&rarr;</a>
    <% } %>
            </div>

            <footer class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
                GRUPO_N7
            </footer>
        </main>

    </div>

    </body>
</html>