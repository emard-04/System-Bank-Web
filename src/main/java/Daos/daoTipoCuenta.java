package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Entidades.TipoCuenta;
import Interfaces.Conexion;
import Interfaces.inTipoCuenta;

public class daoTipoCuenta implements inTipoCuenta{
private String buscarxDescripcion="Select * from tipoCuenta where descripcion=?";
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
                System.out.println(tp);
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
}
