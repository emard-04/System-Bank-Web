<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</head>
<body class="bg-gray-100 h-screen overflow-hidden">
    <div class="flex h-full">

       	<aside
			class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

			<div class="w-full h-48 bg-gray-300 mb-4 profile-photo-placeholder">
			</div>

			<h3 class="text-xl font-bold text-gray-800 text-center mb-1">Nombre
				Apellido</h3>
			<p class="text-md text-gray-600 text-center mb-6">Saldo: $$$</p>

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

			<a href="#"
   onclick="confirmarLogout(event)"
   class="mt-auto bg-red-500 hover:bg-red-600 text-white text-center font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:shadow-outline block">
   Salir
</a>
		</aside>


        <main class="flex-1 flex flex-col overflow-y-auto">
            
            <header class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
                <h1 class="text-xl font-semibold text-gray-800">PRESTAMOS</h1>
                <div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
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
                    <li><a href="PrestamosClient.jsp" class="hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Pedir Préstamos</a></li>
                    <li><a href="pagarPrestamosClient.jsp" class="bg-blue-500 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Pagar Préstamos</a></li>
                </ul>
            </nav>

            <div class="p-6 flex-1 flex flex-col justify-center items-center">
                <div class="bg-white p-8 rounded-lg shadow-md w-full max-w-2xl text-center">
                    <form action="PagarCuotaPrestamoServlet" method="post" class="grid grid-cols-1 md:grid-cols-3 gap-x-8 gap-y-6 items-end">
                        
                        <div>
                            <label for="cuenta_a_debitar" class="block text-gray-700 text-lg font-semibold mb-2">Seleccione una cuenta</label>
                            <select
                                id="cuenta_a_debitar"
                                name="cuenta_a_debitar"
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white"
                                required
                            >
                                <option value="">-- Seleccione cuenta a debitar --</option>
                                <option value="202156">Cuenta Corriente 202156</option>
                                <option value="202157">Caja de Ahorro 202157</option>
                            </select>
                        </div>

                        <div>
                            <label for="nro_cuota_a_pagar" class="block text-gray-700 text-lg font-semibold mb-2">Cuota a pagar</label>
                            <input
                                type="number"
                                id="nro_cuota_a_pagar"
                                name="nro_cuota_a_pagar"
                                placeholder="Ej: 1, 2, 3..."
                                min="1"
                                max="36"
                                class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                                required
                            >
                        </div>
                        
                        <div>
                            <label for="importe_cuota" class="block text-gray-700 text-lg font-semibold mb-2">Importe</label>
                            <p id="importe_cuota" class="text-gray-900 text-2xl font-bold"> $5000.00 </p>
                            <input type="hidden" name="importe_hardcodeado" value="5000.00"> </div>

                        <div class="col-span-1 md:col-span-3 flex justify-center space-x-6 pt-4">
                            <button
                                type="submit"
                                class="bg-green-600 hover:bg-green-700 text-white font-bold py-3 px-6 rounded-md focus:outline-none focus:shadow-outline-green focus:ring-2 focus:ring-green-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl"
                            >
                                PAGAR
                            </button>
                            <button
                                type="button"
                                class="bg-gray-500 hover:bg-gray-600 text-white font-bold py-3 px-6 rounded-md focus:outline-none focus:shadow-outline-gray focus:ring-2 focus:ring-gray-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105 text-xl"
                            >
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
</body>
</html>