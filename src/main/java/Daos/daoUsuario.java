package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Usuario;
import Entidades.Persona;
import Interfaces.Conexion;
import Interfaces.InUsuario;

public class daoUsuario implements InUsuario {
	private final String Agregar = "INSERT INTO Usuarios( Contraseña, dni, TipoUsuario, NombreUsuario, Estado) VALUES(?,?,?,?,?);";
	private final String Eliminar = "UPDATE Usuarios SET Estado = 'Inactivo' WHERE dni = ?";
	private final String Modificar = "UPDATE Usuarios SET NombreUsuario=?,Contraseña=? WHERE Dni=?;";
	private final String ListarTodo = "SELECT IdUsuario, NombreUsuario, Contraseña, dni, TipoUsuario, Estado FROM Usuarios where TipoUsuario=0 AND Estado = 'Activo';";
	private final String Existe = "SELECT * FROM Usuarios WHERE NombreUsuario=? ;";
	private final String ExisteDni = "SELECT * FROM Usuarios WHERE Dni=?;";
	private final String BuscarIdUsuario = "SELECT * FROM Usuarios WHERE IdUsuario=? AND Estado = 'Activo';";
	private final String Login = "SELECT IdUsuario, NombreUsuario, Contraseña, dni, TipoUsuario, Estado FROM Usuarios WHERE BINARY NombreUsuario=? AND BINARY Contraseña=?;";

	private static daoPersonas dp;

	public boolean Agregar(Usuario usuario) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			ps = valoresQuery(cn, Agregar, usuario);
			if (ps.executeUpdate() > 0) {
				cn.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se pudo agregar el usuario.");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
		}
		return false;
	}

	public ArrayList<Usuario> filtrar(String condicionesExtras, ArrayList<Object> parametrosExtras) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cn = Conexion.getConexion().getSQLConnection();
			StringBuilder query = new StringBuilder();
			query.append("SELECT * ");
			query.append("FROM usuarios ");
			query.append("INNER JOIN persona ON persona.dni = usuarios.dni ");
			query.append("INNER JOIN pais ON persona.idPais = pais.IdPais ");
			query.append("INNER JOIN provincia ON persona.idprovincia = provincia.IdProvincia ");
			query.append("INNER JOIN localidad ON persona.IdLocalidad = localidad.IdLocalidad ");
			query.append("WHERE 1=1 and tipoUsuario=0"); // base para agregar condiciones dinámicas
			query.append(condicionesExtras);

			ps = cn.prepareStatement(query.toString());

			int paramIndex = 1;
			for (Object param : parametrosExtras) {
				if (param instanceof Integer) {
					ps.setInt(paramIndex++, (Integer) param);
				} else if (param instanceof String) {
					ps.setString(paramIndex++, (String) param);
				}
			}

			ArrayList<Usuario> lista = new ArrayList<>();
			rs = ps.executeQuery();
			while (rs.next()) {
				lista.add(valoresUsuario(rs)); // método que mapea el ResultSet a un objeto Usuario
			}
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (cn != null)
					cn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private PreparedStatement valoresQuery(Connection cn, String query, Usuario usuario) {
		PreparedStatement ps = null;
		try {
			ps = cn.prepareStatement(query);
			if (query.equals(Agregar)) {
				ps.setString(1, usuario.getContrasena());
				ps.setString(2, usuario.getPersona().getDni());
				ps.setBoolean(3, usuario.isTipoUsuario());
				ps.setString(4, usuario.getNombreUsuario());
				ps.setString(5, "Activo");
			}
			if (query.equals(Modificar)) {
				ps.setString(1, usuario.getNombreUsuario());
				ps.setString(2, usuario.getContrasena());
				ps.setString(3, usuario.getPersona().getDni());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
	}

	private Usuario valoresUsuario(ResultSet rs) {
		Usuario usuario = new Usuario();
		try {
			usuario.setIdUsuario(rs.getInt("IdUsuario"));
			usuario.setNombreUsuario(rs.getString("NombreUsuario"));
			usuario.setContrasena(rs.getString("Contraseña"));
			dp = new daoPersonas();
			Persona persona = (dp.existeObj(rs.getString("dni")));
			usuario.setPersona(persona);
			usuario.setEstado(rs.getString("Estado"));
			usuario.setTipoUsuario(rs.getInt("TipoUsuario") == 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuario;
	}

	public boolean Eliminar(String dni) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			ps = cn.prepareStatement(Eliminar);
			ps.setString(1, dni);
			if (ps.executeUpdate() > 0) {
				cn.commit();
				return true;
			}
		} catch (Exception e) {
			System.out.println(" No se pudo eliminar el usuario. ");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
		}
		return false;
	}

	public boolean Modificar(Usuario usuario) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			ps = valoresQuery(cn, Modificar, usuario);
			if (ps.executeUpdate() > 0) {
				cn.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se pudo modificar el usuario.");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
		}
		return false;
	}

	public ArrayList<Usuario> ListarTodo() {
		ArrayList<Usuario> listaUsuarios = new ArrayList<>();
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			st = cn.createStatement();
			rs = st.executeQuery(ListarTodo);
			while (rs.next()) {
				Usuario usuario = valoresUsuario(rs);
				listaUsuarios.add(usuario);
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
		return listaUsuarios;
	}

	public boolean existe(String nombreUsuario) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			ps = cn.prepareStatement(Existe);
			ps.setString(1, nombreUsuario);
			rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
		}
		return false;
	}

	public Usuario BuscarDni(String Dni) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cn = Conexion.getConexion().getSQLConnection();
			ps = cn.prepareStatement(ExisteDni);
			ps.setString(1, Dni);
			rs = ps.executeQuery();
			if (rs.next()) {
				return valoresUsuario(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	public Usuario BuscarIdusuario(int id) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			ps = cn.prepareStatement(BuscarIdUsuario);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return valoresUsuario(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
		}
		return new Usuario();
	}

	public Usuario Login(String nombreUsuario, String contrasena) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			ps = cn.prepareStatement(Login);
			ps.setString(1, nombreUsuario);
			ps.setString(2, contrasena);
			rs = ps.executeQuery();
			if (rs.next()) {
				return valoresUsuario(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	public int obtenerIdUsuarioPorNombre(String nombreUsuario) {
		int id = -1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = Conexion.getConexion().getSQLConnection();
			String sql = "SELECT IdUsuario FROM Usuarios WHERE NombreUsuario = ?";
			ps = cn.prepareStatement(sql);
			ps.setString(1, nombreUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getInt("IdUsuario");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al obtener ID de usuario por nombre.");
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (Exception e) {
			}
		}
		return id;
	}
}
