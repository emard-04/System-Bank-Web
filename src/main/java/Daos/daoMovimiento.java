package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

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
	    //rivate final String listaxParametro="select * from movimientos ";
	    /*public void filtrar() {
	    	String nombre="ju";
	    	LocalDate local=LocalDate.now();
	    	String operacion=">";
	    	if(!nombre.isEmpty()) {
	    	listaxParametro += "inner join Usuarios on Usuarios.idUsuario=movimientos.idUsuario where Usuarios.nombreusuario like ? and idUsuario=? and cuentaEmisor=?";}
	    	if( local!=null&&!nombre.isEmpty()) {
	    		listaxParametro += "and  fecha between  ? and ? ";
	    	}
	    	if(!operacion.isEmpty()) {
	    		listaxParametro+="and importe > 0";
	    	}
	    	
	    }*/
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
