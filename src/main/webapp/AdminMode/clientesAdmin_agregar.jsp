<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Cliente Admin - Tu Banco</title>
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
                <h1 class="text-xl font-semibold text-gray-800">CLIENTES</h1>
                <div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
            </header>

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
            <nav class="bg-gray-50 border-b border-gray-200 p-4">
                <ul class="flex space-x-10 justify-center">
                    <li><a href="/BancoParcial/clientesAdmin.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a></li>
                    <li><a href="/BancoParcial/clientesAdmin_agregar.jsp" class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a></li>
                    <li><a href="/BancoParcial/clientesAdmin_modificar.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a></li>
                    <li><a href="/BancoParcial/clientesAdmin_borrar.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a></li>
                </ul>
            </nav>
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
           <nav class="bg-gray-50 border-b border-gray-200 p-4">
				<ul class="flex items-center justify-between w-full">
					<li><a href="HomeAdmin.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Home</a>
					</li>

					<li class="flex space-x-10 mx-auto"><a
						href="clientesAdmin.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a>
						<a href="clientesAdmin_agregar.jsp"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>
						<a href="clienteAdmin_modificar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>
						<a href="clientesAdmin_borrar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a>
					</li>

					<li></li>
				</ul>
			</nav>
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

            <div class="p-6 flex-1 flex flex-col justify-center items-center">
                <div class="bg-white p-8 rounded-lg shadow-md w-full max-w-4xl">
                    <form action="/BancoParcial/ServletAgregarCliente" method="post" class="grid grid-cols-1 md:grid-cols-3 gap-x-8 gap-y-6">
                        <div>
                            <label for="nombre" class="block text-gray-700 text-lg font-semibold mb-2">Nombre</label>
                            <input
                                type="text"
                                id="nombre"
                                name="nombre"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>
                        <div>
                            <label for="apellido" class="block text-gray-700 text-lg font-semibold mb-2">Apellido</label>
                            <input
                                type="text"
                                id="apellido"
                                name="apellido"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>
                        <div>
                            <label for="dni" class="block text-gray-700 text-lg font-semibold mb-2">DNI</label>
                            <input
                                type="text"
                                id="dni"
                                name="dni"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>

                        <div>
                            <label for="localidad" class="block text-gray-700 text-lg font-semibold mb-2">Localidad</label>
                            <input
                                type="text"
                                id="localidad"
                                name="localidad"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>
                        <div>
                            <label for="provincia" class="block text-gray-700 text-lg font-semibold mb-2">Provincia</label>
                            <input
                                type="text"
                                id="provincia"
                                name="provincia"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>
                        <div>
                            <label for="direccion" class="block text-gray-700 text-lg font-semibold mb-2">Direccion</label>
                            <input
                                type="text"
                                id="direccion"
                                name="direccion"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>

                        <div>
                            <label for="nacionalidad" class="block text-gray-700 text-lg font-semibold mb-2">Nacionalidad</label>
                            <input
                                type="text"
                                id="nacionalidad"
                                name="nacionalidad"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>
                        <div>
                            <label for="fecha_nacimiento" class="block text-gray-700 text-lg font-semibold mb-2">Fec Nacimiento</label>
                            <input
                                type="date"
                                id="fecha_nacimiento"
                                name="fecha_nacimiento"
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white"
                                required
                            >
                        </div>
                        <div>
                            <label for="correo_electronico" class="block text-gray-700 text-lg font-semibold mb-2">Correo Electronico</label>
                            <input
                                type="email"
                                id="correo_electronico"
                                name="correo_electronico"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>

                        <div>
                            <label for="sexo" class="block text-gray-700 text-lg font-semibold mb-2">Sexo</label>
                            <select
                                id="sexo"
                                name="sexo"
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white"
                                required
                            >
                                <option value="">Seleccione</option>
                                <option value="M">Masculino</option>
                                <option value="F">Femenino</option>
                                <option value="X">Otro</option>
                            </select>
                        </div>
                        <div>
                            <label for="usuario" class="block text-gray-700 text-lg font-semibold mb-2">Usuario</label>
                            <input
                                type="text"
                                id="usuario"
                                name="usuario"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>
                        <div>
                            <label for="contrasena" class="block text-gray-700 text-lg font-semibold mb-2">Contraseña</label>
                            <input
                                type="password"
                                id="contrasena"
                                name="contrasena"
                                placeholder=""
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>

                        <div class="col-span-1 md:col-span-3 flex justify-center pt-4">
                            <button
                                type="submit"
                                class="bg-green-600 hover:bg-green-700 text-white font-bold py-3 px-8 rounded-md focus:outline-none focus:shadow-outline-green focus:ring-2 focus:ring-green-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl"
                            >
                                AGREGAR
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