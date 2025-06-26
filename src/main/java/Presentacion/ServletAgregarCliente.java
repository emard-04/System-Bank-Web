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
import Daos.daoPersonas;
import Entidades.*;
import negocioImpl.PersonaNegImpl;
import negocio.*;
import negocioImpl.*;

/**
 * Servlet implementation class ServletAgregarCliente
 */
@WebServlet("/ServletAgregarCliente")
public class ServletAgregarCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final PersonaNegImpl personaNeg = new PersonaNegImpl();
	private static final UsuarioNeg usuarioNeg = new UsuarioNegImpl();
       
  
    public ServletAgregarCliente() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("entro al post");
		String dni = request.getParameter("dni");
		String cuil = "202020"; // Podés capturarlo si lo agregás al front
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String sexo = request.getParameter("sexo");
		String nacionalidad = request.getParameter("nacionalidad");
		LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("fecha_nacimiento"));
		String direccion = request.getParameter("direccion");
		String localidad = request.getParameter("localidad");
		String provincia = request.getParameter("provincia");
		String correo = request.getParameter("correo_electronico");

		// Datos de Usuario
		String nombreUsuario = request.getParameter("usuario");
		String contrasena = request.getParameter("contrasena");
		// Validar que el DNI no exista
		
		boolean existe = personaNeg.existe(dni);
		if (existe) {
		    response.sendRedirect("/AdminMode/clientesAdmin_agregar.jsp?error=DNI ya registrado");
		    return;
		}

		

		// Crear objeto Usuario
		Usuario usuario = new Usuario();
		usuario.setNombreUsuario(nombreUsuario);
		usuario.setContrasena(contrasena);
		usuario.setTipoUsuario(false); // Suponiendo que false = cliente

		// Crear objeto Persona
		Persona persona = new Persona();
		persona.setDni(dni);
		persona.setCuil(cuil);
		persona.setNombre(nombre);
		persona.setApellido(apellido);
		persona.setSexo(sexo);
		persona.setNacionalidad(nacionalidad);
		persona.setFechaNacimiento(fechaNacimiento);
		persona.setDireccion(direccion);
		persona.setLocalidad(localidad);
		persona.setProvincia(provincia);
		persona.setCorreoElectronico(correo);

		usuario.setPersona(persona); // Asignar persona al usuario

		boolean exito = usuarioNeg.AgregarUsuario(usuario); // método especial

		if (exito) {
			response.sendRedirect("AdminMode/clientesAdmin.jsp?msg=Cliente agregado correctamente");
		} else {
			response.sendRedirect("AdminMode/clientesAdmin_agregar.jsp?error=No se pudo agregar el cliente");
		}
	}
}


