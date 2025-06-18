<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Modificar Cliente Admin - Tu Banco</title>
<script src="https://cdn.tailwindcss.com"></script>
<style>
body {
	font-family: 'Inter', sans-serif; /* O la fuente que prefieras */
}

.profile-photo-placeholder {
	background-size: cover;
	background-position: center;
}

.read-only-input {
	background-color: #e2e8f0; /* bg-gray-200 */
	color: #4a5568; /* text-gray-700 */
}
</style>
</head>
<body class="bg-gray-100 h-screen overflow-hidden">
	<div class="flex h-full">

		<aside
			class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

			<div class="w-full h-48 bg-gray-300 mb-4 profile-photo-placeholder">
			</div>

			<h3 class="text-xl font-bold text-gray-800 text-center mb-6">ADMIN</h3>

			<a href="logout.jsp"
				class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
				Salir </a>
		</aside>

		<main class="flex-1 flex flex-col overflow-y-auto">

			<header
				class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
				<h1 class="text-xl font-semibold text-gray-800">CLIENTES</h1>
				<div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
			</header>

			<nav class="bg-gray-50 border-b border-gray-200 p-4">
				<ul class="flex items-center justify-between w-full">
					<li><a href="HomeAdmin.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Home</a>
					</li>

					<li class="flex space-x-10 mx-auto"><a
						href="clientesAdmin.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a>
						<a href="clientesAdmin_agregar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>
						<a href="clienteAdmin_modificar.jsp"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>
						<a href="clientesAdmin_borrar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a>
					</li>

					<li></li>
				</ul>
			</nav>

			<div class="p-6 flex-1 flex flex-col items-center">
				<div class="bg-white p-8 rounded-lg shadow-md w-full max-w-4xl">
					<div
						class="mb-8 pb-4 border-b border-gray-200 flex items-center justify-center">
						<label for="seleccionar_cliente_dni"
							class="block text-gray-700 text-lg font-semibold mr-4">Seleccionar
							DNI del Cliente:</label> <select id="seleccionar_cliente_dni"
							name="seleccionar_cliente_dni"
							class="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-base bg-white w-64">
							<option value="">-- Seleccione un DNI --</option>
							<option value="12345678">12345678 (Juan Pérez)</option>
							<option value="87654321">87654321 (María García)</option>
							<option value="11223344">11223344 (Pedro López)</option>
						</select>
					</div>

					<form action="ModificarClienteServlet" method="post"
						class="grid grid-cols-1 md:grid-cols-3 gap-x-8 gap-y-6">
						<input type="hidden" id="dni_original_modificar"
							name="dni_original_modificar" value="">

						<div>
							<label for="dni_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">DNI</label>
							<input type="text" id="dni_mod" name="dni_mod" placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg read-only-input"
								readonly>
						</div>
						<div>
							<label for="nombre_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Nombre</label>
							<input type="text" id="nombre_mod" name="nombre_mod"
								placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
								required>
						</div>
						<div>
							<label for="apellido_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Apellido</label>
							<input type="text" id="apellido_mod" name="apellido_mod"
								placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
								required>
						</div>

						<div>
							<label for="localidad_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Localidad</label>
							<input type="text" id="localidad_mod" name="localidad_mod"
								placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
								required>
						</div>
						<div>
							<label for="provincia_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Provincia</label>
							<input type="text" id="provincia_mod" name="provincia_mod"
								placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
								required>
						</div>
						<div>
							<label for="direccion_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Direccion</label>
							<input type="text" id="direccion_mod" name="direccion_mod"
								placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
								required>
						</div>

						<div>
							<label for="nacionalidad_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Nacionalidad</label>
							<input type="text" id="nacionalidad_mod" name="nacionalidad_mod"
								placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
								required>
						</div>
						<div>
							<label for="fecha_nacimiento_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Fec
								Nacimiento</label> <input type="date" id="fecha_nacimiento_mod"
								name="fecha_nacimiento_mod"
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white"
								required>
						</div>
						<div>
							<label for="correo_electronico_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Correo
								Electronico</label> <input type="email" id="correo_electronico_mod"
								name="correo_electronico_mod" placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
								required>
						</div>

						<div>
							<label for="sexo_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Sexo</label>
							<select id="sexo_mod" name="sexo_mod"
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white"
								required>
								<option value="">Seleccione</option>
								<option value="M">Masculino</option>
								<option value="F">Femenino</option>
								<option value="X">Otro</option>
							</select>
						</div>
						<div>
							<label for="usuario_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Usuario</label>
							<input type="text" id="usuario_mod" name="usuario_mod"
								placeholder=""
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
								required>
						</div>
						<div>
							<label for="contrasena_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Contraseña</label>
							<input type="password" id="contrasena_mod" name="contrasena_mod"
								placeholder="Dejar en blanco para no cambiar"
								class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg">
						</div>

						<div
							class="col-span-1 md:col-span-3 flex justify-center space-x-6 pt-4">
							<button type="submit"
								class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-md focus:outline-none focus:shadow-outline-blue focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl">
								Modificar</button>
							<button type="button"
								class="bg-gray-500 hover:bg-gray-600 text-white font-bold py-3 px-6 rounded-md focus:outline-none focus:shadow-outline-gray focus:ring-2 focus:ring-gray-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl">
								Cancelar</button>
						</div>
					</form>
				</div>
			</div>

			<footer
				class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
				GRUPO_N7 </footer>
		</main>

	</div>
</html>