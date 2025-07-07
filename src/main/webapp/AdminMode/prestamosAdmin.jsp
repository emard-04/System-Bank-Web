<%@ page import="Entidades.Usuario" %>
<%@ page import="Entidades.Persona" %>
<%@ page import="Entidades.Prestamos"%>
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
	  <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Préstamos Admin - Tu Banco</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            font-family: 'Inter', sans-serif; /* O la fuente que prefieras */
        }
        .profile-photo-placeholder {
            background-size: cover;
            background-position: center;
        }
        /* Estilos para los íconos de Aceptar/Rechazar */
        .icon-button {
            width: 2.5rem; /* 40px */
            height: 2.5rem; /* 40px */
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 0.375rem; /* rounded-md */
            transition: all 0.2s ease-in-out;
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
                <h1 class="text-xl font-semibold text-gray-800">PRÉSTAMOS</h1>
                <div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
            </header>
            
            <nav class="bg-gray-50 border-b border-gray-200 p-4">
                <ul class="flex space-x-10 justify-center">
                <li><a
						href="<%=request.getContextPath()%>/ClientMode/homeClient.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
							Home </a></li>
                    <li><a href="<%=request.getContextPath()%>/ServletListarClientes?openListar=1&pagina=1" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Clientes</a></li>
                    <li><a href="<%=request.getContextPath()%>/ServletListarCuentas?openListar=1&pagina=1" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Cuentas</a></li>
                    <li><a href="${pageContext.request.contextPath}/ServletPrestamosAdmi"class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Préstamos</a></li>
                    <li><a href="/BancoParcial//AdminMode/reportesAdmin.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Reportes</a></li>
                </ul>
            </nav>
<%
    String mensaje = request.getParameter("mensaje");
    if (mensaje != null) {
%>
    <div class="mb-4 p-4 rounded-md 
        <% if ("aprobado".equals(mensaje)) { %> bg-green-100 text-green-800 
        <% } else if ("rechazado".equals(mensaje)) { %> bg-yellow-100 text-yellow-800 
        <% } else { %> bg-red-100 text-red-800 <% } %> 
        border font-semibold text-center">
        <% if ("aprobado".equals(mensaje)) { %>
            ✅ Préstamo aprobado correctamente.
        <% } else if ("rechazado".equals(mensaje)) { %>
            ❌ Préstamo rechazado correctamente.
        <% } else { %>
            ⚠️ Ocurrió un error al procesar el préstamo.
        <% } %>
    </div>
<%
    }
%>
            <div class="p-6 flex-1 overflow-y-auto">
             
                <form method="get" action="${pageContext.request.contextPath}/ServletPrestamosAdmi" class="flex items-center space-x-4">
    <label for="prestamos_autorizar" class="block text-gray-700 text-lg font-semibold whitespace-nowrap">Préstamos a Autorizar:</label>
    <% String filtroDni = (String) request.getAttribute("filtroDni"); %>
<select id="prestamos_autorizar" name="dni" class="select-dni p-2 border border-gray-300 rounded-md bg-white">
    <option value="">Todos</option>
    <% 
    List<Prestamos> listaPrestamos = (List<Prestamos>) request.getAttribute("prestamosPendientes");
    if (listaPrestamos != null) {
        // Usamos un Set para evitar DNIs duplicados
        java.util.Set<String> dniUnicos = new java.util.HashSet<>();
        for (Prestamos p : listaPrestamos) {
            String dni = p.getUsuario().getPersona().getDni();
            if (!dniUnicos.contains(dni)) {
                dniUnicos.add(dni);
    %>
    <option value="<%= dni %>" <%= dni.equals(filtroDni) ? "selected" : "" %>><%= dni %></option>
    <%
            }
        }
    }
    %>
</select>
    <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700">Filtrar</button>
</form>

<% if (filtroDni != null && !filtroDni.isEmpty()) { %>
    <a href="${pageContext.request.contextPath}/ServletPrestamosAdmi" class="ml-4 text-red-600 font-semibold hover:underline">Quitar filtro</a>
<% } %>

                <div class="bg-white rounded-lg shadow overflow-hidden">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-red-700 text-white">
                            <tr>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Nº Préstamo</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">DNI Cliente</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Fecha</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Importe a Pagar</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Importe Pedido</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Plazo de Pago</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Monto por Mes</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Acciones</th> </tr>
                        </thead>
                       <tbody class="bg-white divide-y divide-gray-200">
  <%
    
    if (listaPrestamos != null) {
        for (Prestamos p : listaPrestamos) {
%>
<tr>
    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900"><%= p.getIdPrestamo() %></td>
    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getUsuario().getPersona().getDni() %></td>
    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getFecha() %></td>
    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getImporteApagar() %></td>
    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getImportePedido() %></td>
    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getPlazoDePago() %></td>
    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800"><%= p.getMontoCuotasxMes() %></td>
    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
       <form method="post" action="${pageContext.request.contextPath}/ServletPrestamosAdmi" style="display:inline;">
        <input type="hidden" name="idPrestamo" value="<%= p.getIdPrestamo() %>" />
        <input type="hidden" name="accion" value="aceptar" />
            <input type="hidden" name="dni" value="<%= filtroDni != null ? filtroDni : "" %>" />
        <button class="icon-button bg-green-500 hover:bg-green-600 text-white" type="submit">&#10003;</button>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/ServletPrestamosAdmi" style="display:inline;">
        <input type="hidden" name="idPrestamo" value="<%= p.getIdPrestamo() %>" />
        <input type="hidden" name="accion" value="rechazar" />
            <input type="hidden" name="dni" value="<%= filtroDni != null ? filtroDni : "" %>" />
        <button class="icon-button bg-red-500 hover:bg-red-600 text-white" type="submit">&#10006;</button>
    </form>
    </td>
</tr>
<%
        }
    }
%>
</tbody>
                    </table>
                </div>

                <%
    Integer paginaActual = (Integer) request.getAttribute("paginaActual");
    Integer totalPaginas = (Integer) request.getAttribute("totalPaginas");
    if (paginaActual == null) paginaActual = 1;
    if (totalPaginas == null) totalPaginas = 1;
    String baseUrl = "ServletPrestamosAdmi";
    String queryParams = (filtroDni != null && !filtroDni.isEmpty()) ? "&dni=" + filtroDni : "";
%>

<div class="bg-red-700 p-3 rounded-b-lg shadow-lg flex justify-center items-center mt-0">
    
    <% if (paginaActual > 1) { %>
        <a href="<%= baseUrl %>?pagina=<%= paginaActual - 1 %><%= queryParams %>" 
           class="p-2 mx-1 text-white rounded-md hover:bg-red-600 transition">&larr;</a>
    <% } %>

    <% for (int i = 1; i <= totalPaginas; i++) { %>
        <a href="<%= baseUrl %>?pagina=<%= i %><%= queryParams %>" 
           class="p-2 mx-1 rounded-md transition 
                  <%= (i == paginaActual) ? "bg-red-500 text-white font-bold" : "text-white hover:bg-red-600" %>">
            <%= i %>
        </a>
    <% } %>

    <% if (paginaActual < totalPaginas) { %>
        <a href="<%= baseUrl %>?pagina=<%= paginaActual + 1 %><%= queryParams %>" 
           class="p-2 mx-1 text-white rounded-md hover:bg-red-600 transition">&rarr;</a>
    <% } %>
</div>

            <footer class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
                GRUPO_N7
            </footer>
        </main>

    </div>
<script>
    $(document).ready(function() {
        $('.select-dni').select2({
            placeholder: "Seleccione un DNI",
            allowClear: true,
            width: 'resolve'// o 'resolve' si querés que se adapte al contenedor
        });
    });
</script>
    </body>
</html>