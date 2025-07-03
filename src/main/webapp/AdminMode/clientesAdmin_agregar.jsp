<%@ page import="Entidades.Usuario" %>
<%@ page import="Entidades.Persona" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<%
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
%>
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
                <h1 class="text-xl font-semibold text-gray-800">CLIENTES</h1>
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

					<li class="flex space-x-10 mx-auto">
					<a href="<%=request.getContextPath()%>/ServletListarClientes?openListar=1&pagina=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Listado</a>
						<a href="<%=request.getContextPath()%>/ServletAgregarCliente?openAgregarUsu=1"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>
						<a href="<%=request.getContextPath()%>/ServletModificarCliente?openModificar=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>
						<a href="<%=request.getContextPath()%>/ServletBorrarCliente?openBorrar=1"
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Borrar</a>
					</li>

					<li></li>
				</ul>
			</nav>

            <div class="p-6 flex-1 flex flex-col justify-center items-center">
                <div class="bg-white p-8 rounded-lg shadow-md w-full max-w-4xl">
                <% if (request.getAttribute("mensaje") != null) { %>
    <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4 text-center font-semibold">
        <%= request.getAttribute("mensaje") %>
    </div>
<% } %>
                    <form action="/BancoParcial/ServletAgregarCliente" method="post" onsubmit="return validarContrasena()" class="grid grid-cols-1 md:grid-cols-3 gap-x-8 gap-y-6">
                    
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
    <label for="cuil" class="block text-gray-700 text-lg font-semibold mb-2">CUIL</label>
    <input
        type="text"
        id="cuil"
        name="cuil"
        placeholder=""
        class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
        required
    >
</div>

                        <div id="grupoProvincia" class="hidden">
  <label for="provincia" class="block text-gray-700 text-lg font-semibold mb-2">Provincia</label>
  <select id="provincia" name="provincia"
         class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white text-black">
    <option value="">Seleccione provincia</option>
  </select>
</div>
<div id="grupoProvinciaManual" class="hidden">
  <label for="provincia_manual" class="block text-gray-700 text-lg font-semibold mb-2">Provincia</label>
  <input type="text" id="provincia_manual" name="provincia"
         class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg" />
</div>

<!-- LOCALIDAD -->
<div id="grupoLocalidad" class="hidden">
  <label for="localidad" class="block text-gray-700 text-lg font-semibold mb-2">Localidad</label>
  <select id="localidad" name="localidad"
          class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg bg-white">
    <option value="">Seleccione localidad</option>
  </select>
