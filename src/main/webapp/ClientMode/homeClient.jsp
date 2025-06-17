<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<body class="bg-gray-100 h-screen overflow-hidden">
	<div class="flex h-full">

		<aside
			class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

			<div
				class="w-28 h-28 rounded-full bg-gray-300 mx-auto mb-4 profile-photo-placeholder">
			</div>

			<h3 class="text-xl font-bold text-gray-800 text-center mb-1">Nombre
				Apellido</h3>
			<p class="text-md text-gray-600 text-center mb-6">Saldo: $XXXX.XX</p>

			<div class="relative w-full mb-6">
				<button
					class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-semibold py-2 px-4 rounded-md w-full text-left focus:outline-none focus:shadow-outline flex items-center justify-between"
					id="dropdown-button">
					Cuentas
					<svg class="w-4 h-4 fill-current ml-2" viewBox="0 0 20 20">
						<path
							d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z" /></svg>
				</button>
				<div id="dropdown-list"
					class="absolute bg-white border border-gray-300 rounded-md shadow-lg mt-1 w-full z-10 hidden">
					<a href="#"
						class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Cuenta
						1</a> <a href="#"
						class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Cuenta
						2</a> <a href="#"
						class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Ver
						todas</a>
				</div>
			</div>

			<a href="logout.jsp"
				class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
				Salir </a>
		</aside>

		<main class="flex-1 flex flex-col overflow-y-auto">

			<header
				class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
				<div class="text-gray-700 font-bold">LOGO / NOMBRE DEL BANCO</div>
			</header>

			<nav class="bg-gray-50 border-b border-gray-200 p-4">
				<ul class="flex space-x-10 justify-center">
					<li><a href="TransferenciaClient.jsp"
						class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Transferencia</a></li>
					<li><a href="movientosClient.jsp"
						class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Movimientos</a></li>
					<li><a href="PrestamosClient.jsp"
						class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Préstamos</a></li>
					<li><a href="personalClient.jsp"
						class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Personal</a></li>
				</ul>
			</nav>

			<div class="p-6 flex-1 overflow-y-auto"></div>

			<footer
				class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
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