package Daos;

import Entidades.TelefonoxPersona;
import Interfaces.inTelefono;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Interfaces.*;
import Entidades.*;

public class daoTelefono implements inTelefono{
	private String buscarxTelefono="Select * from telefonoxpersonas where Telefono=?";

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
	
}
