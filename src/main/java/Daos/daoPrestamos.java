package Daos;
import Interfaces.*;
import Entidades.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List; 
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class daoPrestamos implements InPrestamos{
	 private static final String INSERT = 
		        "INSERT INTO Prestamos (IdUsuario, Fecha, ImporteAPagar, ImportePedido, PlazoDePago, MontoCuotasxMes, EstadoSolicitud, EstadoPago) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

		    @Override
		    public boolean agregar(Prestamos p) {
		        try (Connection conn = Conexion.getConexion().getSQLConnection();
		             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
		            
		            stmt.setInt(1, p.getUsuario().getIdUsuario());
		            stmt.setDate(2, Date.valueOf(p.getFecha()));
		            stmt.setBigDecimal(3, p.getImporteApagar());
		            stmt.setBigDecimal(4, p.getImportePedido());
		            stmt.setString(5, p.getPlazoDePago());
		            stmt.setBigDecimal(6, p.getMontoCuotasxMes());
		            stmt.setString(7, p.getEstadoSolicitud());
		            stmt.setString(8, p.getEstadoPago());
		           
		            

		            if(stmt.executeUpdate() > 0) {
		            	conn.commit();
		            	return true;
		            }
		            		
		             
		            	

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return false;
		    }

		    @Override
		    public List<Prestamos> obtenerTodos() {
		        List<Prestamos> lista = new ArrayList<>();
		        String query = "SELECT * FROM Prestamos";

		        try (Connection conn = Conexion.getConexion().getSQLConnection();
		             Statement stmt = conn.createStatement();
		             ResultSet rs = stmt.executeQuery(query)) {

		            while (rs.next()) {
		                Prestamos p = mapear(rs);
		                lista.add(p);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        return lista;
		    }

		    @Override
		    public List<Prestamos> obtenerPorUsuario(int idUsuario) {
		        List<Prestamos> lista = new ArrayList<>();
		        String query = "SELECT * FROM Prestamos WHERE IdUsuario = ?";

		        try (Connection conn = Conexion.getConexion().getSQLConnection();
		             PreparedStatement stmt = conn.prepareStatement(query)) {
		            
		            stmt.setInt(1, idUsuario);
		            ResultSet rs = stmt.executeQuery();

		            while (rs.next()) {
		                Prestamos p = mapear(rs);
		                lista.add(p);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        return lista;
		    }

		
		    

		    private Prestamos mapear(ResultSet rs) throws SQLException {
		        Prestamos p = new Prestamos();
		        p.setIdPrestamo(rs.getInt("IdPrestamo"));

		        Usuario u = new Usuario();
		        u.setIdUsuario(rs.getInt("IdUsuario"));
		        p.setUsuario(u);

		        p.setFecha(rs.getDate("Fecha").toLocalDate());
		        p.setImporteApagar(rs.getBigDecimal("ImporteAPagar"));
		        p.setImportePedido(rs.getBigDecimal("ImportePedido"));
		        p.setPlazoDePago(rs.getString("PlazoDePago"));
		        p.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
		        p.setEstadoSolicitud(rs.getString("EstadoSolicitud"));
		        p.setEstadoPago(rs.getString("EstadoPago"));
		        
		        return p;
		    }
		    @Override
		    public Prestamos obtenerPendientePorUsuario(int idUsuario) {
		        Connection con = null;
		        PreparedStatement ps = null;
		        ResultSet rs = null;

		        try {
		            con = Conexion.getConexion().getSQLConnection();
		            ps = con.prepareStatement("SELECT * FROM Prestamos WHERE IdUsuario = ? AND Estado = true LIMIT 1");
		            ps.setInt(1, idUsuario);
		            rs = ps.executeQuery();

		            if (rs.next()) {
		                Prestamos p = new Prestamos();
		                p.setIdPrestamo(rs.getInt("IdPrestamo"));
		                p.setImportePedido(rs.getBigDecimal("ImportePedido"));
		                p.setImporteApagar(rs.getBigDecimal("ImporteApagar"));
		                p.setPlazoDePago(rs.getString("PlazoDePago"));
		                p.setFecha(rs.getDate("Fecha").toLocalDate());
		                p.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
		                p.setEstadoSolicitud(rs.getString("EstadoSolicitud"));
				        p.setEstadoPago(rs.getString("EstadoPago"));
		                return p;
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if (rs != null) rs.close();
		                if (ps != null) ps.close();
		                if (con != null) con.close();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }

		        return null;
		    }

		    @Override
		    public boolean cambiarEstadoPago(int idPrestamo, String nuevoEstadoPago) {
		        String query = "UPDATE Prestamos SET EstadoPago = ? WHERE IdPrestamo = ?";

		        try (Connection conn = Conexion.getConexion().getSQLConnection();
		             PreparedStatement stmt = conn.prepareStatement(query)) {
		            
		            stmt.setString(1, nuevoEstadoPago);
		            stmt.setInt(2, idPrestamo);

		            return stmt.executeUpdate() > 0;

		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        return false;
		    }

			@Override
			public boolean cambiarEstadoSolicitado(int idPrestamo, String nuevoEstadoSoli) {
				String query = "UPDATE Prestamos SET EstadoSolicitado = ? WHERE IdPrestamo = ?";

		        try (Connection conn = Conexion.getConexion().getSQLConnection();
		             PreparedStatement stmt = conn.prepareStatement(query)) {
		            
		            stmt.setString(1, nuevoEstadoSoli);
		            stmt.setInt(2, idPrestamo);

		            return stmt.executeUpdate() > 0;

		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        return false;
			}
		}

