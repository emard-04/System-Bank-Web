<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="Entidades.*"%>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pagar Préstamo - Tu Banco</title>
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
<%
if (request.getAttribute("cuentas") == null || request.getAttribute("cuotasPendientes") == null) {
    response.sendRedirect(request.getContextPath() + "/ServletPagarPrestamoCliente");
    return;
}
%>
</head>
<%Usuario usuario= (Usuario)session.getAttribute("usuarioLogueado"); %>
<body class="bg-gray-100 h-screen overflow-hidden">
    <div class="flex h-full">

       	<aside
class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

            <div class="w-full h-48 bg-gray-300 mb-4 profile-photo-placeholder">
            </div>

            <h3 class="text-xl font-bold text-gray-800 text-center mb-1">
                <%= usuario.getPersona().getNombre() %> <%= usuario.getPersona().getApellido() %>
            </h3>

            <p class="text-md text-gray-600 text-center mb-6">
                Saldo: $<span id="saldoActual">---</span>
            </p>

            


			<a href="#"
   onclick="confirmarLogout(event)"
   class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
   Salir
</a>
		</aside>


        <main class="flex-1 flex flex-col overflow-y-auto">
            
            <header class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
                <h1 class="text-xl font-semibold text-gray-800">PRESTAMOS</h1>
                <div class="flex items-center">
        <img src="<%=request.getContextPath()%>/img/FotoLogo.webp" alt="Logo del banco" class="h-12 object-contain">
        <span class="text-gray-700 font-bold text-lg ml-2">Universidad Tecnológica Nacional</span>
    </div>
            </header>

            <nav class="bg-gray-50 border-b border-gray-200 p-4">
             <%
    String paginaActual = "prestamos"; // Ejemplo: "movimientos", "transferencia", "prestamos", "personal", "home"
%>

<ul class="flex items-center justify-between w-full px-4 py-2 bg-white shadow">

    <!-- Home -->
    <li>
        <a href="<%=request.getContextPath()%>/ClientMode/homeClient.jsp"
            class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
            Home
        </a>
    </li>

    <!-- Opciones centradas -->
    <li class="flex space-x-6 mx-auto">
        <a href="/BancoParcial/ClientMode/TransferenciaClient.jsp"
           class="<%= paginaActual.equals("transferencia") ? "bg-blue-600 text-white" : "hover:bg-blue-600 hover:text-white text-gray-700" %> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
            Transferencia
        </a>

        <a href="/BancoParcial/ClientMode/movientosClient.jsp"
           class="<%= paginaActual.equals("movimientos") ? "bg-blue-600 text-white" : "hover:bg-blue-600 hover:text-white text-gray-700" %> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
            Movimientos
        </a>

        <a href="/BancoParcial/ClientMode/PrestamosClient.jsp"
           class="<%= paginaActual.equals("prestamos") ? "bg-blue-600 text-white" : "hover:bg-blue-600 hover:text-white text-gray-700" %> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
            Prestamos
        </a>

        <a href="<%=request.getContextPath()%>/ServletPersonalCliente"
           class="<%= paginaActual.equals("personal") ? "bg-blue-600 text-white" : "hover:bg-blue-600 hover:text-white text-gray-700" %> font-semibold py-2 px-4 rounded-md transition duration-200 ease-in-out">
            Personal
        </a>
    </li>

    <li></li>
</ul>
            </nav>

            <nav class="bg-gray-100 border-b border-gray-200 p-3">
                <ul class="flex space-x-4 justify-center">
                    <li><a href="/BancoParcial/ClientMode/PrestamosClient.jsp" class="hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Pedir Préstamos</a></li>
                    <li><a href="/BancoParcial/ClientMode/pagarPrestamoClient.jsp" class="bg-blue-500 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Pagar Préstamos</a></li>
                </ul>
            </nav>

            <div class="p-6 flex-1 flex flex-col justify-center items-center">
                <div class="bg-white p-8 rounded-lg shadow-md w-full max-w-2xl text-center">
                		<% if (request.getAttribute("mensaje") != null) { %>
    <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4 text-center font-semibold">
        <%= request.getAttribute("mensaje") %>
    </div>
<% } %>
                    <form action="<%=request.getContextPath()%>/ServletPagarPrestamoCliente" method="post" class="grid grid-cols-1 md:grid-cols-3 gap-x-8 gap-y-6 items-end">
                        
                        <div>
                            <label for="cuenta_a_debitar" class="block text-gray-700 text-lg font-semibold mb-2">Seleccione una cuenta</label>

                            <select name="cuenta_a_debitar" id="cuenta"
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

                        <div>
                          <label for="cuotaSeleccionada" class="block text-gray-700 text-lg font-semibold mb-2">Seleccione Cuota</label>
<select id="cuotaSeleccionada" name="cuotaSeleccionada"
    class="p-3 border border-gray-300 rounded-md w-full bg-white text-lg" onchange="actualizarImporte()">
    <option value="">-- Seleccione una cuota --</option>
    <%
        List<Cuota> cuotasPendientes = (List<Cuota>) request.getAttribute("cuotasPendientes");
        if (cuotasPendientes != null) {
            for (Cuota cuota : cuotasPendientes) {
    %>
        <option value="<%= cuota.getIdCuota() %>" data-importe="<%= cuota.getImporte() %>">
            Cuota #<%= cuota.getNroCuota() %> - $<%= cuota.getImporte() %>
        </option>
    <%
            }
        }
    %>
</select>

<!-- Mostrar el importe -->
<label for="importe_cuota" class="block text-gray-700 text-lg font-semibold mt-4 mb-2">Importe</label>
<p id="importe_cuota" class="text-gray-900 text-2xl font-bold">$0.00</p>

<!-- Campo oculto para enviar al servlet -->
<input type="hidden" name="importe_hardcodeado" id="importe_hidden" value="0.00">
                        <div class="col-span-1 md:col-span-3 flex justify-center space-x-6 pt-4">
                            <button type="submit"
    class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-md transition duration-200 text-base">
    PAGAR
</button>
<button type="button" onclick="this.form.reset(); actualizarImporte();"
    class="bg-gray-500 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded-md transition duration-200 text-base">
    CANCELAR
</button>
                        </div>
                    </form>
                </div>
            </div>

            <footer class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
                GRUPO_N7
            </footer>
        </main>

    </div>
    <script>
    function actualizarImporte() {
        const select = document.getElementById('cuotaSeleccionada');
        const selectedOption = select.options[select.selectedIndex];
        console.log("Cambió selección:", selectedOption);

        if (!selectedOption || !selectedOption.getAttribute('data-importe')) {
            document.getElementById('importe_cuota').innerText = '$0.00';
            document.getElementById('importe_hidden').value = '0.00';
            return;
        }

        const importe = selectedOption.getAttribute('data-importe');
        console.log("Importe seleccionado:", importe);
        document.getElementById('importe_cuota').innerText = `$${importe}`;
        document.getElementById('importe_hidden').value = importe;
    }
</script>
</body>
</html>