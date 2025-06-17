package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Interfaces.inPersona;
import Entidades.Persona;
import Interfaces.Conexion;

public class daoPersonas implements inPersona{
		private final String  Agregar="insert into Persona(CorreoElectronico, Cuil, Nombre, Apellido, Sexo, Nacionalidad, fechaNacimiento, Direccion, Localidad, Provincia,Dni) values(?,?,?,?,?,?,?,?,?,?,?);";
		private final String Eliminar="Delete From Persona where Dni=?;";
		private final String Modificar="Update Personas set CorreoElectronico=?, Cuil=?, Nombre=?, Apellido=?, Sexo=?, Nacionalidad=?, fechaNacimiento=?, Direccion=?, Localidad=?, Provincia=? where Dni=?; ";
		private final String ListarTodo="Select * from Persona;";
		private final String Existe="Select * from Persona where dni=?;";
		public boolean Agregar(Persona persona) {
			try {
				Connection  cn=Conexion.getConexion().getSQLConnection();
				//PreparedStatement st=valoresxParametro(cn,Agregar,persona);
				/*if(st.executeUpdate()>0){// lo tengo que cambiar a un procedimiento almacenado
				cn.commit();//guarda los cambios
				return true;
				}*/
				PreparedStatement cs= valoresQuery(cn,Agregar,persona);
				if(cs.executeUpdate()>0) {
				cn.commit();
				return true;
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("No se pudo conectar");
			}
			return false;
		}
		private PreparedStatement valoresQuery(Connection cn,String query,Persona persona) {
			PreparedStatement cs=null;
			try {
		    cs=cn.prepareCall(query);
		    cs.setString(1, persona.getCorreoElectronico());
		    cs.setString(2, persona.getCuil());
		    cs.setString(3, persona.getNombre());
		    cs.setString(4, persona.getApellido());
		    cs.setString(5, persona.getSexo());
		    cs.setString(6, persona.getNacionalidad());
		    cs.setDate(7, java.sql.Date.valueOf(persona.getFechaNacimiento())); 
		    cs.setString(8, persona.getDireccion());
		    cs.setString(9, persona.getLocalidad());
		    cs.setString(10, persona.getProvincia());
		    cs.setString(11, persona.getDni());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			return cs;
		}
		private Persona valoresPersona(ResultSet rs) {
			Persona persona=new Persona();
			try {
			persona.setCorreoElectronico(rs.getString("CorreoElectronico"));
			persona.setCuil(rs.getString("Cuil"));
			persona.setNombre(rs.getString("Nombre"));
			persona.setApellido(rs.getString("Apellido"));
			persona.setSexo(rs.getString("Sexo"));
			persona.setNacionalidad(rs.getString("Nacionalidad"));
			persona.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate()); // suponiendo que usás LocalDate
			persona.setDireccion(rs.getString("Direccion"));
			persona.setLocalidad(rs.getString("Localidad"));
			persona.setProvincia(rs.getString("Provincia"));
			persona.setDni(rs.getString("Dni"));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return persona;
		}
		public boolean Eliminar(String dni) {
			try{
				Connection  cn=Conexion.getConexion().getSQLConnection();
				PreparedStatement ps=cn.prepareStatement(Eliminar);
				ps.setString(1, dni);
				if(ps.executeUpdate()>0) {
					cn.commit();
					return true;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		public boolean Modificar(Persona persona) {
			try {
				Connection  cn=Conexion.getConexion().getSQLConnection();
			PreparedStatement cs=valoresQuery(cn, Modificar, persona);
			if(cs.executeUpdate()>0) {
				cn.commit();
				return true;
			}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		public ArrayList<Persona> ListarTodo() {
			ArrayList<Persona> ListaPersona=new ArrayList<Persona>();
			try {
				Connection cn= Conexion.getConexion().getSQLConnection();
				Statement st=cn.createStatement();
				ResultSet rs=st.executeQuery(ListarTodo);
				while(rs.next()) {
					Persona persona=valoresPersona(rs);
					ListaPersona.add(persona);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return ListaPersona;
		}
		public boolean existe(String dni) {
			try {
				  Connection cn= Conexion.getConexion().getSQLConnection();
				  PreparedStatement ps=cn.prepareStatement(Existe);
				  ps.setString(1, dni);
				  ResultSet rs=ps.executeQuery();
				  if(rs.next()) {
					  return true;
				  }
			}
				  catch(Exception e) {			  
					  e.printStackTrace();
				  }
			return false;
			}

		public Persona Buscardni(String dni) {
			try {
				  Connection cn= Conexion.getConexion().getSQLConnection();
				  PreparedStatement ps=cn.prepareStatement(Existe);
				  ps.setString(1, dni);
				  ResultSet rs=ps.executeQuery();
				  if(rs.next()) {
					  Persona persona=valoresPersona(rs);
					  return persona;
				  }
			}
				  catch(Exception e) {			  
					  e.printStackTrace();
				  }
			return new Persona();
			}

}

