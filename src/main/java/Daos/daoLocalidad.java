package Daos;

import java.util.List;

import Entidades.Localidad;
import Interfaces.inLocalidad;
import Entidades.Provincia;
import Interfaces.inProvincia;
import java.util.ArrayList;
import Interfaces.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class daoLocalidad implements inLocalidad{
	 private final String Listar = "SELECT IdLocalidad, Nombre FROM Localidad WHERE IdProvincia = ? ORDER BY Nombre";
	@Override
	public List<Localidad> listarLocalidadesPorProvincia(int idProvincia) {
		 List<Localidad> lista = new ArrayList<>();
	        try (Connection cn = Conexion.getConexion().getSQLConnection();
	             PreparedStatement ps = cn.prepareStatement(Listar)) {

	            ps.setInt(1, idProvincia);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Localidad loc = new Localidad();
	                    loc.setIdLocalidad(rs.getInt("IdLocalidad"));
	                    loc.setNombre(rs.getString("Nombre"));
	                    lista.add(loc);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return lista;
	    }
	 @Override
	    public Localidad buscarPorId(int idLocalidad, int idProvincia) {
	        Localidad loc = null;
	        daoProvincia dp= new daoProvincia();
	        String query = "SELECT * FROM Localidad WHERE IdLocalidad = ? and IdProvincia=?";
	        try (Connection conn = Conexion.getConexion().getSQLConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {

	            stmt.setInt(1, idLocalidad);
	            stmt.setInt(2, idProvincia);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                loc = new Localidad();
	                loc.setIdLocalidad(rs.getInt("IdLocalidad"));
	                loc.setNombre(rs.getString("Nombre"));
	                Provincia provincia = dp.buscarPorId(rs.getInt("IdProvincia"));
	                loc.setProvincia(provincia);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return loc;
	    }
	

}
