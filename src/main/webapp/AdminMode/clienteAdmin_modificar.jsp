<%@page import="java.util.ArrayList"%>
<%@ page import="Entidades.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
%>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
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
<script>
function confirmarLogout(e) {
    e.preventDefault(); // Detiene la acción por defecto del enlace

    if (confirm("¿Estás seguro de que quieres cerrar sesión?")) {
        // Si el usuario confirma, redirige al servlet de logout
        window.location.href = "<%=request.getContextPath()%>/ServletLogout";
    }
}
</script>
<body class="bg-gray-100 h-screen overflow-hidden" data-context-path="<%= request.getContextPath() %>">
	<div class="flex h-full">

		<aside
			class="bg-white w-64 flex-shrink-0 p-4 border-r border-gray-200 flex flex-col items-center">

			 <img src="<%=request.getContextPath()%>/img/perfilAdmi.webp"
     alt="Foto de perfil"
     class="w-32 h-32 rounded-full object-cover mb-4 border-4 border-gray-300 shadow-md">

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

			<header
				class="bg-white p-4 border-b border-gray-200 flex justify-between items-center">
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
						class="hover:bg-blue-600 hover:text-white text-gray-700 font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Agregar</a>
						<a href="/BancoParcial/ServletModificarCliente?openModificar=1"
						class="bg-blue-600 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:shadow-outline transition duration-200 ease-in-out">Modificar</a>
						<a href="<%=request.getContextPath()%>/ServletBorrarCliente?openBorrar=1"
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
							<%
							if (request.getAttribute("ListaUsuario") != null) {
								ArrayList<Usuario> lista = (ArrayList<Usuario>) request.getAttribute("ListaUsuario");
								for (Usuario u : lista) {
							%>
							<option value=<%=u.getPersona().getDni()%>><%=u.getPersona().getDni()%></option>
							<%
							}
							}
							%>
						</select>
					</div>
<% if (request.getAttribute("mensaje") != null) { %>
    <div class="bg-green-100 text-green-700 p-4 rounded-md mb-4 text-center font-semibold">
        <%= request.getAttribute("mensaje") %>
    </div>
<% } %>
					<form action="<%=request.getContextPath()%>/ServletModificarCliente" method="post"
						class="grid grid-cols-1 md:grid-cols-3 gap-x-8 gap-y-6">

						<!-- Fila 1 -->
						<div>
							<label for="dni_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">DNI</label>
							<input type="text" id="dni_mod" name="dni_mod"
								class="p-3 border border-gray-300 rounded-md w-full text-lg read-only-input"
								readonly>
						</div>
						<div>
							<label for="cuil_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">CUIL</label>
							<input type="text" id="cuil_mod" name="cuil_mod" pattern="[0-9\-]+" title="Solo se permiten números y guiones"
								class="p-3 border border-gray-300 rounded-md w-full text-lg"
								required>
						</div>
						<div>
							<label for="nombre_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Nombre</label>
							<input type="text" id="nombre_mod" name="nombre_mod" pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" title="Solo se permiten letras y espacios"
  oninput="this.value = this.value.replace(/[^A-Za-zÁÉÍÓÚáéíóúÑñ\s]/g, '')"
								class="p-3 border border-gray-300 rounded-md w-full text-lg"
								required>
						</div>

						<!-- Fila 2 -->
						<div>
							<label for="apellido_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Apellido</label>
							<input type="text" id="apellido_mod" name="apellido_mod"
								class="p-3 border border-gray-300 rounded-md w-full text-lg"
								required>
						</div>
												<div>
    <label for="Nacionalidad_mod" class="block text-gray-700 text-lg font-semibold mb-2">Pais</label>
    <select id="Nacionalidad_mod" name="Nacionalidad_mod" 
            class="p-3 border border-gray-300 rounded-md w-full text-lg" required>
        <option value="">Seleccione un pais</option>
        <%-- Aquí llenás las opciones dinámicamente --%>
        <% 
            // Supongamos que pasaste la lista de provincias en el request como "listaProvincias"
            ArrayList<Pais> listaPais = (ArrayList<Pais>) request.getAttribute("listaPais");
            if (listaPais != null) {
                for (Pais p : listaPais) {
        %>
                    <option value="<%= p.getIdPais() %>"><%= p.getNombre() %></option>
        <%      }
            }
        %>
    </select>
