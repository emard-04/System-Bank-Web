package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//import Interfaces.inUsuario;
import Entidades.Usuario;
import Entidades.Persona; // Assuming you still need to interact with Persona
import Interfaces.Conexion;
import Interfaces.InUsuario;

public class daoUsuario  implements InUsuario{
    // SQL Queries adapted for the Usuario table
    private final String Agregar = "INSERT INTO Usuarios( Contraseña, dni, TipoUsuario, NombreUsuario) VALUES(?,?,?,?);";
    private final String Eliminar = "DELETE FROM Usuarios WHERE NombreUsuario=?;"; // Using NombreUsuario as PK for operations
    private final String Modificar = "UPDATE Usuario SET Contraseña=?, dni=?, TipoUsuario=? WHERE NombreUsuario=?;";
    private final String ListarTodo = "SELECT IdUsuario, NombreUsuario, Contraseña, dni, TipoUsuario FROM Usuarios;";
    private final String Existe = "SELECT * FROM Usuarios WHERE NombreUsuario=?;";
    private final String BuscarPorNombreUsuario = "SELECT IdUsuario, NombreUsuario, Contraseña, dni, TipoUsuario FROM Usuarios WHERE NombreUsuario=?;";
    private final String BuscarIdUsuario = "SELECT * FROM Usuarios WHERE IdUsuario=?;";
    private final String BuscarDniUsuario = "SELECT * FROM Usuarios WHERE dni=?;";
    private final String Login = "SELECT IdUsuario, NombreUsuario, Contraseña, dni, TipoUsuario FROM Usuarios WHERE NombreUsuario=? AND Contraseña=?;";


    public boolean Agregar(Usuario usuario) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = valoresQuery(cn, Agregar, usuario);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo agregar el usuario.");
        }
        return false;
    }

    private PreparedStatement valoresQuery(Connection cn, String query, Usuario usuario) {
        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(query);
            System.out.println(usuario);
            if (query.equals(Agregar)) {
                ps.setString(1, usuario.getContrasena());
                ps.setString(2, usuario.getPersona().getDni());
                ps.setBoolean(3, usuario.isTipoUsuario());
                ps.setString(4, usuario.getNombreUsuario());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }

    private Usuario valoresUsuario(ResultSet rs) {
        Usuario usuario = new Usuario();
        try {
            usuario.setIdUsuario(rs.getInt("IdUsuario"));
            usuario.setNombreUsuario(rs.getString("NombreUsuario"));
            usuario.setContrasena(rs.getString("Contraseña"));
            Persona persona = new Persona();
            persona.setDni(rs.getString("dni"));
            usuario.setPersona(persona);
            usuario.setTipoUsuario(rs.getBoolean("TipoUsuario"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public boolean Eliminar(String nombreUsuario) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = cn.prepareStatement(Eliminar);
            ps.setString(1, nombreUsuario);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo eliminar el usuario.");
        }
        return false;
    }

    public boolean Modificar(Usuario usuario) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = valoresQuery(cn, Modificar, usuario);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo modificar el usuario.");
        }
        return false;
    }

    public ArrayList<Usuario> ListarTodo() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ListarTodo);
            while (rs.next()) {
                Usuario usuario = valoresUsuario(rs);
                listaUsuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudieron listar los usuarios.");
        }
        return listaUsuarios;
    }

    public boolean existe(String nombreUsuario) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = cn.prepareStatement(Existe);
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Usuario BuscarPorNombreUsuario(String nombreUsuario) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = cn.prepareStatement(BuscarPorNombreUsuario);
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return valoresUsuario(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario(); // Return an empty Usuario object if not found or an error occurs
    }
    public Usuario BuscarDni(String Dni) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            if(cn==null) {
            	System.out.println("lcdtm");
            }
            
            PreparedStatement ps = cn.prepareStatement(BuscarDniUsuario);
            ps.setString(1, Dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return valoresUsuario(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario(); // Return an empty Usuario object if not found or an error occurs}
       
    }
     
    public Usuario BuscarIdusuario(int id) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = cn.prepareStatement(BuscarIdUsuario);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return valoresUsuario(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario(); // Return an empty Usuario object if not found or an error occurs
    }

    public Usuario Login(String nombreUsuario, String contrasena) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = cn.prepareStatement(Login);
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return valoresUsuario(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if login fails
    }
    public int obtenerIdUsuarioPorNombre(String nombreUsuario) {
        int id = -1; // valor por defecto si no se encuentra
        String sql = "SELECT IdUsuario FROM Usuarios WHERE NombreUsuario = ?";
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("IdUsuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al obtener ID de usuario por nombre.");
        }
        return id;
    }
    
    public static void main(String[] args) {
		daoUsuario du=new daoUsuario();
		for(Usuario u: du.ListarTodo()) {
			System.out.println(u);
		}
	}
}
