package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Interfaces.*;
import Entidades.*;

public class daoTelefono implements inTelefono{
	private String buscarxTelefono="Select * from telefonoxpersonas where Telefono=? and Estado='Activo'";
	private String buscarxDni="Select * from telefonoxpersonas where dni=? and Estado='Activo'";
	private String editar="update telefonoxpersonas set telefono=? where telefono=? and dni=?";
	private String Agregar="insert into telefonoxpersonas (dni, telefono) values(?,?)";
	private String EliminarxTelefono="update telefonoxpersonas set Estado=? where telefono=? and dni=?";
	private String EliminarxDni="update telefonoxpersonas set Estado=? where dni=?";
	private String existe="Select * from telefonoxpersonas where telefono?;";
	public boolean Agregar(TelefonoxPersona telefono) {
		Connection cn = null;
        PreparedStatement cs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            cs = cn.prepareStatement(Agregar);
            cs.setString(1, telefono.getDni().getDni());
            cs.setString(2, telefono.getTelefono());
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
	public boolean Eliminar(TelefonoxPersona telefono) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(EliminarxTelefono);
            ps.setString(1, "Inactivo");
            ps.setString(2, telefono.getTelefono());
            ps.setString(3, telefono.getDni().getDni());
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
	public boolean EliminarxDni(String dni) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(EliminarxDni);
            ps.setString(1, "Inactivo");
            ps.setString(2,dni);
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
	public int maximoTelefonos(TelefonoxPersona telefono) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int contador=0;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(buscarxDni);
            ps.setString(1, telefono.getDni().getDni());
            rs = ps.executeQuery();
            while (rs.next()) {
            	contador++;
                cn.commit();
            }
            return contador;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return -1;
    }	
	
	public ArrayList<TelefonoxPersona> listarTelefonos(String dni) {
		TelefonoxPersona telPersona = null;
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<TelefonoxPersona>telefonos=new ArrayList<TelefonoxPersona>();
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(buscarxDni);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            while (rs.next()) {
                telPersona = new TelefonoxPersona();
                Persona persona = new Persona();
                persona.setDni(rs.getString("Dni"));  

                telPersona.setDni(persona);
                telPersona.setTelefono(rs.getString("telefono"));
                telefonos.add(telPersona);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }

        return telefonos;
    }
	public boolean Modificar(String oldTelefono,TelefonoxPersona telefono) {
        Connection cn = null;
        PreparedStatement cs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            cs = cn.prepareStatement( editar);
            cs.setString(1, telefono.getTelefono());
            cs.setString(2, oldTelefono);
            cs.setString(3, telefono.getDni().getDni());
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
	public boolean existe(String telefono) {
		Connection cn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			cn=Conexion.getConexion().getSQLConnection();
			ps= cn.prepareStatement(buscarxTelefono);
			ps.setString(1, telefono);
			rs = ps.executeQuery();
            return rs.next();
		}catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
            try { if (rs != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }
	public TelefonoxPersona buscarPorDni(String dni) {
        TelefonoxPersona telefono = null;

        try (Connection conn = Conexion.getConexion().getSQLConnection();
             PreparedStatement ps = conn.prepareStatement(buscarxDni)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    telefono = new TelefonoxPersona();
                    telefono.setTelefono(rs.getString("Telefono"));

                    Persona persona = new Persona();
                    persona.setDni(dni); // Asignás solo el dni, si es lo único que necesitás
                    telefono.setDni(persona);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return telefono;
    }
}

