package Presentacion;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Daos.daoTelefono;
import Entidades.Cuenta;
import Entidades.Persona;
import Entidades.TelefonoxPersona;
import Entidades.Usuario;
import Interfaces.inTelefono;
import negocio.ClientesNeg;
import negocio.TelefonoNeg;
import negocio.UsuarioNeg;
import negocioImpl.PersonaNegImpl;
import negocioImpl.TelefonoNegImpl;
import negocioImpl.UsuarioNegImpl;

/**
 * Servlet implementation class ServletModificarCliente
 */
@WebServlet("/ServletModificarCliente")
public class ServletModificarCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UsuarioNeg negUsuario= new UsuarioNegImpl();
	private static ClientesNeg negCliente= new PersonaNegImpl();
    private static TelefonoNeg negTelefono=new TelefonoNegImpl();
    public ServletModificarCliente() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("openModificar")!=null) {
			String ventana="AdminMode/clienteAdmin_modificar.jsp";
			windowdefault(request,response, ventana);
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
					    + "\"localidad\": \"" + us.getPersona().getLocalidad() + "\","
					    + "\"provincia\": \"" + us.getPersona().getProvincia() + "\","
					    + "\"direccion\": \"" + us.getPersona().getDireccion() + "\","
					    + "\"nacionalidad\": \"" + us.getPersona().getNacionalidad() + "\","
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario=new Usuario();
		Persona persona= new Persona();
		persona.setDni(request.getParameter("dni_mod"));
		persona.setCuil(request.getParameter("cuil_mod"));
		persona.setNombre(request.getParameter("nombre_mod"));
		persona.setApellido(request.getParameter("apellido_mod"));
		persona.setLocalidad(request.getParameter("localidad_mod"));
		persona.setProvincia(request.getParameter("provincia_mod"));
		persona.setDireccion(request.getParameter("direccion_mod"));
		persona.setNacionalidad(request.getParameter("nacionalidad_mod"));
		persona.setCorreoElectronico(request.getParameter("correo_electronico_mod"));
		persona.setSexo(request.getParameter("sexo_mod"));
		persona.setFechaNacimiento(LocalDate.parse(request.getParameter("fecha_nacimiento_mod")));
		if(!negCliente.Modificar(persona)) {
			String ventana="AdminMode/clienteAdmin_modificar.jsp?mensaje=Error persona";
			windowdefault(request, response, ventana);
			return;
		}
		String accion=request.getParameter("Accion");
		if(!accion.trim().isEmpty()) {
		if(!actualizarTelefonos(request, response, persona)) {
			String ventana="AdminMode/clienteAdmin_modificar.jsp?mensaje=Error telefono";
			windowdefault(request, response, ventana);
			return;
		}}
		usuario.setPersona(persona);
		usuario.setNombreUsuario(request.getParameter("usuario_mod"));
		usuario.setContrasena(request.getParameter("contrasena_mod"));
		if(!negUsuario.Modificar(usuario)) {
		String ventana="AdminMode/clienteAdmin_modificar.jsp?mensaje=Error usuario";
		windowdefault(request, response, ventana);
		return;
		}
		System.out.println("todo ok");
		String ventana="AdminMode/clienteAdmin_modificar.jsp";
		windowdefault(request,response, ventana);
		doGet(request, response);
	}
	private void windowdefault(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException {
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
