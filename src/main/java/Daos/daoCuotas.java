package Daos;

import java.sql.SQLException;
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



public class daoCuotas implements inCuotas{

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
            String sqlSaldo = "SELECT Saldo FROM Cuentas WHERE NroCuenta = ?";
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

}
