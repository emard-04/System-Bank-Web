<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Cuenta Admin - Tu Banco</title>
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
            
            <a href="logout.jsp" class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
                Salir
            </a>
        </aside>

        <main class="flex-1 flex flex-col overflow-y-auto">
            
            <header class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
                <h1 class="text-xl font-semibold text-gray-800">CUENTAS</h1>
                <div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
            </header>

            <nav class="bg-gray-50 border-b border-gray-200 p-4">
<<<<<<< Updated upstream
                <ul class="flex space-x-10 justify-center">
                    <li><a href="/BancoParcial/AdminMode/cuentasAdmin.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md ...">Listado</a></li>
        			<li><a href="/BancoParcial/ServletAgregarCuentas?openAgregar=1" class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md ...">Agregar</a></li>
        			<li><a href="/BancoParcial/AdminMode/cuentaAdmin_modificar.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md ...">Modificar</a></li>
        			<li><a href="/BancoParcial/AdminMode/cuentaAdmin_borrar.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md ...">Borrar</a></li>
                </ul>
            </nav>
=======
				<ul class="flex items-center justify-between w-full">
					<li><a href="HomeAdmin.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Home</a>
					</li>

					<li class="flex space-x-10 mx-auto"><a
						href="cuentasAdmin.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a>
						<a href="cuentaAdmin_agregar.jsp"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>
						<a href="cuentaAdmin_modificar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>
						<a href="cuentaAdmin_borrar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a>
					</li>

					<li></li>
				</ul>
			</nav>

>>>>>>> Stashed changes

            <div class="p-6 flex-1 flex flex-col justify-center items-center">
                <div class="bg-white p-8 rounded-lg shadow-md w-full max-w-4xl">
                <% if (request.getParameter("error") != null) { %>
        <div class="bg-red-100 text-red-700 p-4 rounded-md mb-4 text-center font-semibold">
            <%= request.getParameter("error") %>
        </div>
    <% } %>

    <% if (request.getParameter("msg") != null) { %>
        <div class="bg-green-100 text-green-700 p-4 rounded-md mb-4 text-center font-semibold">
            <%= request.getParameter("msg") %>
        </div>
    <% } 
    int nroCuenta=0;
    if(request.getAttribute("nroCuenta")!=null){
    	
    	 nroCuenta=(int)request.getAttribute("nroCuenta");
    }
    %>
                    <form action="/BancoParcial/ServletAgregarCuentas" method="post" class="grid grid-cols-1 md:grid-cols-3 gap-x-8 gap-y-6">
                        <div>
                            <label for="nro_cuenta" class="block text-gray-700 text-lg font-semibold mb-2">Nro de Cuenta</label>
                            <input
                                type="number"
                                id="nro_cuenta"
                                name="nro_cuenta"
                                value=<%=nroCuenta%>
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full text-lg read-only-input"
                                readonly
                                required
                            >
                        </div>
                        <div>
                            <label for="dni_cliente" class="block text-gray-700 text-lg font-semibold mb-2">DNI del Cliente</label>
                            <input
                                type="text"
                                id="dni_cliente"
                                name="dni_cliente"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>
                        <div>
                            <label for="fecha_creacion" class="block text-gray-700 text-lg font-semibold mb-2">Fec Creacion</label>
                            <input
                                type="date"
                                id="fecha_creacion"
                                name="fecha_creacion"
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white"
                                required
                            >
                        </div>

                        <div class="col-span-1 md:col-span-1 flex flex-col justify-end">
                            <label class="block text-gray-700 text-lg font-semibold mb-2">TIPO</label>
                            <div class="flex items-center space-x-6">
                                <div class="flex items-center">
                                    <input type="radio" id="cuenta_corriente" name="tipo_cuenta" value="Corriente" class="h-5 w-5 text-blue-600 border-gray-300 focus:ring-blue-500" required>
                                    <label for="cuenta_corriente" class="ml-2 text-gray-800 text-lg">Cuenta Corriente</label>
                                </div>
                                <div class="flex items-center">
                                    <input type="radio" id="caja_ahorro" name="tipo_cuenta" value="Ahorro" class="h-5 w-5 text-blue-600 border-gray-300 focus:ring-blue-500">
                                    <label for="caja_ahorro" class="ml-2 text-gray-800 text-lg">Caja de Ahorro</label>
                                </div>
                            </div>
                        </div>

                        <div>
                            <label for="cbu" class="block text-gray-700 text-lg font-semibold mb-2">CBU</label>
                            <input
                                type="text"
                                id="cbu"
                                name="cbu"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>

                        <div>
                            <label for="saldo" class="block text-gray-700 text-lg font-semibold mb-2">SALDO</label>
                            <input
                                type="number"
                                id="saldo"
                                name="saldo"
                                value="10000"
                                class="p-3 border border-gray-300 rounded-md w-full text-lg read-only-input"
                                readonly
                                required
                            >
                        </div>

                        <div class="col-span-1 md:col-span-3 flex justify-center space-x-6 pt-4">
                            <button
                                type="submit"
                                class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-md focus:outline-none focus:shadow-outline-blue focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl"
                            >
                                Guardar
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
</html>