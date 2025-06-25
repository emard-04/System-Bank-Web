package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Interfaces.*;
import Entidades.*;

public class daoTelefono implements inTelefono{
	private String buscarxTelefono="Select * from telefonoxpersonas where Telefono=?";
	private String buscarxDni="Select * from telefonoxpersonas where dni=?";
	private String editar="update telefonoxpersonas set telefono=? where telefono=? and dni=?";
	private String Agregar="insert into telefonoxpersonas (dni, telefono) values(?,?)";
	private String Eliminar="Delete From telefonoxpersonas where telefono=? and dni=?";
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
            ps = cn.prepareStatement(Eliminar);
            ps.setString(1, telefono.getTelefono());
            ps.setString(2, telefono.getDni().getDni());
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
	
	@Override
	public TelefonoxPersona buscarXTelefono(String telefono) {
		TelefonoxPersona telPersona = null;
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(buscarxTelefono);
            ps.setString(1, telefono);
            rs = ps.executeQuery();

            if (rs.next()) {
                telPersona = new TelefonoxPersona();
                Persona persona = new Persona();
                persona.setDni(rs.getString("Dni"));  

                telPersona.setDni(persona);
                telPersona.setTelefono(telefono);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }

        return telPersona;
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
	
	
}
