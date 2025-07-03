package Daos;

import java.sql.Connection;
import Entidades.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Interfaces.inPersona;
import Entidades.Persona;
import Entidades.TelefonoxPersona;
import Interfaces.Conexion;

public class daoPersonas implements inPersona {
    private final String Agregar = "insert into Persona(CorreoElectronico, Cuil, Nombre, Apellido, Sexo, Nacionalidad, fechaNacimiento, Direccion, IdLocalidad, IdProvincia, Dni, Estado) values(?,?,?,?,?,?,?,?,?,?,?,?);";
    private final String Eliminar = "UPDATE Persona SET Estado = 'Inactiva' where Dni=?;";
    private final String Modificar = "Update Persona set CorreoElectronico=?, Cuil=?, Nombre=?, Apellido=?, Sexo=?, Nacionalidad=?, fechaNacimiento=?, Direccion=?, IdLocalidad=?, IdProvincia=? where Dni=?;";
    private final String ListarTodo = "SELECT p.Dni, p.Cuil, p.Nombre, p.Apellido, p.CorreoElectronico,\r\n"
    		+ "       loc.IdLocalidad, loc.Nombre AS NombreLocalidad,\r\n"
    		+ "       prov.IdProvincia, prov.Nombre AS NombreProvincia,\r\n"
    		+ "       p.Nacionalidad, p.Sexo, p.fechaNacimiento, p.Direccion\r\n"
    		+ "FROM Persona p\r\n"
    		+ "JOIN Localidad loc ON p.IdLocalidad = loc.IdLocalidad\r\n"
    		+ "JOIN Provincia prov ON p.IdProvincia = prov.IdProvincia\r\n"
    		+ "WHERE p.Estado = 'Activo';";
    private final String Existe = "Select * from Persona where dni=?;";
    private final String ExisteMail = "Select * from Persona where CorreoElectronico=?;";

    public boolean Agregar(Persona persona) {
        Connection cn = null;
        PreparedStatement cs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            cs = valoresQuery(cn, Agregar, persona);
            
            if (cs.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo conectar");
        } finally {
            try { if (cs != null) cs.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }

    private PreparedStatement valoresQuery(Connection cn, String query, Persona persona) {
        PreparedStatement cs = null;
        try {
            cs = cn.prepareStatement(query);
            cs.setString(1, persona.getCorreoElectronico());
            cs.setString(2, persona.getCuil());
            cs.setString(3, persona.getNombre());
            cs.setString(4, persona.getApellido());
            cs.setString(5, persona.getSexo());
            cs.setString(6, persona.getNacionalidad());
            cs.setDate(7, java.sql.Date.valueOf(persona.getFechaNacimiento()));
            cs.setString(8, persona.getDireccion());
            cs.setInt(9, persona.getLocalidad().getIdLocalidad());
            cs.setInt(10, persona.getProvincia().getIdProvincia());
            cs.setString(11, persona.getDni());
            cs.setString(12, "Activo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cs;
    }

    private Persona valoresPersona(ResultSet rs) {
        Persona persona = new Persona();
        daoTelefono dTel = new daoTelefono();
        daoLocalidad dLoc= new daoLocalidad();
        daoProvincia dProv= new daoProvincia();
        try {
            persona.setCorreoElectronico(rs.getString("CorreoElectronico"));
            persona.setCuil(rs.getString("Cuil"));
            persona.setNombre(rs.getString("Nombre"));
            persona.setApellido(rs.getString("Apellido"));
            persona.setSexo(rs.getString("Sexo"));
            persona.setNacionalidad(rs.getString("Nacionalidad"));
            persona.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
            persona.setDireccion(rs.getString("Direccion"));
            Localidad loc = dLoc.buscarPorId(rs.getInt("IdLocalidad"),rs.getInt("IdProvincia"));
            if (loc != null) {
                persona.setLocalidad(loc);
            } else {
                persona.setLocalidad(null); // o lanzar excepción si es obligatorio
            }
            Provincia prov = dProv.buscarPorId(rs.getInt("IdProvincia"));
            if (loc != null) {
                persona.setProvincia(prov);
            } else {
                persona.setProvincia(null); // o lanzar excepción si es obligatorio
            }
            persona.setDni(rs.getString("Dni"));
            ArrayList<TelefonoxPersona>Lista=dTel.listarTelefonos(persona.getDni());
            ArrayList<String> telefonos = new ArrayList<>();
            for (TelefonoxPersona t : Lista) {
                telefonos.add(t.getTelefono());
            }
            persona.setTelefonos(telefonos);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return persona;
    }

    public boolean Eliminar(String dni) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(Eliminar);
            ps.setString(1, dni);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }

    public boolean Modificar(Persona persona) {
        Connection cn = null;
        PreparedStatement cs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            cs = valoresQuery(cn, Modificar, persona);
            if (cs.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (cs != null) cs.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }

    public ArrayList<Persona> ListarTodo() {
        ArrayList<Persona> ListaPersona = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            st = cn.createStatement();
            rs = st.executeQuery(ListarTodo);
            while (rs.next()) {
            	System.out.println("crack");
                Persona persona = valoresPersona(rs);
                ListaPersona.add(persona);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (st != null) st.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return ListaPersona;
    }

    public boolean existe(String dni) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(Existe);
            ps.setString(1, dni);
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

    public Persona existeObj(String dni) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(Existe);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                return valoresPersona(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return new Persona();
    }

    public boolean verificarMail(String mail) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(ExisteMail);
            ps.setString(1, mail);
            rs = ps.executeQuery();
            if(rs.next()) {
            return true;}
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }
}