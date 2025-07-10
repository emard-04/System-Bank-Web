package Presentacion;

import java.io.IOException;
import Entidades.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Interfaces.inTelefono;
import negocio.ClientesNeg;
import negocio.PaisNeg;
import negocio.ProvinciaNeg;
import negocio.TelefonoNeg;
import negocio.UsuarioNeg;
import negocioImpl.LocalidadNegImpl;
import negocioImpl.PaisNegImpl;
import negocioImpl.PersonaNegImpl;
import negocioImpl.ProvinciaNegImpl;
import negocioImpl.TelefonoNegImpl;
import negocioImpl.UsuarioNegImpl;
import java.util.List;

/**
 * Servlet implementation class ServletModificarCliente
 */
@WebServlet("/ServletModificarCliente")
public class ServletModificarCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UsuarioNeg negUsuario= new UsuarioNegImpl();
	private static ClientesNeg negCliente= new PersonaNegImpl();
    private static TelefonoNeg negTelefono=new TelefonoNegImpl();
    private static final ProvinciaNegImpl ProvinciaNeg = new ProvinciaNegImpl();
	private static final LocalidadNegImpl localidadNeg = new LocalidadNegImpl();
	private static final PaisNeg nPais= new PaisNegImpl();
    public ServletModificarCliente() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("openModificar") != null) {

	        String ventana = "AdminMode/clienteAdmin_modificar.jsp";
	        windowdefault(request, response, ventana);
	        return; 
	    
		}
		if(request.getParameter("dniCliente")!=null) {
			String dniCliente=request.getParameter("dniCliente");
			Usuario us= new Usuario();
			TelefonoxPersona  telefono=new TelefonoxPersona();
			us=negUsuario.BuscarDni(dniCliente);
			ArrayList<TelefonoxPersona>Lista=negTelefono.listarTelefonos(dniCliente);
			 StringBuilder telefonosJson = new StringBuilder("[");
			    for (int i = 0; i < Lista.size(); i++) {
			        telefono = Lista.get(i);
			        telefonosJson.append("{\"numero\": \"").append(telefono.getTelefono()).append("\"}");
			        if (i < Lista.size() - 1) {
			            telefonosJson.append(",");
			        }
			    }
			    telefonosJson.append("]");
			    
			 String json = "{"
					 
					 + "\"dni\": \"" + us.getPersona().getDni() + "\","
					    + "\"cuil\": \"" + us.getPersona().getCuil() + "\","
					    + "\"nombre\": \"" + us.getPersona().getNombre() + "\","
					    + "\"apellido\": \"" + us.getPersona().getApellido() + "\","
					    + "\"localidad_id\": " + us.getPersona().getLocalidad().getIdLocalidad() + ","
					    + "\"localidad_nombre\": \"" + us.getPersona().getLocalidad().getNombre() + "\","
					    + "\"provincia_id\": " + us.getPersona().getProvincia().getIdProvincia() + ","
					    + "\"provincia_nombre\": \"" + us.getPersona().getProvincia().getNombre() + "\","
					    + "\"direccion\": \"" + us.getPersona().getDireccion() + "\","
					    + "\"pais_Id\": \"" + us.getPersona().getPais().getIdPais() + "\","
					    + "\"pais_nombre\": \"" + us.getPersona().getPais().getNombre() + "\","
					    + "\"fechaNacimiento\": \"" + us.getPersona().getFechaNacimiento() + "\","
					    + "\"correoElectronico\": \"" + us.getPersona().getCorreoElectronico() + "\","
					    + "\"sexo\": \"" + us.getPersona().getSexo() + "\","
					    + "\"usuario\": \"" + us.getNombreUsuario() + "\","
					    + "\"contrasena\": \"" + us.getContrasena() + "\","
					    + "\"telefonos\": " + telefonosJson.toString()
                     + "}";
			 response.setContentType("application/json");
             response.setCharacterEncoding("UTF-8");
             response.getWriter().write(json);
		}
		if (request.getParameter("listarProvincias") != null) {
	    	int idPais = Integer.parseInt(request.getParameter("idPais").trim());
	    	List<Provincia> provincia = ProvinciaNeg.listarProvinciasxPais(idPais);

	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");

	        StringBuilder json = new StringBuilder();
	        json.append("[");
	        for (int i = 0; i < provincia.size(); i++) {
	            Provincia prov = provincia.get(i);
	            json.append("{");
	            json.append("\"idProvincia\":").append(prov.getIdProvincia()).append(",");
	            json.append("\"nombre\":\"").append(prov.getNombre().replace("\"", "\\\"")).append("\"");
	            json.append("}");
	            if (i < provincia.size() - 1) {
	                json.append(",");
	            }
	        }
	        json.append("]");

	        response.getWriter().write(json.toString());
	        return;
	    }
		if (request.getParameter("listarLocalidades") != null) {
	    	int idProvincia = Integer.parseInt(request.getParameter("idProvincia").trim());
	    	List<Localidad> localidades = localidadNeg.listarLocalidadesPorProvincia(idProvincia);

	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");

	        StringBuilder json = new StringBuilder();
	        json.append("[");
	        for (int i = 0; i < localidades.size(); i++) {
	            Localidad loc = localidades.get(i);
	            json.append("{");
	            json.append("\"idLocalidad\":").append(loc.getIdLocalidad()).append(",");
	            json.append("\"nombre\":\"").append(loc.getNombre().replace("\"", "\\\"")).append("\"");
	            json.append("}");
	            if (i < localidades.size() - 1) {
	                json.append(",");
	            }
	        }
	        json.append("]");

	        response.getWriter().write(json.toString());
	        return;
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idProvincia = request.getParameter("provincia_mod");
		String idLocalidad = request.getParameter("localidad_mod");
		Usuario usuario=new Usuario();
		Persona persona= new Persona();
		persona.setDni(request.getParameter("dni_mod"));
		persona.setCuil(request.getParameter("cuil_mod"));
		persona.setNombre(request.getParameter("nombre_mod"));
		persona.setApellido(request.getParameter("apellido_mod"));
		
		Pais pais= new Pais();
		pais.setIdPais(Integer.parseInt(request.getParameter("Nacionalidad_mod")));
		Provincia provincia = new Provincia();
		provincia.setIdProvincia(Integer.parseInt(idProvincia));

		Localidad localidad = new Localidad();
		localidad.setIdLocalidad(Integer.parseInt(idLocalidad));

		// Asignar a persona
		persona.setPais(pais);
		persona.setProvincia(provincia);
		persona.setLocalidad(localidad);
		persona.setDireccion(request.getParameter("direccion_mod"));
		//persona.setNacionalidad(request.getParameter("nacionalidad_mod"));
		persona.setCorreoElectronico(request.getParameter("correo_electronico_mod"));
		persona.setSexo(request.getParameter("sexo_mod"));
		persona.setFechaNacimiento(LocalDate.parse(request.getParameter("fecha_nacimiento_mod")));
		if(!negCliente.Modificar(persona)) {
			request.setAttribute("mensaje", "❌ Error al modificar cliente.");
			String ventana="AdminMode/clienteAdmin_modificar.jsp?mensaje=Error persona";
			windowdefault(request, response, ventana);
			return;
		}
		String accion=request.getParameter("Accion");
		if(!accion.trim().isEmpty()) {
		if(!actualizarTelefonos(request, response, persona)) {
			request.setAttribute("mensaje", "❌ Error al modificar telefono.");
			String ventana="AdminMode/clienteAdmin_modificar.jsp?mensaje=Error telefono";
			windowdefault(request, response, ventana);
			return;
		}}
		usuario.setPersona(persona);
		usuario.setNombreUsuario(request.getParameter("usuario_mod"));
		usuario.setContrasena(request.getParameter("contrasena_mod"));
		if(!negUsuario.Modificar(usuario)) {
			request.setAttribute("mensaje", "❌ Error al modificar cliente.");
		String ventana="AdminMode/clienteAdmin_modificar.jsp?mensaje=Error usuario";
		windowdefault(request, response, ventana);
		return;
		}
		request.setAttribute("mensaje", "✅ Cliente modificado correctamente.");
		String ventana="AdminMode/clienteAdmin_modificar.jsp";
		//request.setAttribute("listaProvincias", ProvinciaNeg.listarProvinciasxPais(/* algún idPais por defecto o null */));
		windowdefault(request,response, ventana);
		//doGet(request, response);
	}
	private void windowdefault(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException {
		request.setAttribute("listaPais", nPais.listarTodo());
		request.setAttribute("ListaUsuario", negUsuario.listarTodo());
		RequestDispatcher rd= request.getRequestDispatcher(jsp);
		rd.forward(request, response);
	}

	private boolean actualizarTelefonos(HttpServletRequest request, HttpServletResponse response, Persona persona)
			throws ServletException, IOException {
		if (request.getParameter("Accion").equals("Editar")) {
			TelefonoxPersona tf = new TelefonoxPersona();
			tf.setDni(persona);
			tf.setTelefono(request.getParameter("telefono_input"));
			String oldTel = request.getParameter("oldTelefono");
			if (negTelefono.Modificar(oldTel, tf)) {
				return true;
			} else {
				return false;
			}
		}
		if (request.getParameter("Accion").equals("Agregar")) {
			TelefonoxPersona tf = new TelefonoxPersona();
			tf.setDni(persona);
			tf.setTelefono(request.getParameter("telefono_input"));
			if (negTelefono.Agregar(tf)) {
				return true;
			} else {
				return false;
			}
		}
		if (request.getParameter("Accion").equals("Eliminar")) {
			TelefonoxPersona tf = new TelefonoxPersona();
			tf.setDni(persona);
			tf.setTelefono(request.getParameter("telefono_input"));
			if (negTelefono.Eliminar(tf)) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}
}
