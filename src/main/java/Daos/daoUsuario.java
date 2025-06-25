package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Usuario;
import Entidades.Persona;
import Interfaces.Conexion;
import Interfaces.InUsuario;

public class daoUsuario implements InUsuario {
    private final String Agregar = "INSERT INTO Usuarios( Contraseña, dni, TipoUsuario, NombreUsuario) VALUES(?,?,?,?);";
    private final String Eliminar = "DELETE FROM Usuarios WHERE NombreUsuario=?;";
    private final String Modificar = "UPDATE Usuarios SET NombreUsuario=?,Contraseña=? WHERE Dni=?;";
    private final String ListarTodo = "SELECT IdUsuario, NombreUsuario, Contraseña, dni, TipoUsuario FROM Usuarios where TipoUsuario=1;";
    private final String Existe = "SELECT * FROM Usuarios WHERE NombreUsuario=?;";
    private final String ExisteDni = "SELECT * FROM Usuarios WHERE Dni=?;";
    private final String BuscarIdUsuario = "SELECT * FROM Usuarios WHERE IdUsuario=?;";
    private final String Login = "SELECT IdUsuario, NombreUsuario, Contraseña, dni, TipoUsuario FROM Usuarios WHERE NombreUsuario=? AND Contraseña=?;";
    private static daoPersonas dp;
    public boolean Agregar(Usuario usuario) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = valoresQuery(cn, Agregar, usuario);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo agregar el usuario.");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }

    private PreparedStatement valoresQuery(Connection cn, String query, Usuario usuario) {
        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(query);
            if (query.equals(Agregar)) {
                ps.setString(1, usuario.getContrasena());
                ps.setString(2, usuario.getPersona().getDni());
                ps.setBoolean(3, usuario.isTipoUsuario());
                ps.setString(4, usuario.getNombreUsuario());
            }
            if (query.equals(Modificar)) {
            	ps.setString(1, usuario.getNombreUsuario());
                ps.setString(2, usuario.getContrasena());
                ps.setString(3, usuario.getPersona().getDni());
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
            dp=new daoPersonas();
            Persona persona =(dp.existeObj(rs.getString("dni")));
            usuario.setPersona(persona);
            usuario.setTipoUsuario(rs.getBoolean("TipoUsuario"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public boolean Eliminar(String nombreUsuario) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(Eliminar);
            ps.setString(1, nombreUsuario);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo eliminar el usuario.");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }

    public boolean Modificar(Usuario usuario) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = valoresQuery(cn, Modificar, usuario);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo modificar el usuario.");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }

    public ArrayList<Usuario> ListarTodo() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            st = cn.createStatement();
            rs = st.executeQuery(ListarTodo);
            while (rs.next()) {
                Usuario usuario = valoresUsuario(rs);
                listaUsuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudieron listar los usuarios.");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (st != null) st.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return listaUsuarios;
    }

    public boolean existe(String nombreUsuario) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(Existe);
            ps.setString(1, nombreUsuario);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }

    public Usuario BuscarDni(String Dni) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(ExisteDni);
            ps.setString(1, Dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                return valoresUsuario(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return null;
    }

    public Usuario BuscarIdusuario(int id) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(BuscarIdUsuario);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return valoresUsuario(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return new Usuario();
    }

    public Usuario Login(String nombreUsuario, String contrasena) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(Login);
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();
            if (rs.next()) {
                return valoresUsuario(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return null;
    }

    public int obtenerIdUsuarioPorNombre(String nombreUsuario) {
        int id = -1;
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            String sql = "SELECT IdUsuario FROM Usuarios WHERE NombreUsuario = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("IdUsuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al obtener ID de usuario por nombre.");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return id;
    }
}