</div>
						<div>
    <label for="provincia_mod" class="block text-gray-700 text-lg font-semibold mb-2">Provincia</label>
    <select id="provincia_mod" name="provincia_mod" 
            class="p-3 border border-gray-300 rounded-md w-full text-lg" required>
        <option value="">Seleccione una provincia</option>
        <%-- Aquí llenás las opciones dinámicamente --%>
        <% 
            // Supongamos que pasaste la lista de provincias en el request como "listaProvincias"
            ArrayList<Provincia> listaProvincias = (ArrayList<Provincia>) request.getAttribute("listaProvincias");
            if (listaProvincias != null) {
                for (Provincia p : listaProvincias) {
        %>
                    <option value="<%= p.getIdProvincia() %>"><%= p.getNombre() %></option>
        <%      }
            }
        %>
    </select>
</div>

<div>
    <label for="localidad_mod" class="block text-gray-700 text-lg font-semibold mb-2">Localidad</label>
    <select id="localidad_mod" name="localidad_mod" 
            class="p-3 border border-gray-300 rounded-md w-full text-lg" required>
        <option value="">Seleccione una localidad</option>
        <%-- Inicialmente puede estar vacío o cargar todas --%>
    </select>
</div>
						<!-- Fila 3 -->
						<div>
							<label for="direccion_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Dirección</label>
							<input type="text" id="direccion_mod" name="direccion_mod"
								class="p-3 border border-gray-300 rounded-md w-full text-lg"
								required>
						</div>
						<div>
							<label for="fecha_nacimiento_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Fec
								Nacimiento</label> <input type="date" id="fecha_nacimiento_mod"
								name="fecha_nacimiento_mod"
								class="p-3 border border-gray-300 rounded-md w-full text-lg bg-white"
								required>
						</div>

						<!-- Fila 4 -->
						<div>
							<label for="sexo_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Sexo</label>
							<select id="sexo_mod" name="sexo_mod"
								class="p-3 border border-gray-300 rounded-md w-full text-lg bg-white"
								required>
								<option value="">Seleccione</option>
								<option value="Masculino">Masculino</option>
								<option value="Femenino">Femenino</option>
								<option value="Otro">Otro</option>
							</select>
						</div>
						<div>
							<label for="correo_electronico_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Correo
								electrónico</label> <input type="email" id="correo_electronico_mod"
								name="correo_electronico_mod"
								class="p-3 border border-gray-300 rounded-md w-full text-lg"
								required>
						</div>
						<div>
							<label for="usuario_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Usuario</label>
							<input type="text" id="usuario_mod" name="usuario_mod"
								class="p-3 border border-gray-300 rounded-md w-full text-lg"
								required>
						</div>

						<!-- Fila 5 -->
						<div class="md:col-span-1">
							<label for="contrasena_mod"
								class="block text-gray-700 text-lg font-semibold mb-2">Contraseña</label>
							<input type="password" id="contrasena_mod" name="contrasena_mod"
								placeholder="Dejar en blanco para no cambiar"
								class="p-3 border border-gray-300 rounded-md w-full text-lg">
						</div>

						<!-- Teléfonos -->
						<div id="telefonosContainer" class="md:col-span-2">
							<label for="telefono_select"
								class="block text-gray-700 text-lg font-semibold mb-2">Teléfonos
								(máx 3)</label> <select id="telefono_select"  style="background-color:silver;" name="telefono_select"
								class="p-2 border border-gray-300 rounded-md w-full mb-2 bg-white" disabled="true">
								<option value="">-- Seleccione un teléfono --</option>
							</select> 
							<input type="hidden" id="oldTelefono" name="oldTelefono">
							<input type="hidden" id="Accion" name="Accion" value="">
							<div class="flex space-x-2">
								<input type="text" id="telefono_input" pattern="[0-9]+" title="Solo se permiten números" style="background-color:silver;" name="telefono_input" readonly
									placeholder="Ej: 1123456789"
									class="p-2 border border-gray-300 rounded-md flex-grow focus:outline-none focus:ring-2 focus:ring-blue-500 text-base">

								<button type="button" id="btnAgregarTelefono"
									class="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-md">Agregar</button>

								<button type="button" id="btnEditarTelefono"
									class="bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-2 rounded-md">Editar</button>

								<button type="button" id="btnEliminarTelefono"
									class="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-md">Eliminar</button>
							</div>
						</div>

						<!-- Botones -->
						<div class="md:col-span-3 flex justify-center space-x-6 pt-4">
							<button type="submit"
								class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-md text-xl">Modificar</button>
							<button type="button"  id="btnCancelar"
								class="bg-gray-500 hover:bg-gray-600 text-white font-bold py-3 px-6 rounded-md text-xl">Cancelar</button>
						</div>

					</form>
				</div>
			</div>

			<footer
				class="bg-gray-200 p-4 text-center text-gray-600 border-t border-gray-200 flex-shrink-0">
				GRUPO_N7 </footer>
		</main>

	</div>

