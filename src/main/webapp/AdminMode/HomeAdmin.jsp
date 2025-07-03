<%@ page import="Entidades.Usuario" %>
<%@ page import="Entidades.Persona" %>
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
    <title>Home Admin - Tu Banco</title>
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
            
            <header class="bg-white p-4 border-b border-gray-200 flex justify-end items-center">
    <div class="flex items-center">
        <img src="<%=request.getContextPath()%>/img/FotoLogo.webp" alt="Logo del banco" class="h-12 object-contain">
        <span class="text-gray-700 font-bold text-lg ml-2">Universidad Tecnológica Nacional</span>
    </div>
</header>

            <nav class="bg-gray-50 border-b border-gray-200 p-4">
                <ul class="flex space-x-10 justify-center">
                    <li><a href="<%=request.getContextPath()%>/ServletListarClientes?openListar=1&pagina=1"class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Clientes</a></li>
                    <li><a href="<%=request.getContextPath()%>/ServletListarCuentas?openListar=1&pagina=1"class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Cuentas</a></li>
                    <li><a href="${pageContext.request.contextPath}/ServletPrestamosAdmi" class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Préstamos</a></li>
                    <li><a href="reportesAdmin.jsp" class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Reportes</a></li>
                </ul>
            </nav>

            <div class="p-6 flex-1 flex flex-col justify-center items-center">
                <div class="bg-white p-8 rounded-lg shadow-md w-full h-full flex items-center justify-center text-gray-400 text-3xl font-light">
                    CONTENIDO PRINCIPAL ADMIN
                </div>
            </div>

            <footer class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
                GRUPO_N7
            </footer>
        </main>

    </div>

    </body>
</html>