package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.TipoCuenta;
import Entidades.TipoMovimiento;
import Interfaces.Conexion;
import Interfaces.inTipoMovimiento;

public class daoTipoMovimiento implements inTipoMovimiento {
	private String buscarxDescripcion = "select * from tipomovimiento where descripcion=?";
	private String buscarxId = "select * from tipomovimiento where idTipoMovimiento=?";
	private String listarTodo = "select * from TipoMovimiento";

	public TipoMovimiento buscarXDescripcion(String Descripcion) {
		Connection cn = null;
		PreparedStatement cs = null;
		ResultSet rs = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			cs = cn.prepareStatement(buscarxDescripcion);
			cs.setString(1, Descripcion);
			rs = cs.executeQuery();
			TipoMovimiento tm = new TipoMovimiento();
			if (rs.next()) {
				tm.setIdTipoMovimiento(rs.getInt("idTipoMovimiento"));
				tm.setDescripcion(Descripcion);
				return tm;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se pudo conectar");
		} finally {
			try {
				if (cs != null)
					cs.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	@Override
	public TipoMovimiento buscarXID(int Id) {
		Connection cn = null;
		PreparedStatement cs = null;
		ResultSet rs = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			cs = cn.prepareStatement(buscarxId);
			cs.setInt(1, Id);
			rs = cs.executeQuery();
			TipoMovimiento tm = new TipoMovimiento();
			if (rs.next()) {
				tm.setIdTipoMovimiento(Id);
				tm.setDescripcion(rs.getString("Descripcion"));
				return tm;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se pudo conectar");
		} finally {
			try {
				if (cs != null)
					cs.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	public ArrayList<TipoMovimiento> listarTodo() {
		ArrayList<TipoMovimiento> listaTipoMovimiento = new ArrayList<>();
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			st = cn.createStatement();
			rs = st.executeQuery(listarTodo);
			while (rs.next()) {
				TipoMovimiento tipoMovimiento = new TipoMovimiento();
				tipoMovimiento.setIdTipoMovimiento(rs.getInt("idTipoMovimiento"));
				tipoMovimiento.setDescripcion(rs.getString("Descripcion"));
				listaTipoMovimiento.add(tipoMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se pudieron listar los usuarios.");
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (st != null)
					st.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
		}
		return listaTipoMovimiento;
	}
}
