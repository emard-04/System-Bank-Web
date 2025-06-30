package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Entidades.Movimiento;
import Entidades.Persona;
import Interfaces.Conexion;
import Interfaces.inMovimiento;
import Interfaces.inPersona;

	public class daoMovimiento implements inMovimiento {
	    private final String Agregar = "insert into Movimientos(IdUsuario, IdTipoMovimiento, CuentaEmisor,CuentaReceptor, Detalle, Importe, Fecha) values(?,?,?,?,?,?,?);";
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
	}
