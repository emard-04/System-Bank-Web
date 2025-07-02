package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import Entidades.Movimiento;
import Entidades.Persona;
import Entidades.TipoMovimiento;
import Entidades.Usuario;
import Interfaces.Conexion;
import Interfaces.inMovimiento;
import Interfaces.inPersona;

	public class daoMovimiento implements inMovimiento {
	    private final String Agregar = "insert into Movimientos(IdUsuario, IdTipoMovimiento, CuentaEmisor,CuentaReceptor, Detalle, Importe, Fecha) values(?,?,?,?,?,?,?);";
	    private final String ListarxCuenta="select * from movimientos where idUsuario=? and cuentaEmisor=?;";
	    public ArrayList<Movimiento> filtrar(Movimiento mov, String nombre, String operador, LocalDate desde, LocalDate hasta) {
	        Connection cn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            cn = Conexion.getConexion().getSQLConnection();
	            StringBuilder query = new StringBuilder();
	            query.append("SELECT IdMovimiento, movimientos.IdUsuario, IdTipoMovimiento, CuentaEmisor, CuentaReceptor, Detalle, Importe, Fecha ");
	            query.append("FROM movimientos ");
	            query.append("INNER JOIN\r\n"
	            		+ "    cuentas ON movimientos.CuentaReceptor = cuentas.NroCuenta\r\n"
	            		+ "    inner join Usuarios on usuarios.IdUsuario=cuentas.IdUsuario ");
	            query.append("WHERE 1=1 ");
	            query.append("AND movimientos.idUsuario = ? ");
	            query.append("AND movimientos.CuentaEmisor = ? ");
	            query.append("AND Usuarios.nombreusuario LIKE ? ");

	            if (desde != null && hasta != null) {
	                query.append("AND fecha BETWEEN ? AND ? ");
	            }

	            if (!operador.isEmpty()) {
	                if (operador.equals(">")) {
	                    query.append("AND importe > 0 ");
	                } else {
	                    query.append("AND importe < 0 ");
	                }
	            }

	            ps = cn.prepareStatement(query.toString());

	            // Parámetros en el orden correcto
	            int paramIndex = 1;
	            ps.setInt(paramIndex++, mov.getUsuario().getIdUsuario());
	            ps.setInt(paramIndex++, mov.getCuentaEmisor().getNroCuenta());
	            ps.setString(paramIndex++, "%" + nombre + "%");

	            if (desde != null && hasta != null) {
	                ps.setDate(paramIndex++, java.sql.Date.valueOf(desde));
	                ps.setDate(paramIndex++, java.sql.Date.valueOf(hasta));
	            }

	            rs = ps.executeQuery();
	            ArrayList<Movimiento> listaFiltro = new ArrayList<Movimiento>();
	            while (rs.next()) {
	                listaFiltro.add(valoresMovimiento(rs));
	            }

	            return listaFiltro;

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
	     
	}