</div>
<div id="grupoLocalidadManual" class="hidden">
  <label for="localidad_manual" class="block text-gray-700 text-lg font-semibold mb-2">Localidad</label>
  <input type="text" id="localidad_manual" name="localidad"
         class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg" />
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
  <label class="block text-gray-700 text-lg font-semibold mb-2">Teléfonos (máx 3)</label>
  <div id="telefonosContainer">
    <div class="flex items-center space-x-2 mb-2" id="telefono_group_1">
      <input
        type="text"
        id="telefono_1"
        name="telefonos"
        placeholder="Teléfono 1"
        class="p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
        oninput="mostrarSiguiente(1)"
        required
      >
    </div>
  </div>
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
                                <option value="Masculino">Masculino</option>
                                <option value="Femenino">Femenino</option>
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
                        <div>
    <label for="confirmar_contrasena" class="block text-gray-700 text-lg font-semibold mb-2">Confirmar Contraseña</label>
    <input
        type="password"
        id="confirmar_contrasena"
        name="confirmar_contrasena"
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

    <script>
    function validarContrasena() {
        var contrasena = document.getElementById("contrasena").value;
        var confirmar = document.getElementById("confirmar_contrasena").value;

        if (contrasena !== confirmar) {
            alert("Las contraseñas no coinciden.");
            return false; // evita el submit
        }
        return true; // permite el submit
    }
    function mostrarSiguiente(n) {
    		  const actual = document.getElementById('telefono_' + n);
    		  const contenedor = document.getElementById('telefonosContainer');

    		  // Si el campo está lleno y el siguiente no existe aún, y hay menos de 3
    		  if (actual.value.trim() !== "" && !document.getElementById('telefono_' + (n + 1)) && n < 3) {
    		    const div = document.createElement('div');
    		    div.className = 'flex items-center space-x-2 mb-2';
    		    div.id = 'telefono_group_' + (n + 1);

    		    const input = document.createElement('input');
    		    input.type = 'text';
    		    input.id = 'telefono_' + (n + 1);
    		    input.name = 'telefonos';
    		    input.placeholder = 'Teléfono ' + (n + 1);
    		    input.className = 'p-3 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg';
    		    input.oninput = function () {
    		      mostrarSiguiente(n + 1);
    		    };

    		    const btn = document.createElement('button');
    		    btn.type = 'button';
    		    btn.textContent = '−';
    		    btn.className = 'text-red-600 font-bold text-xl hover:text-red-800';
    		    btn.onclick = function () {
    		      // Eliminar este y todos los posteriores
    		      for (let i = n + 1; i <= 3; i++) {
    		        const grupoPosterior = document.getElementById('telefono_group_' + i);
    		        if (grupoPosterior) {
    		          contenedor.removeChild(grupoPosterior);
    		        }
    		      }
    		      contenedor.removeChild(div); // eliminar el actual también
    		    };

    		    div.appendChild(input);
    		    div.appendChild(btn);
    		    contenedor.appendChild(div);
    		  }
    		}
    document.addEventListener("DOMContentLoaded", function () {
        const nacionalidad = document.getElementById("nacionalidad");
        const grupoProvincia = document.getElementById("grupoProvincia");
        const grupoProvinciaManual = document.getElementById("grupoProvinciaManual");
        const grupoLocalidad = document.getElementById("grupoLocalidad");
        const grupoLocalidadManual = document.getElementById("grupoLocalidadManual");

        const provinciaSelect = document.getElementById("provincia");
        const localidadSelect = document.getElementById("localidad");

        nacionalidad.addEventListener("input", function () {
            if (nacionalidad.value.toLowerCase() === "argentina") {
                grupoProvincia.classList.remove("hidden");
                grupoLocalidad.classList.remove("hidden");
                grupoProvinciaManual.classList.add("hidden");
                grupoLocalidadManual.classList.add("hidden");
                cargarProvincias();
            } else {
                grupoProvincia.classList.add("hidden");
                grupoLocalidad.classList.add("hidden");
                grupoProvinciaManual.classList.remove("hidden");
                grupoLocalidadManual.classList.remove("hidden");
            }
        });

        provinciaSelect.addEventListener("change", function () {
            const idProvincia = provinciaSelect.value;
            cargarLocalidades(idProvincia);
        });

        function cargarProvincias() {
        	fetch("<%=request.getContextPath()%>/ServletAgregarCliente?listarProvincias=1")
                .then(response => response.json())
                .then(data => {
                	console.log("Provincias recibidas:", data);
                    provinciaSelect.innerHTML = '<option value="">Seleccione provincia</option>';
                    data.forEach(p => {
                    	const option = document.createElement("option");
                    	option.value = p.idProvincia;
                    	option.textContent = p.nombre;
                    	provinciaSelect.appendChild(option);
                    });
                })
                .catch(err => console.error("Error cargando provincias:", err));
        }
        const contextPath = "<%= request.getContextPath() %>";
        function cargarLocalidades(idProvincia) {
            if (!idProvincia) return;
            const url = contextPath + "/ServletAgregarCliente?listarLocalidades=1&idProvincia=" + idProvincia;
            fetch(url)
                .then(response => response.json())
                .then(data => {
                    localidadSelect.innerHTML = '<option value="">Seleccione localidad</option>';
                    data.forEach(l => {
                    	const option = document.createElement("option");
                    	option.value = l.idLocalidad;
                    	option.textContent = l.nombre;
                    	localidadSelect.appendChild(option);
                    });
                })
                .catch(err => console.error("Error cargando localidades:", err));
        }
    });
</script>
</body>
</html>