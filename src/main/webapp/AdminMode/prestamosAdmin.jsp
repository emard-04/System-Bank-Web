<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Préstamos Admin - Tu Banco</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            font-family: 'Inter', sans-serif; /* O la fuente que prefieras */
        }
        .profile-photo-placeholder {
            background-size: cover;
            background-position: center;
        }
        /* Estilos para los íconos de Aceptar/Rechazar */
        .icon-button {
            width: 2.5rem; /* 40px */
            height: 2.5rem; /* 40px */
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 0.375rem; /* rounded-md */
            transition: all 0.2s ease-in-out;
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
                <h1 class="text-xl font-semibold text-gray-800">PRÉSTAMOS</h1>
                <div class="text-gray-700 font-bold">LOGO / NAME DEL BANCO</div>
            </header>

            <nav class="bg-gray-50 border-b border-gray-200 p-4">
                <ul class="flex space-x-10 justify-center">
                    <li><a href="clientesAdmin.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Clientes</a></li>
                    <li><a href="cuentasAdmin.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Cuentas</a></li>
                    <li><a href="prestamosAdmin.jsp" class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Préstamos</a></li>
                    <li><a href="reportesAdmin.jsp" class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Reportes</a></li>
                </ul>
            </nav>

            <div class="p-6 flex-1 overflow-y-auto">
                <div class="mb-6 flex items-center">
                    <label for="prestamos_autorizar" class="block text-gray-700 text-lg font-semibold mr-4 whitespace-nowrap">Préstamos a Autorizar:</label>
                    <select
                        id="prestamos_autorizar"
                        name="prestamos_autorizar"
                        class="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-base bg-white"
                    >
                        <option value="">Seleccione</option>
                        <option value="10020">10020</option>
                        <option value="10021">10021</option>
                        <option value="10022">10022</option>
                    </select>
                </div>

                <div class="bg-white rounded-lg shadow overflow-hidden">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-red-700 text-white">
                            <tr>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Nº Préstamo</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">DNI Cliente</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Fecha</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Importe a Pagar</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Importe Pedido</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Plazo de Pago</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Monto por Mes</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Cuotas</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Acciones</th> </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">10020</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">123456789</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">04-11-2024</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">100000</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">100000</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">-</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">-</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">3</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                    <div class="flex space-x-2">
                                        <button class="icon-button bg-green-500 hover:bg-green-600 text-white focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50">
                                            &#10003; </button>
                                        <button class="icon-button bg-red-500 hover:bg-red-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50">
                                            &#10006; </button>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                    </table>
                </div>

                <div class="bg-red-700 p-3 rounded-b-lg shadow-lg flex justify-end items-center mt-0">
                    <button class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
                        &larr; </button>
                    <button class="bg-red-500 text-white font-bold p-2 mx-1 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
                        1
                    </button>
                    <button class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
                        2
                    </button>
                    <button class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
                        3
                    </button>
                    <button class="p-2 mx-1 text-white opacity-75 hover:opacity-100 rounded-md focus:outline-none focus:ring-2 focus:ring-white">
                        &rarr; </button>
                </div>
            </div>

            <footer class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
                GRUPO_N7
            </footer>
        </main>

    </div>

    </body>
</html>