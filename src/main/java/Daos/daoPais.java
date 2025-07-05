package Daos;

import java.sql.Connection;
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
import Entidades.Pais;
import Entidades.TipoCuenta; // Necesario para el objeto TipoCuenta dentro de Cuenta
import Interfaces.Conexion;
import Interfaces.inCuentas;
import Interfaces.inPais;

import java.util.List;
	public class daoPais implements inPais {

	    private String buscarxNombre = "SELECT * FROM pais WHERE Nombre = ?";
	    private String buscarxID = "SELECT * FROM pais WHERE idPais = ?";
	    private String listarTodo = "SELECT * FROM pais";

	    public Pais buscarXNombre(String nombre) {
	        Connection cn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            cn = Conexion.getConexion().getSQLConnection();
	            ps = cn.prepareStatement(buscarxNombre);
	            ps.setString(1, nombre);
	            rs = ps.executeQuery();
	            Pais p = new Pais();
	            if (rs.next()) {
	                p.setIdPais(rs.getInt("idPais"));
	                p.setNombre(nombre);
	                return p;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("No se pudo conectar");
	        } finally {
	            try { if (ps != null) ps.close(); } catch (Exception e) {}
	            try { if (rs != null) rs.close(); } catch (Exception e) {}
	            try { if (cn != null) cn.close(); } catch (Exception e) {}
	        }
	        return null;
	    }

	    public Pais buscarXID(int id) {
	        Connection cn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            cn = Conexion.getConexion().getSQLConnection();
	            ps = cn.prepareStatement(buscarxID);
	            ps.setInt(1, id);
	            rs = ps.executeQuery();
	            Pais p = new Pais();
	            if (rs.next()) {
	                p.setIdPais(id);
	                p.setNombre(rs.getString("Nombre"));
	                return p;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("No se pudo conectar");
	        } finally {
	            try { if (ps != null) ps.close(); } catch (Exception e) {}
	            try { if (rs != null) rs.close(); } catch (Exception e) {}
	            try { if (cn != null) cn.close(); } catch (Exception e) {}
	        }
	        return null;
	    }

	    public ArrayList<Pais> listarTodo() {
	        ArrayList<Pais> listaPaises = new ArrayList<>();
	        Connection cn = null;
	        Statement st = null;
	        ResultSet rs = null;

	        try {
	            cn = Conexion.getConexion().getSQLConnection();
	            st = cn.createStatement();
	            rs = st.executeQuery(listarTodo);
	            while (rs.next()) {
	                Pais p = new Pais();
	                p.setIdPais(rs.getInt("idPais"));
	                p.setNombre(rs.getString("Nombre"));
	                System.out.println( p.getIdPais()+" pais "+p.getNombre());
	                listaPaises.add(p);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("No se pudieron listar los países.");
	        } finally {
	            try { if (rs != null) rs.close(); } catch (Exception e) {}
	            try { if (st != null) st.close(); } catch (Exception e) {}
	            try { if (cn != null) cn.close(); } catch (Exception e) {}
	        }

	        return listaPaises;
	    }
	}
