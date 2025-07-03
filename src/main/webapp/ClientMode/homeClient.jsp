<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="java.util.ArrayList"%>
<%@ page import="Entidades.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Inicio - Tu Banco</title>
<script src="https://cdn.tailwindcss.com"></script>
<style>
body {
	font-family: 'Inter', sans-serif;
	/* Puedes importar Google Fonts si quieres algo más específico */
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
<%Usuario usuario= (Usuario)session.getAttribute("usuarioLogueado"); %>
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
                <%= usuario.getPersona().getNombre() %> <%= usuario.getPersona().getApellido() %>
            </h3>

            <p class="text-md text-gray-600 text-center mb-6">
                Saldo: $<span id="saldoActual">---</span>
            </p>

            <div class="relative w-full mb-6">
                <select name="cuentaSeleccionada" id="cuenta"
                    class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-semibold py-2 px-4 rounded-md w-full text-left focus:outline-none focus:shadow-outline flex items-center justify-between">
                    <option value="" disabled selected>Cuentas</option>
                    <%
                        int contador = 0;
                        ArrayList<Cuenta> listaCuenta = (ArrayList<Cuenta>) session.getAttribute("cuentasUsuario");
                        for (Cuenta c : listaCuenta) {
                            contador++;
                    %>
                        <option value="<%= c.getNroCuenta() %>" data-saldo="<%= c.getSaldo() %>">
                            Cuenta <%= contador %> - CBU: <%= c.getCbu() %>
                        </option>
                    <%
                        }
                    %>
                </select>
            </div>


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
				<ul class="flex space-x-8 justify-center">
					<li><a href="TransferenciaClient.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Transferencia</a></li>
					<li><a href="movientosClient.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Movimientos</a></li>
					<li><a href="PrestamosClient.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Prestamos</a></li>
					<li><a href="<%=request.getContextPath()%>/ServletPersonalCliente"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Personal</a></li>
				</ul>
			</nav>

			<div class="p-6 flex-1 flex flex-col justify-center items-center">
				<div
					class="bg-white p-8 rounded-lg shadow-md w-full h-full flex flex-col items-center justify-center text-center">
					<p class="text-gray-800 text-3xl font-bold mb-2">¡Bienvenido al
						Banco GRUPO N°7!</p>
					<p class="text-gray-600 text-xl">Tu banco de confianza!</p>
				</div>
			</div>

			<footer
				class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
				GRUPO_N7 </footer>
		</main>

	</div>

	<script>
        const dropdownButton = document.getElementById('dropdown-button');
        const dropdownList = document.getElementById('dropdown-list');

        if (dropdownButton && dropdownList) {
            dropdownButton.addEventListener('click', () => {
                dropdownList.classList.toggle('hidden');
            });
        }
    </script>
</body>
</html>