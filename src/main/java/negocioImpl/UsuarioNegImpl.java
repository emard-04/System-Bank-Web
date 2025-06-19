package negocioImpl;
import negocio.*;
import Interfaces.*;
import Daos.*;
import Entidades.*;
public class UsuarioNegImpl implements UsuarioNeg {

    private InUsuario daoUsuario = new daoUsuario();
    private inPersona daoPersona = new daoPersonas();

    @Override
    public boolean AgregarUsuario(Usuario usuario) {
        Persona persona = usuario.getPersona();
        if (daoPersona.existe(persona.getDni()))return false;
        if(daoPersona.verificarMail(persona.getCorreoElectronico()))return false;
        if(daoUsuario.existe(usuario.getNombreUsuario())) return false;
        boolean exitoPersona=daoPersona.Agregar(persona);
        if(!exitoPersona) {
        	System.out.println("No se agrego persona");
        	return false;
        }
        boolean exitoUsuario=daoUsuario.Agregar(usuario);
        if(!exitoUsuario) {
        	return false;
        }
        return true;
        

        
    }
    @Override
    public Usuario Login(String nombreUsuario, String contrasena) {
        return daoUsuario.Login(nombreUsuario, contrasena);
    }
}
