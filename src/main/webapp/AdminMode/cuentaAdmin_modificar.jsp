<%@ page import="Entidades.Usuario" %>
<%@ page import="Entidades.Persona" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.reflect.Array"%>
<%@ page import="java.util.List" %>
<%@ page import="Entidades.Cuenta" %>

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
    <title>Modificar Cuenta Admin - Tu Banco</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            font-family: 'Inter', sans-serif; /* O la fuente que prefieras */
        }
        .profile-photo-placeholder {
            background-size: cover;
            background-position: center;
        }
        /* Estilo para inputs de solo lectura si aplica */
        .read-only-input {
            background-color: #e2e8f0; /* bg-gray-200 */
            color: #4a5568; /* text-gray-700 */
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
            
            <div class="w-full h-48 bg-gray-300 mb-4 profile-photo-placeholder">
                </div>
            
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
                <h1 class="text-xl font-semibold text-gray-800">CUENTAS</h1>
                 <div class="flex items-center">
				<img src="<%=request.getContextPath()%>/img/FotoLogo.webp" alt="Logo del banco" class="h-12 object-contain">
				<span class="text-gray-700 font-bold text-lg">Universidad Tecnológica Nacional</span>
				</div>
            </header>

              <nav class="bg-gray-50 border-b border-gray-200 p-4">
				<ul class="flex items-center justify-between w-full">
					<li><a href="<%=request.getContextPath()%>/AdminMode/HomeAdmin.jsp" 
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Home</a>
					</li>

					<li class="flex space-x-10 mx-auto"><a
						href="/BancoParcial/ServletListarCuentas?openListar=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a>
						<a href="/BancoParcial/ServletAgregarCuentas?openAgregar=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>
						<a href="/BancoParcial/ServletModificarCuentas?openModificar=1"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>
						<a href="<%=request.getContextPath()%>/ServletBorrarCuenta"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a>
					</li>

					<li></li>
				</ul>
			</nav>

            <div class="p-6 flex-1 flex flex-col items-center">
                <div class="bg-white p-8 rounded-lg shadow-md w-full max-w-4xl">
                    <div class="mb-8 pb-4 border-b border-gray-200 flex items-center justify-center">
                        <label for="seleccionar_cuenta_dni" class="block text-gray-700 text-lg font-semibold mr-4">Seleccionar Nº de Cuenta:</label>
                      
                        <select
                            id="seleccionar_cuenta_dni"
                            name="seleccionar_cuenta_dni"
                            class="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-base bg-white w-64"
                            >
                             <option value="">-- Seleccione un Nº de Cuenta --</option>
                               <% if(request.getAttribute("ListaCuenta")!=null){
                        	ArrayList<Cuenta> cuentas=(ArrayList<Cuenta>)request.getAttribute("ListaCuenta");
                        
                        
    for (Cuenta c : cuentas) { %>
        <option value="<%= c.getNroCuenta() %>">
            <%= c.getNroCuenta() %> (Cliente: <%= c.getUsuario().getPersona().getDni() %>)
        </option>
    <% }}%>
                        </select>
                    </div>

                    <form action="/BancoParcial/ServletModificarCuentas" method="post" class="grid grid-cols-1 md:grid-cols-3 gap-x-8 gap-y-6">
                        <input type="hidden" id="cuenta_id_modificar" name="cuenta_id_modificar" value="">

                        <div>
                            <label for="nro_cuenta_mod" class="block text-gray-700 text-lg font-semibold mb-2">Nro de Cuenta</label>
                            <input
                                type="text"
                                id="nro_cuenta_mod"
                                name="nro_cuenta_mod"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg read-only-input"
                                readonly
                            >
                        </div>
                        <div>
                            <label for="dni_cliente_mod" class="block text-gray-700 text-lg font-semibold mb-2">DNI del Cliente</label>
                            <input
                                type="text"
                                id="dni_cliente_mod"
                                name="dni_cliente_mod"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>
                        <div>
                            <label for="fecha_creacion_mod" class="block text-gray-700 text-lg font-semibold mb-2">Fec Creacion</label>
                            <input
                                type="date"
                                id="fecha_creacion_mod"
                                name="fecha_creacion_mod"
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white"
                                required
                            >
                        </div>

                        <div class="col-span-1 md:col-span-1 flex flex-col justify-end">
                            <label class="block text-gray-700 text-lg font-semibold mb-2">TIPO</label>
                            <div class="flex items-center space-x-6">
                                <div class="flex items-center">
                                    <input type="radio" id="cuenta_corriente_mod" name="tipo_cuenta_mod" value="Cuenta Corriente" class="h-5 w-5 text-blue-600 border-gray-300 focus:ring-blue-500" required>
                                    <label for="cuenta_corriente_mod" class="ml-2 text-gray-800 text-lg">Cuenta Corriente</label>
                                </div>
                                <div class="flex items-center">
                                    <input type="radio" id="caja_ahorro_mod" name="tipo_cuenta_mod" value="Caja de Ahorro" class="h-5 w-5 text-blue-600 border-gray-300 focus:ring-blue-500">
                                    <label for="caja_ahorro_mod" class="ml-2 text-gray-800 text-lg">Caja de Ahorro</label>
                                </div>
                            </div>
                        </div>

                        <div>
                            <label for="cbu_mod" class="block text-gray-700 text-lg font-semibold mb-2">CBU</label>
                            <input
                                type="text"
                                id="cbu_mod"
                                name="cbu_mod"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full text-lg"
                                required
                                readonly
                            >
                        </div>

                        <div>
                            <label for="saldo_mod" class="block text-gray-700 text-lg font-semibold mb-2">SALDO</label>
                            <input
                                type="number"
                                id="saldo_mod"
                                name="saldo_mod"
                                placeholder=""
                                min=0
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                                
                            >
                        </div>

                        <div class="col-span-1 md:col-span-3 flex justify-center space-x-6 pt-4">
                            <button
                                type="submit" name="btnModificar"
                                class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-md focus:outline-none focus:shadow-outline-blue focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl"
                            >
                                Guardar Cambios
                            </button>
                            <button
                                type="button"
                                class="bg-gray-500 hover:bg-gray-600 text-white font-bold py-3 px-6 rounded-md focus:outline-none focus:shadow-outline-gray focus:ring-2 focus:ring-gray-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl"
                            >
                                Cancelar
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
</body>
<script>
    document.getElementById('seleccionar_cuenta_dni').addEventListener('change', function() {
        var nroCuenta = this.value;
        if (!nroCuenta) {
            // limpiar campos si se deselecciona
            limpiarCampos();
            return;
        }

        fetch('/BancoParcial/ServletModificarCuentas?nroCuenta=' + nroCuenta)
            .then(response => {
                if (!response.ok) throw new Error('Cuenta no encontrada');
                return response.json();
            })
            .then(data => {
                document.getElementById('nro_cuenta_mod').value = data.nroCuenta;
                document.getElementById('dni_cliente_mod').value = data.dniCliente;
                document.getElementById('fecha_creacion_mod').value = data.fechaCreacion;
                // marcar radio según tipo de cuenta
                if(data.tipoCuenta == "1") {
                    document.getElementById('caja_ahorro_mod').checked = true;
                } else {
                    document.getElementById('cuenta_corriente_mod').checked = true;
                }
                document.getElementById('cbu_mod').value = data.cbu;
                document.getElementById('saldo_mod').value = data.saldo;
            })
            .catch(error => {
                alert(error.message);
                limpiarCampos();
            });
    });

    function limpiarCampos() {
        document.getElementById('nro_cuenta_mod').value = '';
        document.getElementById('dni_cliente_mod').value = '';
        document.getElementById('fecha_creacion_mod').value = '';
        document.getElementById('cuenta_corriente_mod').checked = false;
        document.getElementById('caja_ahorro_mod').checked = false;
        document.getElementById('cbu_mod').value = '';
        document.getElementById('saldo_mod').value = '';
    }
</script>
</html>