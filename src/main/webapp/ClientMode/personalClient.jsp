<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Entidades.Usuario" %>
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
<body class="bg-gray-100 h-screen overflow-hidden">
    <div class="flex h-full">

        <aside class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">
            
            <div class="w-full h-48 bg-gray-300 mb-4 profile-photo-placeholder">
                </div>
            
            <h3 class="text-xl font-bold text-gray-800 text-center mb-1">Nombre Apellido</h3>
            <p class="text-md text-gray-600 text-center mb-6">Saldo: $$$</p>

            <div class="relative w-full mb-6">
                <button class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-semibold py-2 px-4 rounded-md w-full text-left focus:outline-none focus:shadow-outline flex items-center justify-between" id="dropdown-button">
                    Cuentas
                    <svg class="w-4 h-4 fill-current ml-2" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
                </button>
                <div id="dropdown-list" class="absolute bg-white border border-gray-300 rounded-md shadow-lg mt-1 w-full z-10 hidden">
                    <a href="#" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Cuenta 1</a>
                    <a href="#" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Cuenta 2</a>
                    <a href="#" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Ver todas</a>
                </div>
            </div>

            <a href="logout.jsp" class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
                Salir
            </a>
        </aside>

        <main class="flex-1 flex flex-col overflow-y-auto">
            
            <header class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
                <h1 class="text-xl font-semibold text-gray-800">PERSONAL</h1>
                <div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
            </header>
            
            <nav class="bg-gray-50 border-b border-gray-200 p-4">
				<ul class="flex space-x-4 justify-center">
					<li><a href="/BancoParcial/ClientMode/TransferenciaClient.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Transferencia</a></li>
					<li><a href="/BancoParcial/ClientMode/movientosClient.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Movimientos</a></li>
					<li><a href="/BancoParcial/ClientMode/PrestamosClient.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Prestamos</a></li>
					<li><a href="<%=request.getContextPath()%>/ServletPersonalCliente" 
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Personal</a></li>
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
                            <input type="text" value="<%= usuario.getPersona().getProvincia() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
                        </div>
                        <div>
                            <label class="block text-gray-700 text-lg font-semibold mb-2">Localidad</label>
                            <input type="text" value="<%= usuario.getPersona().getLocalidad() %>" class="p-3 border rounded-md w-full text-lg read-only-input" readonly>
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