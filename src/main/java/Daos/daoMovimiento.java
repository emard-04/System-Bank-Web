package Daos;

import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import Entidades.*;
import Entidades.Movimiento;
import Interfaces.Conexion;
import Interfaces.inMovimiento;


	public class daoMovimiento implements inMovimiento {
	    private final String Agregar = "insert into Movimientos(IdUsuario, IdTipoMovimiento, CuentaEmisor,CuentaReceptor, Detalle, Importe, Fecha) values(?,?,?,?,?,?,?);";
	    private final String Eliminar = "UPDATE Movimientos SET Estado = 'Inactivo' WHERE cuentaEmisor=? ";//EliminarxCuenta
	    private final String EliminarxUsuario = "UPDATE Movimientos SET Estado = 'Inactivo' WHERE idUsuario = ?";//EliminarxUsuario
	    private final String ListarxCuenta="select * from movimientos where idUsuario=? and cuentaEmisor=?;";
	    public ArrayList<Movimiento> Filtrar(int idUsuario, int cuentaEmisor, String nombreUsuario, String condicionesExtras, ArrayList<Object> parametrosExtras) {
	        Connection cn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            cn = Conexion.getConexion().getSQLConnection();
	            StringBuilder query = new StringBuilder();
	            query.append("SELECT IdMovimiento, movimientos.IdUsuario, IdTipoMovimiento, CuentaEmisor, CuentaReceptor, Detalle, Importe, Fecha ");
	            query.append("FROM movimientos ");
	            query.append("INNER JOIN cuentas ON movimientos.CuentaReceptor = cuentas.NroCuenta ");
	            query.append("INNER JOIN Usuarios ON usuarios.IdUsuario = cuentas.IdUsuario ");
	            query.append("WHERE movimientos.idUsuario = ? ");
	            query.append("AND movimientos.CuentaEmisor = ? ");
	            query.append("AND Usuarios.nombreusuario LIKE ? ");
	            query.append(condicionesExtras);  // Aquí se agregan condiciones dinámicas

	            ps = cn.prepareStatement(query.toString());

	            int paramIndex = 1;
	            ps.setInt(paramIndex++, idUsuario);
	            ps.setInt(paramIndex++, cuentaEmisor);
	            ps.setString(paramIndex++, "%" + nombreUsuario + "%");

	            for (Object param : parametrosExtras) {
	            	 if (param instanceof LocalDate) {
	                    ps.setDate(paramIndex++, java.sql.Date.valueOf((LocalDate) param));
	                } else if (param instanceof Integer) {
	                    ps.setInt(paramIndex++, (Integer) param);
	                } else if (param instanceof String) {
	                    ps.setString(paramIndex++, (String) param);
	                }
	            }

	            ArrayList<Movimiento> lista = new ArrayList<>();
	            rs = ps.executeQuery();
	            while (rs.next()) {
	                lista.add(valoresMovimiento(rs));
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
	    public boolean Agregar(Movimiento mov) {
	        Connection cn = null;
	        PreparedStatement cs = null;
	        try {
	            cn = Conexion.getConexion().getSQLConnection();
	            cs = valoresQuery(cn, Agregar, mov);
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
	    public boolean EliminarxCuenta(int idCuenta) {
	    	Connection cn = null;
	    	PreparedStatement ps = null;
	        try {
	            cn = Conexion.getConexion().getSQLConnection();
	            ps = cn.prepareStatement(Eliminar);
	            ps.setInt(1, idCuenta);
	            if (ps.executeUpdate() > 0) {
	                cn.commit();
	                return true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("No se pudo elimianr movimiento.");
	        }
	        finally {
	            try { if (ps != null) ps.close(); } catch (Exception e) {}
	            try { if (cn != null) cn.close(); } catch (Exception e) {}
	        }
	        return false;
	    }
	    public boolean EliminarMovimientos(int idUsuario) {
	    	Connection cn = null;
	    	PreparedStatement ps = null;
	        try {
	            cn = Conexion.getConexion().getSQLConnection();
	            ps = cn.prepareStatement(EliminarxUsuario);
	            ps.setInt(1, idUsuario);
	            if (ps.executeUpdate() > 0) {
	                cn.commit();
	                return true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("No se pudo elimianr movimiento.");
	        }
	        finally {
	            try { if (ps != null) ps.close(); } catch (Exception e) {}
	            try { if (cn != null) cn.close(); } catch (Exception e) {}
	        }
	        return false;
	    }
	    private PreparedStatement valoresQuery(Connection cn, String query, Movimiento mov) {
	        PreparedStatement cs = null;
	        try {
	            cs = cn.prepareStatement(query);
	            cs.setInt(1, mov.getUsuario().getIdUsuario());
	            cs.setInt(2, mov.getTipoMovimiento().getIdTipoMovimiento());
	            cs.setInt(3, mov.getCuentaEmisor().getNroCuenta());
	            cs.setInt(4, mov.getCuentaReceptor().getNroCuenta());
	            cs.setString(5, mov.getDetalle());
	            cs.setBigDecimal(6, mov.getImporte());
	            cs.setDate(7, java.sql.Date.valueOf(mov.getFecha()));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return cs;
	    }
	    public ArrayList<Movimiento> Listarxcuentas(Movimiento mov) {
	        Connection cn = null;
	        PreparedStatement cs = null;
	        ArrayList<Movimiento> listaMovimiento=new ArrayList<Movimiento>();
	        ResultSet rs= null;
	        try {
	            cn = Conexion.getConexion().getSQLConnection();
	            cs = cn.prepareStatement(ListarxCuenta);
	            cs.setInt(1, mov.getUsuario().getIdUsuario());
	            cs.setInt(2, mov.getCuentaEmisor().getNroCuenta());
	            rs=cs.executeQuery();
	            while (rs.next()) {
	            	
	            	listaMovimiento.add(valoresMovimiento(rs));
	            }
	            return listaMovimiento;
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("No se pudo conectar");
	        } finally {
	            try { if (cs != null) cs.close(); } catch (Exception e) {}
	            try { if (cn != null) cn.close(); } catch (Exception e) {}
	        }
	        return null;
	    }
	    private Movimiento valoresMovimiento(ResultSet rs){
	    Movimiento mov= new Movimiento();
	    daoUsuario du= new daoUsuario();
	    daoTipoMovimiento dtm= new daoTipoMovimiento();
	    daoCuentas dc= new daoCuentas();
	    try {
	    	mov.setIdMovimiento(rs.getInt("IdMovimiento"));
	    	mov.setUsuario(du.BuscarIdusuario(rs.getInt("IdUsuario")));
	    	mov.setTipoMovimiento(dtm.buscarXID(rs.getInt("IdTipoMovimiento")));
	    	mov.setCuentaEmisor(dc.BuscarPorNro(rs.getInt("CuentaEmisor")));
	    	mov.setCuentaReceptor(dc.BuscarPorNro(rs.getInt("CuentaReceptor")));
	    	mov.setDetalle(rs.getString("Detalle"));
	    	mov.setImporte(rs.getBigDecimal("Importe"));
	    	mov.setFecha(rs.getDate("fecha").toLocalDate());
	    }catch(Exception e) {}
	    return mov;
	    }
	    @Override
	    public int contarMovimientos(Movimiento mov) {
	        int total = 0;
	        String sql = "SELECT COUNT(*) FROM movimientos WHERE nroCuentaEmisor = ?";
	        
	        try (Connection conn = Conexion.getConexion().getSQLConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setInt(1, mov.getCuentaEmisor().getNroCuenta());

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    total = rs.getInt(1);
	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return total;
	    }
	    @Override
	    public List<Movimiento> ListarxcuentasPaginado(Movimiento mov, int offset, int limite) {
	        List<Movimiento> lista = new ArrayList<>();
	        String sql = "SELECT * FROM movimientos WHERE nroCuentaEmisor = ? ORDER BY fecha DESC LIMIT ? OFFSET ?";

	        try (Connection conn = Conexion.getConexion().getSQLConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, mov.getCuentaEmisor().getNroCuenta());
	            ps.setInt(2, limite);
	            ps.setInt(3, offset);

	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Movimiento m = new Movimiento();

	                    // Asegurate de completar estos datos según tu entidad
	                    m.setIdMovimiento(rs.getInt("idMovimiento"));
	                    m.setDetalle(rs.getString("detalle"));
	                    m.setFecha(rs.getDate("fecha").toLocalDate());
	                    m.setImporte(rs.getBigDecimal("importe"));
	                    
	                    // Si necesitas cargar más datos (tipoMovimiento, usuario, cuentaReceptor, etc.)
	                    // deberás hacer joins o consultas adicionales.

	                    Cuenta cuentaEmisor = new Cuenta();
	                    cuentaEmisor.setNroCuenta(rs.getInt("nroCuentaEmisor"));
	                    m.setCuentaEmisor(cuentaEmisor);

	                    lista.add(m);
	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return lista;
	    }
	     
	}
