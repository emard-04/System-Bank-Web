package Interfaces;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexion {
	public static Conexion instancia;
	private Connection connection;
	
	private Conexion()
	{
		try
		{
	this.connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/BancoParcial","root","0606");
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
