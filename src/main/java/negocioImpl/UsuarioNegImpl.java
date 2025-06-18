package negocioImpl;
import negocio.*;
import Interfaces.*;
import Daos.*;
import Entidades.*;
public class UsuarioNegImpl implements UsuarioNeg {

    private InUsuario daoUsuario = new daoUsuario();
    private inPersona daoPersona = new daoPersonas();

    @Override
    public boolean existeDni(String dni) {
        return daoPersona.existe(dni);
    }

    @Override
    public boolean AgregarConPersona(Usuario usuario) {
        Persona persona = usuario.getPersona();
        
        if (daoPersona.existe(persona.getDni())) {
            return false;
        }

        // Insertar usuario
        boolean exitoUsuario = daoUsuario.Agregar(usuario);
        if (!exitoUsuario) return false;

        // Obtener el ID del usuario recién insertado
        daoUsuario.BuscarPorNombreUsuario(null);
        int idGenerado = daoUsuario.obtenerIdUsuarioPorNombre(usuario.getNombreUsuario());
        persona.setIdUsuario(idGenerado);

        // Insertar persona
        return daoPersona.Agregar(persona);
    }
}
