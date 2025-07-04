package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.TipoCuenta;
import Entidades.Usuario;
import Interfaces.Conexion;
import Interfaces.inTipoCuenta;

public class daoTipoCuenta implements inTipoCuenta{
private String buscarxDescripcion="Select * from tipoCuenta where descripcion=?";
private String buscarxID="Select * from tipoCuenta where idTipoCuenta=?";
private String listarTodo="select * from tipoCuenta";
@Override
public TipoCuenta buscarXDescripcion(String Descripcion) {
        Connection cn = null;
        PreparedStatement cs = null;
        ResultSet rs=null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            cs=cn.prepareStatement(buscarxDescripcion);
            cs.setString(1, Descripcion);
            rs=cs.executeQuery();
            TipoCuenta tp=new TipoCuenta();
            if (rs.next()) {
                tp.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
                tp.setDescripcion(Descripcion);
                return tp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo conectar");
        } finally {
            try { if (cs != null) cs.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
            try { if (rs != null) rs.close(); } catch (Exception e) {}
        }
        return null;
    }
@Override

public TipoCuenta buscarXID(int Id) {
        Connection cn = null;
        PreparedStatement cs = null;
        ResultSet rs=null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            cs=cn.prepareStatement(buscarxID);
            cs.setInt(1, Id);
            rs=cs.executeQuery();
            TipoCuenta tp=new TipoCuenta();
            if (rs.next()) {
                tp.setIdTipoCuenta(Id);
                tp.setDescripcion(rs.getString("Descripcion"));
                return tp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo conectar");
        } finally {
            try { if (cs != null) cs.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
            try { if (rs != null) rs.close(); } catch (Exception e) {}
        }
        return null;
    }
public ArrayList<TipoCuenta> ListarTodo() {
    ArrayList<TipoCuenta> listaTipoCuenta = new ArrayList<>();
    Connection cn = null;
    Statement st = null;
    ResultSet rs = null;
    try {
        cn = Conexion.getConexion().getSQLConnection();
        st = cn.createStatement();
        rs = st.executeQuery(listarTodo);
        while (rs.next()) {
            TipoCuenta tipCuenta= new TipoCuenta();
            tipCuenta.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
            tipCuenta.setDescripcion(rs.getString("Descripcion"));
            listaTipoCuenta.add(tipCuenta);
        }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("No se pudieron listar los usuarios.");
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (st != null) st.close(); } catch (Exception e) {}
        try { if (cn != null) cn.close(); } catch (Exception e) {}
    }
    return listaTipoCuenta;
}
}
