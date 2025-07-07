<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<script src="https://cdn.tailwindcss.com"></script>
<style>
body {
	font-family: 'Inter', sans-serif;
}
</style>
</head>
<body
	class="bg-gray-100 flex items-center justify-center min-h-screen p-4">
	<%
	String mensaje = (String) session.getAttribute("mensaje");
	if (mensaje != null) {
	%>
	<script>alert("<%=mensaje%>")</script>

	<%
	session.removeAttribute("mensaje");
	}
	%>
	<div
		class="bg-white p-8 rounded-xl shadow-lg w-full max-w-md border border-gray-200">
		<h2 class="text-4xl font-extrabold text-center text-gray-800 mb-8">Iniciar
			Sesión</h2>

		<form action="/BancoParcial/ServletLogin" method="post">
			<div class="mb-6">
				<label for="username"
					class="block text-gray-700 text-sm font-medium mb-2">
					Usuario </label> <input type="text" id="username" name="username"
					placeholder="Ingresa tu usuario"
					class="shadow-sm appearance-none border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-200 ease-in-out"
					required>
			</div>
			<div class="mb-8">
				<label for="password"
					class="block text-gray-700 text-sm font-medium mb-2">
					Contraseña </label> <input type="password" id="password" name="password"
					placeholder="Ingresa tu contraseña"
					class="shadow-sm appearance-none border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-200 ease-in-out"
					required>
			</div>

			<button type="submit"
				class="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded-lg focus:outline-none focus:shadow-outline-blue focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105">
				Entrar</button>
		</form>
	</div>
</body>
</html>