</body>
<script>
    // Estas funciones ahora están afuera para que puedan ser usadas globalmente
    function cargarProvincias(idPais) {
        const provinciaSelect = document.getElementById("provincia_mod");
        const localidadSelect = document.getElementById("localidad_mod");
        const contextPath = document.body.getAttribute("data-context-path");

        return fetch(contextPath + "/ServletModificarCliente?listarProvincias=1&idPais=" + idPais)
            .then(response => response.json())
            .then(data => {
                provinciaSelect.innerHTML = '<option value="">Seleccione provincia</option>';
                localidadSelect.innerHTML = '<option value="">Seleccione localidad</option>';
                data.forEach(p => {
                    const option = document.createElement("option");
                    option.value = p.idProvincia;
                    option.textContent = p.nombre;
                    provinciaSelect.appendChild(option);
                });
            })
            .catch(err => console.error("Error cargando provincias:", err));
    }

    function cargarLocalidades(idProvincia) {
        const localidadSelect = document.getElementById("localidad_mod");
        const contextPath = document.body.getAttribute("data-context-path");

        return fetch(contextPath + "/ServletModificarCliente?listarLocalidades=1&idProvincia=" + idProvincia)
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

    // Esperamos que el DOM esté listo
    document.addEventListener("DOMContentLoaded", function () {
        const nacionalidad = document.getElementById("Nacionalidad_mod");
        const provinciaSelect = document.getElementById("provincia_mod");
        const localidadSelect = document.getElementById("localidad_mod");

        // Cargar provincias cuando cambia el país
        nacionalidad.addEventListener("change", function () {
            const idPais = nacionalidad.value;
            if (idPais) {
                cargarProvincias(idPais);
            } else {
                provinciaSelect.innerHTML = '<option value="">Seleccione provincia</option>';
                localidadSelect.innerHTML = '<option value="">Seleccione localidad</option>';
            }
        });

        // Cargar localidades cuando cambia la provincia
        provinciaSelect.addEventListener("change", function () {
            const idProvincia = provinciaSelect.value;
            if (idProvincia) {
                cargarLocalidades(idProvincia);
            } else {
                localidadSelect.innerHTML = '<option value="">Seleccione localidad</option>';
            }
        });
        $(document).ready(function() {
            $('#seleccionar_cliente_dni').select2({
              placeholder: "Seleccione un DNI",
              allowClear: true,
              width: '50%' // Se adapta al ancho del contenedor
            });
          });
        // Cuando se selecciona un cliente por DNI
        $('#seleccionar_cliente_dni').on('change', function ()  {
        var dniCliente = this.value;
        if (!dniCliente) {
            // limpiar campos si se deselecciona
            limpiarCampos();
            return;
        }

        fetch('/BancoParcial/ServletModificarCliente?dniCliente=' + dniCliente)
            .then(response => {
                if (!response.ok) throw new Error('Usuario no encontrado');
                return response.json();
                })
                .then(data => {
                    document.getElementById('dni_mod').value = data.dni;
                    document.getElementById('cuil_mod').value = data.cuil;
                    document.getElementById('nombre_mod').value = data.nombre;
                    document.getElementById('apellido_mod').value = data.apellido;
                    document.getElementById('Nacionalidad_mod').value = data.pais_Id;

                    // Esperar a que se carguen provincias antes de seleccionar la del usuario
                    cargarProvincias(data.pais_Id).then(() => {
                        document.getElementById('provincia_mod').value = data.provincia_id;

                        // Luego esperamos a que se carguen localidades
                        cargarLocalidades(data.provincia_id).then(() => {
                            document.getElementById('localidad_mod').value = data.localidad_id;
                        });
                    });

                    document.getElementById('direccion_mod').value = data.direccion;
                    document.getElementById('fecha_nacimiento_mod').value = data.fechaNacimiento;
                    document.getElementById('correo_electronico_mod').value = data.correoElectronico;
                    document.getElementById('sexo_mod').value = data.sexo;
                    document.getElementById('usuario_mod').value = data.usuario;
                    document.getElementById('contrasena_mod').value = data.contrasena;
                    let selectTelefonos = document.getElementById('telefono_select');
                    selectTelefonos.innerHTML = '<option value="">-- Seleccione un teléfono --</option>';

                    data.telefonos.forEach(tel => {
                        let option = document.createElement('option');
                        option.value = tel.numero;
                        option.textContent = tel.numero;
                        selectTelefonos.appendChild(option);
                    });
                })
                .catch(error => {
                    alert(error.message);
                    limpiarCampos();
                });
        });
        document.getElementById('btnCancelar').addEventListener('click', function () {
            location.reload();
        });
        document.getElementById('telefono_select').addEventListener('change', function() {
        	var telefono=this.value;
        	document.getElementById('telefono_input').value=telefono;
        	document.getElementById('oldTelefono').value=telefono;
        	document.getElementById('telefono_select').disabled=true;
        });
        document.getElementById('btnAgregarTelefono').addEventListener('click', function () {
        	document.getElementById('telefono_select').value = '';
        	  document.getElementById('telefono_select').disabled = true;
        	  document.getElementById('telefono_select').style.backgroundColor= 'silver';
        	  document.getElementById('telefono_select').disabled = true;
        	  document.getElementById('telefono_input').value = '';
        	  document.getElementById('telefono_input').required = true;
        	  document.getElementById('telefono_input').readOnly = false;
        	  document.getElementById('telefono_input').style.backgroundColor = 'white';
        	  document.getElementById('Accion').value = 'Agregar';
        	});

        	document.getElementById('btnEditarTelefono').addEventListener('click', function () {
        	document.getElementById('telefono_select').value = '';
        	  document.getElementById('telefono_select').disabled = false;
        	  document.getElementById('telefono_select').style.backgroundColor= 'white';
        	  document.getElementById('telefono_select').required = true;
        	  document.getElementById('telefono_input').required = true;
        	  document.getElementById('telefono_input').value = '';
        	  document.getElementById('telefono_input').readOnly = false;
        	  document.getElementById('telefono_input').style.backgroundColor = 'white';
        	  document.getElementById('Accion').value = 'Editar';
        	});

        	document.getElementById('btnEliminarTelefono').addEventListener('click', function () {
        		document.getElementById('telefono_select').value = '';
        	  document.getElementById('telefono_select').disabled = false;
        	  document.getElementById('telefono_select').style.backgroundColor= 'white';
        	  document.getElementById('telefono_select').required = true;
        	  document.getElementById('telefono_input').value = '';
        	  document.getElementById('telefono_input').required = true;
        	  document.getElementById('telefono_input').style.backgroundColor = 'white';
        	  document.getElementById('telefono_input').readOnly = true;
        	  document.getElementById('Accion').value = 'Eliminar';
        	});

        function limpiarCampos() {
            document.getElementById('dni_mod').value = '';
            document.getElementById('cuil_mod').value = '';
            document.getElementById('nombre_mod').value = '';
            document.getElementById('apellido_mod').value = '';
            document.getElementById('localidad_mod').value = '';
            document.getElementById('provincia_mod').value = '';
            document.getElementById('direccion_mod').value = '';
            document.getElementById('Nacionalidad_mod').value = '';
            document.getElementById('fecha_nacimiento_mod').value = '';
            document.getElementById('correo_electronico_mod').value = '';
            document.getElementById('sexo_mod').value = '';
            document.getElementById('usuario_mod').value = '';
            document.getElementById('contrasena_mod').value = '';
        }
    });
</script>

</html>