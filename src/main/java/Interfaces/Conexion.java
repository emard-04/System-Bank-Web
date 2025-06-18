package Interfaces;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexion {
	public static Conexion instancia;
	private Connection connection;
	
	private Conexion()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
	this.connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/BancoParcial","root","root");
	this.connection.setAutoCommit(false);
	}
	catch(Exception e) {
	e.printStackTrace();
	}
}
	public static Conexion getConexion() {
		if(instancia==null) {
			instancia=new Conexion();
		}
		return instancia;
	}
	public Connection getSQLConnection() {
		return this.connection;
	}
}
