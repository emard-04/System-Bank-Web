package Daos;

import java.sql.SQLException;
import Interfaces.*;
import java.util.List;
import java.sql.Connection;
import Entidades.Cuota;
import Interfaces.inCuotas;
import java.util.ArrayList;
import Interfaces.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.math.BigDecimal;
import java.time.LocalDate;


public class daoCuotas implements inCuotas{
	private final InPrestamos prestamosNeg = new daoPrestamos(); 
	@Override
    public List<Cuota> obtenerCuotasPendientesPorUsuario(int idUsuario) throws SQLException {
        List<Cuota> cuotasPendientes = new ArrayList<>();
        String sql = "SELECT c.IdCuota, c.NroCuota, c.Importe, c.FechaVencimiento, c.FechaPago, c.IdPrestamo " +
                     "FROM Cuotas c " +
                     "JOIN Prestamos p ON c.IdPrestamo = p.IdPrestamo " +
                     "WHERE p.IdUsuario = ? AND c.FechaPago IS NULL " +
                     "ORDER BY c.NroCuota";

        try (Connection conn = Conexion.getConexion().getSQLConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cuota cuota = new Cuota();
                    cuota.setIdCuota(rs.getInt("IdCuota"));
                    cuota.setNroCuota(rs.getInt("NroCuota"));
                    cuota.setImporte(rs.getBigDecimal("Importe"));
                    cuota.setFechaVencimiento(rs.getDate("FechaVencimiento").toLocalDate());
                    Date fechaPago = rs.getDate("FechaPago");
                    cuota.setFechaPago(fechaPago != null ? fechaPago.toLocalDate() : null);
                    cuota.setIdPrestamo(rs.getInt("IdPrestamo"));
                    cuotasPendientes.add(cuota);
                }
            }
        }
        return cuotasPendientes;
    }

	@Override
    public boolean pagarCuota(int idCuota, int nroCuenta) throws SQLException {
        Connection conn = null;
        PreparedStatement psUpdateCuota = null;
        PreparedStatement psUpdateCuenta = null;
        boolean exito = false;

        try {
            conn = Conexion.getConexion().getSQLConnection();
            conn.setAutoCommit(false);

            // 1. Obtener importe de la cuota a pagar
            String sqlImporte = "SELECT Importe FROM Cuotas WHERE IdCuota = ? AND FechaPago IS NULL";
            BigDecimal importe = null;

            try (PreparedStatement ps = conn.prepareStatement(sqlImporte)) {
                ps.setInt(1, idCuota);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        importe = rs.getBigDecimal("Importe");
                    } else {
                        conn.rollback();
                        return false; // Cuota ya pagada o no existe
                    }
                }
            }

            // 2. Verificar saldo de la cuenta
            String sqlSaldo = "SELECT Saldo FROM cuentas WHERE NroCuenta = ?";
            BigDecimal saldo = null;
            try (PreparedStatement ps = conn.prepareStatement(sqlSaldo)) {
                ps.setInt(1, nroCuenta);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        saldo = rs.getBigDecimal("Saldo");
                    } else {
                        conn.rollback();
                        return false; // Cuenta no existe
                    }
                }
            }

            if (saldo.compareTo(importe) < 0) {
                conn.rollback();
                return false; // No hay saldo suficiente
            }

            // 3. Descontar importe de la cuenta
            String sqlUpdateCuenta = "UPDATE Cuentas SET Saldo = Saldo - ? WHERE NroCuenta = ?";
            psUpdateCuenta = conn.prepareStatement(sqlUpdateCuenta);
            psUpdateCuenta.setBigDecimal(1, importe);
            psUpdateCuenta.setInt(2, nroCuenta);
            psUpdateCuenta.executeUpdate();

            // 4. Actualizar cuota como pagada con fecha actual
            String sqlUpdateCuota = "UPDATE Cuotas SET FechaPago = CURDATE() WHERE IdCuota = ?";
            psUpdateCuota = conn.prepareStatement(sqlUpdateCuota);
            psUpdateCuota.setInt(1, idCuota);
            psUpdateCuota.executeUpdate();
            
         // 5. Verificar si fue la última cuota
            int idPrestamo = prestamosNeg.obtenerIdPrestamoPorCuota(idCuota);
            System.out.println("si entro aca");
            int cuotasPendientes = prestamosNeg.contarCuotasPendientesPorPrestamo(idPrestamo);
            System.out.println("Quedan " + cuotasPendientes + " cuotas sin pagar.");
            System.out.println("DEBUG: Cuotas pendientes para préstamo " + idPrestamo + " = " + cuotasPendientes);
            System.out.println("si entro aca tambien");

            if (cuotasPendientes == 1) {
                System.out.println("✅ Última cuota pagada. Actualizando EstadoPago a 'Pagado' usando cambiarEstadoPago");

                boolean actualizado = prestamosNeg.cambiarEstadoPago(idPrestamo, "Pagado", conn);

                if (!actualizado) {
                    System.out.println("❌ Error al actualizar EstadoPago del préstamo.");
                    conn.rollback(); // revertir todo si falla
                    return false;
                }
            }

            // Si querés, acá podrías insertar un registro en tabla movimientos bancarios.

            conn.commit();
            exito = true;

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            e.printStackTrace();
        } finally {
            if (psUpdateCuota != null) psUpdateCuota.close();
            if (psUpdateCuenta != null) psUpdateCuenta.close();
            if (conn != null) conn.setAutoCommit(true);
            if (conn != null) conn.close();
        }

        return exito;
    }
	public boolean generarCuotasParaPrestamo(int idPrestamo, BigDecimal importeCuota, int cantidadCuotas) {
		
		Connection conn = null;
	    PreparedStatement ps = null;

	    String sql = "INSERT INTO Cuotas (NroCuota, Importe, FechaVencimiento, FechaPago, IdPrestamo) VALUES (?, ?, ?, ?, ?)";

	    try {
	        conn = Conexion.getConexion().getSQLConnection();
	        conn.setAutoCommit(false);
	        ps = conn.prepareStatement(sql);

	        LocalDate vencimiento = LocalDate.now().plusMonths(1);

	        for (int i = 1; i <= cantidadCuotas; i++) {
	            ps.setInt(1, i);  // NroCuota
	            ps.setBigDecimal(2, importeCuota);  // Importe
	            ps.setDate(3, java.sql.Date.valueOf(vencimiento));  // FechaVencimiento
	            ps.setNull(4, java.sql.Types.DATE);  // FechaPago (null)
	            ps.setInt(5, idPrestamo);  // IdPrestamo

	            ps.addBatch();
	            vencimiento = vencimiento.plusMonths(1);
	        }

	        int[] resultados = ps.executeBatch();
	        for (int r : resultados) {
	            if (r <= 0) return false;  // Si alguna cuota no se insertó
	        }
	        conn.commit();  // Muy importante si estás trabajando con transacciones
	        return true;

	    } catch (Exception e) {
	        e.printStackTrace();
	        try {
	            if (conn != null) conn.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;

	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
		
	}
	public List<Cuota> obtenerCuotasPendientesPorCuenta(int nroCuenta) throws SQLException {
	    String sql = "SELECT c.* FROM Cuotas c INNER JOIN Prestamos p ON c.IdPrestamo = p.IdPrestamo WHERE p.IdCuenta = ? AND c.FechaPago IS NULL";
	    List<Cuota> lista = new ArrayList<>();
	    
	    try (Connection conn = Conexion.getConexion().getSQLConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setInt(1, nroCuenta);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	            	Cuota cuota = new Cuota();
	                cuota.setIdCuota(rs.getInt("IdCuota"));
	                cuota.setNroCuota(rs.getInt("NroCuota"));
	                cuota.setImporte(rs.getBigDecimal("Importe"));
	                cuota.setFechaVencimiento(rs.getDate("FechaVencimiento").toLocalDate());

	                // FechaPago puede ser null, así que la controlamos
	                Date fechaPago = rs.getDate("FechaPago");
	                if (fechaPago != null) {
	                    cuota.setFechaPago(fechaPago.toLocalDate());
	                } else {
	                    cuota.setFechaPago(null);
	                }
	                lista.add(cuota);
	            }
	        }
	    }
	    return lista;
	}
	@Override
	public Cuota obtenerCuotaPorId(int idCuota) {
	    Cuota cuota = null;
	    String query = "SELECT * FROM Cuotas WHERE IdCuota = ?";

	    try (Connection conn = Conexion.getConexion().getSQLConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setInt(1, idCuota);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            cuota = new Cuota();
	            cuota.setIdCuota(rs.getInt("IdCuota"));
	            cuota.setNroCuota(rs.getInt("NroCuota"));
	            cuota.setImporte(rs.getBigDecimal("Importe"));
	            
	            Date fechaVenc = rs.getDate("FechaVencimiento");
	            if (fechaVenc != null) {
	                cuota.setFechaVencimiento(fechaVenc.toLocalDate());
	            }

	            Date fechaPago = rs.getDate("FechaPago");
	            if (fechaPago != null) {
	                cuota.setFechaPago(fechaPago.toLocalDate());
	            }

	            cuota.setIdPrestamo(rs.getInt("IdPrestamo"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return cuota;
	}
	public boolean existenCuotasPendientesPorPrestamo(int idPrestamo) throws SQLException {
	    String query = "SELECT COUNT(*) FROM Cuotas WHERE IdPrestamo = ? AND FechaPago IS NULL";
	    
	    try (Connection conn = Conexion.getConexion().getSQLConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setInt(1, idPrestamo);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    }

	    return false;
	}

}
