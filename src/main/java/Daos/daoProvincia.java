package Daos;

import java.util.List;
import Entidades.Provincia;
import Interfaces.inProvincia;
import java.util.ArrayList;
import Interfaces.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class daoProvincia implements inProvincia {
	private final String Listar = "SELECT IdProvincia, Nombre FROM Provincia ORDER BY Nombre";
	private final String ListarProvinciaxPais = "select * from Provincia where idPais=?";

	@Override
	public List<Provincia> listarProvincias() {
		List<Provincia> lista = new ArrayList<>();
		try (Connection cn = Conexion.getConexion().getSQLConnection();
				PreparedStatement ps = cn.prepareStatement(Listar);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Provincia prov = new Provincia();
				prov.setIdProvincia(rs.getInt("IdProvincia"));
				prov.setNombre(rs.getString("Nombre"));
				lista.add(prov);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;

	}

	public List<Provincia> listarProvinciasxPais(int idPais) {
		List<Provincia> lista = new ArrayList<>();
		try {
			Connection cn = Conexion.getConexion().getSQLConnection();
			PreparedStatement ps = cn.prepareStatement(ListarProvinciaxPais);
			ps.setInt(1, idPais);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Provincia prov = new Provincia();
				prov.setIdProvincia(rs.getInt("IdProvincia"));
				prov.setNombre(rs.getString("Nombre"));
				lista.add(prov);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;

	}

	@Override
	public Provincia buscarPorId(int idProvincia) {
		Provincia provincia = null;
		String query = "SELECT * FROM Provincia WHERE IdProvincia = ?";
		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, idProvincia);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				provincia = new Provincia();
				provincia.setIdProvincia(idProvincia);
				provincia.setNombre(rs.getString("Nombre"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return provincia;
	}
}
