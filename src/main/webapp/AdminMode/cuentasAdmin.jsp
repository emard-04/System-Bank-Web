<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cuentas Admin - Tu Banco</title>
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

		<aside
			class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

			<div class="w-full h-48 bg-gray-300 mb-4 profile-photo-placeholder">
			</div>

<<<<<<< Updated upstream
<<<<<<< Updated upstream
            <nav class="bg-gray-50 border-b border-gray-200 p-4">
                <ul class="flex space-x-10 justify-center">
                    <li><a href="/BancoParcial/AdminMode/cuentasAdmin.jsp" class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md ...">Listado</a></li>
        			<li><a href="/BancoParcial/ServletAgregarCuentas?openAgregar=1" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md ...">Agregar</a></li>
        			<li><a href="/BancoParcial/AdminMode/cuentaAdmin_modificar.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md ...">Modificar</a></li>
        			<li><a href="/BancoParcial/AdminMode/cuentaAdmin_borrar.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md ...">Borrar</a></li>
                </ul>
            </nav>
=======
			<h3 class="text-xl font-bold text-gray-800 text-center mb-6">ADMIN</h3>
>>>>>>> Stashed changes
=======
			<h3 class="text-xl font-bold text-gray-800 text-center mb-6">ADMIN</h3>
>>>>>>> Stashed changes

			<a href="logout.jsp"
				class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
				Salir </a>
		</aside>

		<main class="flex-1 flex flex-col overflow-y-auto">

			<header
				class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
				<h1 class="text-xl font-semibold text-gray-800">CUENTAS</h1>
				<div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
			</header>

			 <nav class="bg-gray-50 border-b border-gray-200 p-4">
				<ul class="flex items-center justify-between w-full">
					<li><a href="HomeAdmin.jsp"
						class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Home</a>
					</li>

					<li class="flex space-x-10 mx-auto"><a
						href="cuentaAdmin.jsp"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a>
						<a href="cuentaAdmin_agregar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>
						<a href="cuentaAdmin_modificar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>
						<a href="cuentaAdmin_borrar.jsp"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a>
					</li>

					<li></li>
				</ul>
			</nav>

			<div class="p-6 flex-1 overflow-y-auto">
				<div class="bg-white rounded-lg shadow overflow-hidden">
					<table class="min-w-full divide-y divide-gray-200">
						<thead class="bg-red-700 text-white">
							<tr>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Nº
									Cuenta</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">DNI
									Cliente</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Fecha
									Creación</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Tipo</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">CBU</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Saldo</th>
							</tr>
						</thead>
						<tbody class="bg-white divide-y divide-gray-200">
							<tr>
								<td
									class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">202156</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">21569840</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">04-11-2024</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">Cuenta
									corriente</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">2189050469840</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">410000</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div
					class="bg-red-700 p-3 rounded-b-lg shadow-lg flex justify-end items-center mt-0">
					<button
						class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
						&larr;</button>
					<button
						class="bg-red-500 text-white font-bold p-2 mx-1 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
						1</button>
					<button
						class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
						2</button>
					<button
						class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
						3</button>
					<button
						class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
						&rarr;</button>
				</div>
			</div>

			<footer
				class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
				GRUPO_N7 </footer>
		</main>

	</div>
</body>
</html>