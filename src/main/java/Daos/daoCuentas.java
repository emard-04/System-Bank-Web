package Daos;

import java.sql.Connection;

import java.util.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.math.BigDecimal; // Import for BigDecimal

//import Interfaces.inCuenta; // Assuming you will create this interface
import Entidades.Cuenta;
import Entidades.Persona;
import Entidades.TipoCuenta; // Necesario para el objeto TipoCuenta dentro de Cuenta
import Entidades.Usuario;
import Interfaces.Conexion;
import Interfaces.inCuentas;
import java.util.List;

public class daoCuentas implements inCuentas{
    // SQL Queries adaptadas para la tabla Cuentas
    private final String Agregar = "INSERT INTO Cuentas(IdUsuario, FechaCreacion, IdtipoCuenta, Cbu, Saldo, Estado) VALUES(?,?,?,?,?,?);";
    private final String obtenerIdCuenta="Select AUTO_INCREMENT from information_schema.TABLES where TABLE_SCHEMA='bancoparcial' and TABLE_NAME='cuentas';";//IdCuentas
    private final String Eliminar = "UPDATE Cuentas SET Estado = 'Inactiva' WHERE NroCuenta = ?;";
    private final String EliminarTodasCuentas = "UPDATE Cuentas SET Estado = 'Inactiva' WHERE idUsuario = ?;";
    private final String Modificar = "UPDATE Cuentas SET FechaCreacion=?, IdtipoCuenta=?, Saldo=? WHERE NroCuenta=?;";
    private final String ListarTodo = "SELECT NroCuenta, cuentas.IdUsuario, FechaCreacion, IdtipoCuenta, Cbu, Saldo, cuentas.Estado FROM Cuentas inner join usuarios on cuentas.idusuario=usuarios.idusuario WHERE cuentas.Estado = 'Activa' and Tipousuario=0;";
    private final String ListarxUsuario = "SELECT NroCuenta, IdUsuario, FechaCreacion, IdtipoCuenta, Cbu, Saldo, Estado FROM Cuentas WHERE (Estado = 'Activa' OR Estado = 'Activo' )and idUsuario=?;";
    private final String existe = "SELECT * FROM Cuentas WHERE NroCuenta=?;";
    private final String existeCBU = "SELECT * FROM Cuentas WHERE Cbu=?;";
    private final String BuscarPorNro = "SELECT NroCuenta, IdUsuario, FechaCreacion, IdtipoCuenta, Cbu, Saldo, Estado FROM Cuentas WHERE NroCuenta=?;";
    private final String maximoCuentas="Select count(IdUsuario) as Cantidad from cuentas where idUsuario=? and Estado=?";
 

   
    public  daoCuentas() {
		// TODO Auto-generated constructor stub
	}
    public int obtenerIdCuenta() {
    	Connection cn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
        try {
             cn = Conexion.getConexion().getSQLConnection();
             ps = cn.prepareStatement(obtenerIdCuenta);
             rs=ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("AUTO_INCREMENT");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL BUSCAR ID.");
        }
        finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return 0;
    }
    public String generarCBUAleatorio() {
        Random random = new Random();
        String cbu;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 22; i++) {
                sb.append(random.nextInt(10));
            }
            cbu = sb.toString();
        } while (verificarCbu(cbu));
        return cbu;
        
    }
    private boolean verificarCbu(String cbu) {
    	Connection cn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(existeCBU);
            ps.setString(1, cbu);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }
    public ArrayList<Cuenta> filtrar(String condicionesExtras, String orden, ArrayList<Object> parametrosExtras) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConexion().getSQLConnection();
            StringBuilder query = new StringBuilder();
            query.append("SELECT * ");
            query.append("FROM cuentas ");
            query.append("INNER JOIN usuarios ON cuentas.IdUsuario = usuarios.IdUsuario ");
            query.append("WHERE (cuentas.Estado = 'Activa' or cuentas.Estado='Activo')");
            query.append(condicionesExtras);
            query.append(orden);  // ← lo agregás acá


            ps = cn.prepareStatement(query.toString());

            int paramIndex = 1;
            for (Object param : parametrosExtras) {
                if (param instanceof Integer) {
                    ps.setInt(paramIndex++, (Integer) param);
                } else if (param instanceof String) {
                    ps.setString(paramIndex++, (String) param);
                }
            }

            ArrayList<Cuenta> lista = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(valoresCuenta(rs));
            }

            return lista;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    public Cuenta cuentaxCbu(String cbu) {
    	Connection cn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	Cuenta cuenta=null;
    	try {
            cn = Conexion.getConexion().getSQLConnection();
            System.out.println("try");
            ps = cn.prepareStatement(existeCBU);
            ps.setString(1, cbu);
            rs = ps.executeQuery();
            if(rs.next()) {
            return valoresCuenta(rs);}
        } catch (Exception e) {
            e.printStackTrace();
        }
    	finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
    	System.out.println("null"+ cuenta);
        return cuenta;
    }
    
    public boolean Agregar(Cuenta cuenta) {
    	Connection cn = null;
    	PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = valoresQuery(cn, Agregar, cuenta);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo agregar la cuenta.");
        }
        finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }
    
    private PreparedStatement valoresQuery(Connection cn, String query, Cuenta cuenta) {
        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(query);
            System.out.println(query.equals(Agregar));
            if (query.equals(Agregar)) {
                ps.setInt(1, cuenta.getUsuario().getIdUsuario()); 
                ps.setDate(2, java.sql.Date.valueOf(cuenta.getFechaCreacion()));
                ps.setInt(3, cuenta.getTipoCuenta().getIdTipoCuenta());
                ps.setString(4, cuenta.getCbu());
                ps.setBigDecimal(5, cuenta.getSaldo());
                ps.setString(6, "Activa");
                
            } else if (query.equals(Modificar)) {
                ps.setDate(1, java.sql.Date.valueOf(cuenta.getFechaCreacion()));
                ps.setInt(2, cuenta.getTipoCuenta().getIdTipoCuenta());
                ps.setBigDecimal(3, cuenta.getSaldo());
                ps.setInt(4, cuenta.getNroCuenta()); // WHERE clause
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }

    private Cuenta valoresCuenta(ResultSet rs) {
        Cuenta cuenta = new Cuenta();
        daoTipoCuenta dtp=new daoTipoCuenta();
        try {
            cuenta.setNroCuenta(rs.getInt("NroCuenta"));
            daoUsuario du=new daoUsuario();
            cuenta.setUsuario(du.BuscarIdusuario(rs.getInt("IdUsuario")));
            cuenta.setFechaCreacion(rs.getDate("FechaCreacion").toLocalDate());
            cuenta.setTipoCuenta(dtp.buscarXID(rs.getInt("IdTipoCuenta")));
            cuenta.setCbu(rs.getString("Cbu"));
            cuenta.setSaldo(rs.getBigDecimal("Saldo"));
            cuenta.setEstado(rs.getString("Estado"));
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return cuenta;
    }
    public boolean Eliminar(int nroCuenta) {
    	Connection cn = null;
    	PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(Eliminar);
            ps.setInt(1, nroCuenta);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo elimianr la cuenta.");
        }
        finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    
    }
	public boolean EliminarCuentas(int idUsuario) {
    	Connection cn = null;
    	PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(EliminarTodasCuentas);
            ps.setInt(1, idUsuario);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo elimianr la cuenta.");
        }
        finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }
    public boolean Modificar(Cuenta cuenta) {
    	Connection cn = null;
    	PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = valoresQuery(cn, Modificar, cuenta);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo modificar la cuenta.");
        }
        finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }
 
    public ArrayList<Cuenta> ListarTodo() {
        ArrayList<Cuenta> listaCuentas = new ArrayList<>();
        Connection cn = null;
    	Statement st = null;
    	ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            st = cn.createStatement();
            rs = st.executeQuery(ListarTodo);
            while (rs.next()) {
                Cuenta cuenta = valoresCuenta(rs);
                listaCuentas.add(cuenta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudieron listar las cuentas.");
        }
        finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return listaCuentas;
    }
    public ArrayList<Cuenta> ListarxUsuario(int Id) {
        ArrayList<Cuenta> listaCuentas = new ArrayList<>();
        Connection cn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(ListarxUsuario);
            ps.setInt(1, Id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cuenta cuenta = valoresCuenta(rs);
                listaCuentas.add(cuenta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudieron listar las cuentas.");
        }
        finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return listaCuentas;
    }

    public boolean existe(int nroCuenta) {
    	Connection cn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(existe);
            ps.setInt(1, nroCuenta);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }

    public int maximoCuentas(int id) {
		 Connection cn = null;
	    	PreparedStatement ps = null;
	    	ResultSet rs = null;
		    try {
		       cn = Conexion.getConexion().getSQLConnection();
		        ps = cn.prepareStatement(maximoCuentas);
		        ps.setInt(1, id);
		        ps.setString(2, "Activa");
		        rs = ps.executeQuery();
		        if (rs.next()) {
		            return rs.getInt("Cantidad");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println("Error");
		    }
		    finally {
	            try { if (rs != null) rs.close(); } catch (Exception e) {}
	            try { if (ps != null) ps.close(); } catch (Exception e) {}
	            try { if (cn != null) cn.close(); } catch (Exception e) {}
	        }
		    return -1;
	}	
    	
    	
    
	@Override
	public Cuenta BuscarPorNro(int nroCuenta) {
		 Cuenta cue = null;
		 Connection cn = null;
	    	PreparedStatement ps = null;
	    	ResultSet rs = null;
		    try {
		       cn = Conexion.getConexion().getSQLConnection();
		        ps = cn.prepareStatement(BuscarPorNro);
		        ps.setInt(1, nroCuenta);
		        rs = ps.executeQuery();
		        if (rs.next()) {
		            cue = valoresCuenta(rs); 
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println("Error al buscar la cuenta por número.");
		    }
		    finally {
	            try { if (rs != null) rs.close(); } catch (Exception e) {}
	            try { if (ps != null) ps.close(); } catch (Exception e) {}
	            try { if (cn != null) cn.close(); } catch (Exception e) {}
	        }
		    return cue;
	}
	public boolean actualizarSaldo(int nroCuenta, BigDecimal monto, Connection conn) {
	    String sql = "UPDATE Cuentas SET Saldo = Saldo + ? WHERE NroCuenta = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setBigDecimal(1, monto);
	        stmt.setInt(2, nroCuenta);
	        return stmt.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public int obtenerNroCuentaPorIdUsuario(int idUsuario, Connection conn) throws SQLException {
	    String sql = "SELECT NroCuenta FROM Cuentas WHERE IdUsuario = ? AND (Estado = 'Activa' or Estado = 'Activo') LIMIT 1";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idUsuario);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("NroCuenta");
	        }
	    }
	    System.out.println("No se encontró cuenta activa para usuario " + idUsuario);
	    return -1;
	}
	
	@Override
	public int contarCuentasCreadasEnRango(Date desde, Date hasta) {
        int cantidad = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConexion().getSQLConnection();
            String sql = "SELECT COUNT(*) AS totalCuentas FROM Cuentas WHERE FechaCreacion BETWEEN ? AND ?";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, new java.sql.Timestamp(desde.getTime())); // CAMBIO CLAVE
            stmt.setTimestamp(2, new java.sql.Timestamp(hasta.getTime())); // CAMBIO CLAVE
            rs = stmt.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt("totalCuentas");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return cantidad;
    }

    @Override
    public BigDecimal obtenerPromedioSaldoInicialEnRango(Date desde, Date hasta) {
        BigDecimal promedio = BigDecimal.ZERO;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConexion().getSQLConnection();
            String sql = "SELECT AVG(Saldo) AS promedioSaldo FROM Cuentas WHERE FechaCreacion BETWEEN ? AND ?";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, new java.sql.Timestamp(desde.getTime())); 
            stmt.setTimestamp(2, new java.sql.Timestamp(hasta.getTime())); 
            rs = stmt.executeQuery();
            if (rs.next()) {
                promedio = rs.getBigDecimal("promedioSaldo");
                if (promedio == null) promedio = BigDecimal.ZERO; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return promedio;
    }

    @Override
    public String obtenerTipoCuentaMasCreadaEnRango(Date desde, Date hasta) {
        String tipoMasCreado = "N/A";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConexion().getSQLConnection();
            String sql = """
                    SELECT tc.Descripcion AS TipoCuenta, COUNT(c.IdTipoCuenta) AS Cantidad
                    FROM Cuentas c
                    INNER JOIN tipocuenta tc ON c.IdTipoCuenta = tc.IdTipoCuenta  -- Corregido el nombre de la tabla
                    WHERE c.FechaCreacion BETWEEN ? AND ?
                    GROUP BY tc.Descripcion
                    ORDER BY Cantidad DESC
                    LIMIT 1
                    """; 
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, new java.sql.Timestamp(desde.getTime())); 
            stmt.setTimestamp(2, new java.sql.Timestamp(hasta.getTime())); 

            System.out.println("DEBUG DAO: Ejecutando consulta para tipo de cuenta más creado. Fechas: " + desde + " a " + hasta);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                tipoMasCreado = rs.getString("TipoCuenta");
                System.out.println("DEBUG DAO: Tipo de cuenta más creado encontrado: " + tipoMasCreado);
            } else {
                System.out.println("DEBUG DAO: No se encontró tipo de cuenta más creado en el rango especificado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR DAO: Error al obtener el tipo de cuenta más creado: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tipoMasCreado;
    }

    @Override
    public List<Cuenta> obtenerCuentasCreadasEnRango(Date desde, Date hasta) {
        List<Cuenta> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConexion().getSQLConnection();
            String sql = """
                SELECT c.*, tc.Descripcion AS TipoCuentaDesc, u.IdUsuario, p.Dni, p.Nombre, p.Apellido
                FROM Cuentas c
                INNER JOIN tipocuenta tc ON c.IdTipoCuenta = tc.IdTipoCuenta
                INNER JOIN usuarios u ON c.IdUsuario = u.IdUsuario
                INNER JOIN persona p ON u.Dni = p.Dni
                WHERE c.FechaCreacion BETWEEN ? AND ?
                ORDER BY c.FechaCreacion DESC
                """; 
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, new java.sql.Timestamp(desde.getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(hasta.getTime()));

            System.out.println("DEBUG DAO: Ejecutando obtenerCuentasCreadasEnRango. SQL: " + sql);
            System.out.println("DEBUG DAO: Fechas para obtenerCuentasCreadasEnRango: " + desde + " a " + hasta);

            rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
                Cuenta c = new Cuenta();
                c.setNroCuenta(rs.getInt("NroCuenta"));
                c.setCbu(rs.getString("Cbu"));
                c.setFechaCreacion(rs.getDate("FechaCreacion").toLocalDate()); 
                c.setSaldo(rs.getBigDecimal("Saldo"));
                c.setEstado(rs.getString("Estado")); 

                TipoCuenta tc = new TipoCuenta();
                tc.setIdTipoCuenta(rs.getInt("IdTipoCuenta"));
                tc.setDescripcion(rs.getString("TipoCuentaDesc"));
                c.setTipoCuenta(tc);

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("IdUsuario"));
                Persona p = new Persona();
                p.setDni(rs.getString("Dni"));
                p.setNombre(rs.getString("Nombre"));
                p.setApellido(rs.getString("Apellido"));
                u.setPersona(p);
                c.setUsuario(u);

                lista.add(c);
            }
            System.out.println("DEBUG DAO: Cantidad de cuentas encontradas en obtenerCuentasCreadasEnRango: " + count);
        } catch (SQLException e) { 
            e.printStackTrace();
            System.err.println("ERROR DAO: SQL Exception en obtenerCuentasCreadasEnRango: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR DAO: Excepción general en obtenerCuentasCreadasEnRango: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
}
