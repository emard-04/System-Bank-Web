package negocioImpl;
import Daos.daoPersonas;
import Daos.daoUsuario;
import Interfaces.InUsuario;
import Interfaces.inPersona;
import negocio.*;
import Entidades.*;
import java.util.ArrayList;
public class PersonaNegImpl implements ClientesNeg{
	
	private inPersona daoP = new daoPersonas();
	private InUsuario daoU = new daoUsuario();

	@Override
	public boolean Agregar(Persona persona) {
		// Primero verificamos si ya existe el DNI
		if (daoP.existe(persona.getDni())) {
			return false;
		}
		return daoP.Agregar(persona);
	}
	public boolean AgregarConPersona(Usuario usuario) {
	    Persona persona = usuario.getPersona();

	    // Validar si ya existe el DNI
	    if (daoP.existe(persona.getDni())) {
	        return false;
	    }

	    // Primero agregar el Usuario y obtener el ID
	    boolean okUsuario = daoU.Agregar(usuario);
	    if (!okUsuario) {
	        return false;
	    }

	    // Obtener ID generado
	    int idGenerado = daoU.obtenerIdUsuarioPorNombre(usuario.getNombreUsuario());
	    persona.setIdUsuario(idGenerado);

	    // Luego agregar la persona con la FK del usuario
	    return daoP.Agregar(persona);
	}

	@Override
	public boolean Modificar(Persona persona) {
		return daoP.Modificar(persona);
	}

	@Override
	public boolean Eliminar(String dni) {
		return daoP.Eliminar(dni);
	}

	@Override
	public ArrayList<Persona> ListarTodo() {
		return daoP.ListarTodo();
	}

	@Override
	public boolean existe(String dni) {
		return daoP.existe(dni);
	}

	@Override
	public Persona Buscardni(String dni) {
		return daoP.Buscardni(dni);
	}
}


