<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="Entidades.*"%>
<%
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    String telefono = (String) request.getAttribute("telefonoUsuario");

    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
	    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Información Personal - Tu Banco</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            font-family: 'Inter', sans-serif; /* O la fuente que prefieras */
        }
        .profile-photo-placeholder {
            background-size: cover;
            background-position: center;
        }
        /* Estilo específico para los inputs de solo lectura */
        .read-only-input {
            background-color: #e2e8f0; /* bg-gray-200 */
            color: #4a5568; /* text-gray-700 */
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
<%Usuario us= (Usuario)session.getAttribute("usuarioLogueado"); %>
<body class="bg-gray-100 h-screen overflow-hidden">
    <div class="flex h-full">

        <aside 
 class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

            <div class="w-full h-48 bg-gray-300 mb-4 profile-photo-placeholder">
            </div>

            <h3 class="text-xl font-bold text-gray-800 text-center mb-1">
                <%= us.getPersona().getNombre() %> <%= us.getPersona().getApellido() %>
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
            
            <header class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
                <h1 class="text-xl font-semibold text-gray-800">PERSONAL</h1>
                <div class="flex items-center">
        <img src="<%=request.getContextPath()%>/img/FotoLogo.webp" alt="Logo del banco" class="h-12 object-contain">
        <span class="text-gray-700 font-bold text-lg ml-2">Universidad Tecnológica Nacional</span>
    </div>
            </header>
            
            <nav class="bg-gray-50 border-b border-gray-200 p-4">
				<%
    String paginaActual = "personal"; // Ejemplo: "movimientos", "transferencia", "prestamos", "personal", "home"
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

            <div class="p-6 flex-1 flex flex-col justify-center items-center">
                <div class="bg-white p-8 rounded-lg shadow-md w-full max-w-4xl">
                    <form class="grid grid-cols-1 md:grid-cols-3 gap-x-8 gap-y-6">
                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Nombre</label>
                            <input type="text" value="<%= usuario.getPersona().getNombre() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>
                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Apellido</label>
                            <input type="text" value="<%= usuario.getPersona().getApellido() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>
                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">DNI</label>
                            <input type="text" value="<%= usuario.getPersona().getDni() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>

                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Provincia</label>
                            <input type="text" value="<%= request.getAttribute("nombreProvincia") %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>
                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Localidad</label>
                            <input type="text" value="<%= request.getAttribute("nombreLocalidad") %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>
                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Dirección</label>
                            <input type="text" value="<%= usuario.getPersona().getDireccion() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>

                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Sexo</label>
                            <input type="text" value="<%= usuario.getPersona().getSexo() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>
                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Fecha de Nacimiento</label>
                            <input type="text" value="<%= usuario.getPersona().getFechaNacimiento() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>
                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Nacionalidad</label>
                            <input type="text" value="<%= usuario.getPersona().getNacionalidad() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>

                        <div>
    <label class="block text-gray-700 text-lg font-semibold mb-2">Teléfono</label>
    <input type="text" value="<%= telefono != null ? telefono : "" %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly />
</div>
                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">CUIL</label>
                            <input type="text" value="<%= usuario.getPersona().getCuil() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>
                        <div></div>

                        <div class="col-span-1 md:col-span-2">
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Usuario</label>
                            <input type="text" value="<%= usuario.getNombreUsuario() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>
                        <div class="col-span-1 md:col-span-1">
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Correo</label>
                            <input type="email" value="<%= usuario.getPersona().getCorreoElectronico() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
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