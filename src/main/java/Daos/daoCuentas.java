package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.math.BigDecimal; // Import for BigDecimal

//import Interfaces.inCuenta; // Assuming you will create this interface
import Entidades.Cuenta;
import Entidades.Usuario;    // Necesario para el objeto Usuario dentro de Cuenta
import Entidades.TipoCuenta; // Necesario para el objeto TipoCuenta dentro de Cuenta
import Interfaces.Conexion;
import Interfaces.inCuentas;

public class daoCuentas implements inCuentas{
    // SQL Queries adaptadas para la tabla Cuentas
    private final String Agregar = "INSERT INTO Cuentas(IdUsuario, FechaCreacion, IdtipoCuenta, Cbu, Saldo) VALUES(?,?,?,?,?);";
    private final String Eliminar = "DELETE FROM Cuentas WHERE NroCuenta=?;";
    private final String Modificar = "UPDATE Cuentas SET IdUsuario=?, FechaCreacion=?, IdtipoCuenta=?, Cbu=?, Saldo=? WHERE NroCuenta=?;";
    private final String ListarTodo = "SELECT NroCuenta, IdUsuario, FechaCreacion, IdtipoCuenta, Cbu, Saldo FROM Cuentas;";
    private final String existe = "SELECT * FROM Cuentas WHERE NroCuenta=?;";
    private final String BuscarPorNro = "SELECT NroCuenta, IdUsuario, FechaCreacion, IdtipoCuenta, Cbu, Saldo FROM Cuentas WHERE NroCuenta=?;";
    
    public  daoCuentas() {
		// TODO Auto-generated constructor stub
	}

    public boolean Agregar(Cuenta cuenta) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = valoresQuery(cn, Agregar, cuenta);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo agregar la cuenta.");
        }
        return false;
    }

    private PreparedStatement valoresQuery(Connection cn, String query, Cuenta cuenta) {
        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(query);
            if (query.equals(Agregar)) {
                ps.setInt(1, cuenta.getUsuario().getIdUsuario()); 
                ps.setDate(2, java.sql.Date.valueOf(cuenta.getFechaCreacion()));
                ps.setInt(3, cuenta.getTipoCuenta().getIdTipoCuenta());
                ps.setString(4, cuenta.getCbu());
                ps.setBigDecimal(5, cuenta.getSaldo());
                
            } else if (query.equals(Modificar)) {
                ps.setInt(1, cuenta.getUsuario().getIdUsuario());
                ps.setDate(2, java.sql.Date.valueOf(cuenta.getFechaCreacion()));
                ps.setInt(3, cuenta.getTipoCuenta().getIdTipoCuenta());
                ps.setString(4, cuenta.getCbu());
                ps.setBigDecimal(5, cuenta.getSaldo());
                ps.setInt(6, cuenta.getNroCuenta()); // WHERE clause
                
            }else {
            ps.setInt(1, cuenta.getNroCuenta()); 
            }// WHERE clause
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }

    private Cuenta valoresCuenta(ResultSet rs) {
        Cuenta cuenta = new Cuenta();
        try {
            cuenta.setNroCuenta(rs.getInt("NroCuenta"));
            daoUsuario du=new daoUsuario();
            cuenta.setUsuario(du.BuscarIdusuario(rs.getInt("IdUsuario")));
            cuenta.setFechaCreacion(rs.getDate("FechaCreacion").toLocalDate());
            // Para el objeto TipoCuenta dentro de Cuenta:
            // Similar al Usuario, solo seteamos el IdTipoCuenta por ahora.
            // Si necesitas la descripción del tipo de cuenta, necesitarías un daoTipoCuenta
            // o un JOIN en la consulta SQL.
            TipoCuenta tipoCuenta = new TipoCuenta();
            tipoCuenta.setIdTipoCuenta(rs.getInt("IdtipoCuenta"));
            cuenta.setTipoCuenta(tipoCuenta);

            cuenta.setCbu(rs.getString("Cbu"));
            cuenta.setSaldo(rs.getBigDecimal("Saldo"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cuenta;
    }

    public boolean Eliminar(int nroCuenta) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = cn.prepareStatement("DELETE FROM Cuentas WHERE NroCuenta=?;");
            ps.setInt(1, nroCuenta);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo eliminar la cuenta.");
        }
        return false;
    }

    public boolean Modificar(Cuenta cuenta) {
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = valoresQuery(cn, Modificar, cuenta);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo modificar la cuenta.");
        }
        return false;
    }

    public ArrayList<Cuenta> ListarTodo() {
        ArrayList<Cuenta> listaCuentas = new ArrayList<>();
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ListarTodo);
            while (rs.next()) {
                Cuenta cuenta = valoresCuenta(rs);
                listaCuentas.add(cuenta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudieron listar las cuentas.");
        }
        return listaCuentas;
    }

    public boolean existe(int nroCuenta) {
    	try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = cn.prepareStatement("SELECT 1 FROM Cuentas WHERE NroCuenta=?;");
            ps.setInt(1, nroCuenta);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


	@Override
	public Cuenta BuscarPorNro(int nroCuenta) {
		Cuenta cue = new Cuenta();
        try {
            Connection cn = Conexion.getConexion().getSQLConnection();
            PreparedStatement ps = cn.prepareStatement("SELECT NroCuenta, IdUsuario, FechaCreacion, IdtipoCuenta, Cbu, Saldo FROM Cuentas WHERE NroCuenta=?;");
            ps.setInt(1, nroCuenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cue = valoresCuenta(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al buscar la cuenta por número.");
        }
        return cue;
	}
}