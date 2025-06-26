package Presentacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Daos.daoPersonas;
import Entidades.*;
import negocio.*;
import negocioImpl.*;

/**
 * Servlet implementation class ServletAgregarCliente
 */
@WebServlet("/ServletAgregarCliente")
public class ServletAgregarCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final PersonaNegImpl negPersona= new PersonaNegImpl(); 
	private static final TelefonoNegImpl negTelefono = new TelefonoNegImpl();
	private static final UsuarioNeg negUsuario = new UsuarioNegImpl();
    public ServletAgregarCliente() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("openAgregarUsu")!=null) {
			String ventana="AdminMode/clientesAdmin_agregar.jsp";
			windowDefault(request,response, ventana);//Cargar ventana agregar cuenta
			}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        // 1. Obtener datos del formulario
	        String nombre = request.getParameter("nombre");
	        String apellido = request.getParameter("apellido");
	        String dni = request.getParameter("dni");
	        String cuil = request.getParameter("cuil");
	        String localidad = request.getParameter("localidad");
	        String provincia = request.getParameter("provincia");
	        String direccion = request.getParameter("direccion");
	        String nacionalidad = request.getParameter("nacionalidad");
	        LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("fecha_nacimiento"));
	        String correo = request.getParameter("correo_electronico");
	        String sexo = request.getParameter("sexo");
	        String telefono = request.getParameter("telefono");

	        String nombreUsuario = request.getParameter("usuario");
	        String contrasena = request.getParameter("contrasena");

	        // 2. Crear objeto Persona
	        Persona persona = new Persona();
	        persona.setDni(dni);
	        persona.setCuil(cuil);
	        persona.setNombre(nombre);
	        persona.setApellido(apellido);
	        persona.setLocalidad(localidad);
	        persona.setProvincia(provincia);
	        persona.setDireccion(direccion);
	        persona.setNacionalidad(nacionalidad);
	        persona.setFechaNacimiento(fechaNacimiento);
	        persona.setCorreoElectronico(correo);
	        persona.setSexo(sexo);
	        persona.setEstado(true);

	        // 3. Insertar persona
	        boolean personaCreada = negPersona.Agregar(persona);

	        // 4. Crear e insertar usuario
	        Usuario usuario = new Usuario();
	        usuario.setNombreUsuario(nombreUsuario);
	        usuario.setContrasena(contrasena);
	        usuario.setPersona(persona);
	        usuario.setTipoUsuario(false); // asumimos que es cliente

	        boolean usuarioCreado = negUsuario.AgregarUsuario(usuario);

	        // 5. Insertar teléfono
	        TelefonoxPersona telPersona = new TelefonoxPersona();
	        telPersona.setDni(persona);
	        telPersona.setTelefono(telefono);

	        boolean telefonoGuardado = negTelefono.Agregar(telPersona);

	        // 6. Redirigir según resultado
	        if (personaCreada && usuarioCreado && telefonoGuardado) {
	            windowDefault(request, response, "AdminMode/clientesAdmin_agregar.jsp?exito=1");
	        } else {
	            windowDefault(request, response, "AdminMode/clientesAdmin_agregar.jsp?error=No se pudo registrar");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        windowDefault(request, response, "AdminMode/clientesAdmin_agregar.jsp?error=Excepcion");
	    }
	}
	private void windowDefault(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException{
		 
		 RequestDispatcher rd= request.getRequestDispatcher(jsp);
		 rd.forward(request, response);
	}
}


