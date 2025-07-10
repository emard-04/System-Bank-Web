package negocioImpl;
import Daos.daoPersonas;
import Daos.daoUsuario;
import Interfaces.inPersona;
import negocio.*;
import Entidades.*;
import java.util.ArrayList;
public class PersonaNegImpl implements ClientesNeg{

    private inPersona daoP = new daoPersonas();
    private TelefonoNeg telefonoNeg= new TelefonoNegImpl();
    @Override
    public boolean Agregar(Persona persona) {
        
        if (daoP.existe(persona.getDni()))return false;
        if(verificarMail(persona.getCorreoElectronico()))return false;
        return daoP.Agregar(persona);
    }
    public boolean verificarMail(String mail) {
        return daoP.verificarMail(mail);
    }

    @Override
    public boolean Modificar(Persona persona) {
        Persona pers= new Persona();
        boolean existe =false;
        pers= daoP.existeObj(persona.getDni());
        if(!persona.getCorreoElectronico().equals(pers.getCorreoElectronico())) {
            existe=verificarMail(persona.getCorreoElectronico());
        }
        if(existe) {
            return false;
        }

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
        return daoP.existeObj(dni);
    }
}