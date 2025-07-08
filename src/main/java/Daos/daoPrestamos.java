package Daos;

import Interfaces.*;
import Entidades.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class daoPrestamos implements InPrestamos {
	private static final String INSERT = "INSERT INTO Prestamos (IdUsuario, Fecha, ImporteAPagar, ImportePedido, PlazoDePago, MontoCuotasxMes, EstadoSolicitud, EstadoPago, IdCuenta) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
	private static final String EliminarxUsuario = "UPDATE prestamos SET EstadoSolicitud = 'Inactivo' WHERE IdUsuario = ?";
	private static final String EliminarxCuenta = "UPDATE prestamos SET EstadoSolicitud = 'Inactivo' WHERE IdCuenta = ? and EstadoPago='En curso'";
	private static final String BuscarxCuenta="select * from prestamos where IdCuenta=? and EstadoSolicitud='Aprobado'";
	public daoPrestamos() {

	}
	

	@Override
	public boolean agregar(Prestamos p) {
		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement stmt = conn.prepareStatement(INSERT)) {

			stmt.setInt(1, p.getUsuario().getIdUsuario());
			stmt.setDate(2, Date.valueOf(p.getFecha()));
			stmt.setBigDecimal(3, p.getImporteApagar());
			stmt.setBigDecimal(4, p.getImportePedido());
			stmt.setString(5, p.getPlazoDePago());
			stmt.setBigDecimal(6, p.getMontoCuotasxMes());
			stmt.setString(7, p.getEstadoSolicitud());
			stmt.setString(8, p.getEstadoPago());
			stmt.setInt(9, p.getCuenta().getNroCuenta());

			if (stmt.executeUpdate() > 0) {
				conn.commit();
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public int BuscarxCuenta(int nrocuenta) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs=null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(BuscarxCuenta);
            ps.setInt(1,nrocuenta);
             rs=ps.executeQuery();
            if (rs.next() ) {
                return rs.getInt("IdPrestamo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return -1;
    }
	public boolean EliminarxCuenta(int nrocuenta) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = Conexion.getConexion().getSQLConnection();
            ps = cn.prepareStatement(EliminarxCuenta);
            ps.setInt(1,nrocuenta);
            if (ps.executeUpdate() > 0) {
                cn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return false;
    }
	public boolean EliminarxUsuario(int id) {
	        Connection cn = null;
	        PreparedStatement ps = null;
	        try {
	            cn = Conexion.getConexion().getSQLConnection();
	            ps = cn.prepareStatement(EliminarxUsuario);
	            ps.setInt(1,id);
	            if (ps.executeUpdate() > 0) {
	                cn.commit();
	                return true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try { if (ps != null) ps.close(); } catch (Exception e) {}
	            try { if (cn != null) cn.close(); } catch (Exception e) {}
	        }
	        return false;
	    }
	@Override
	public List<Prestamos> obtenerTodos() {
	    List<Prestamos> lista = new ArrayList<>();
	    String query = """
	        SELECT p.*, per.Nombre, per.Apellido, per.Dni, u.IdUsuario
	        FROM Prestamos p
	        INNER JOIN Usuarios u ON p.IdUsuario = u.IdUsuario
	        INNER JOIN Persona per ON u.Dni = per.Dni
	    """;

	    try (Connection conn = Conexion.getConexion().getSQLConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            Prestamos p = new Prestamos();

	            p.setIdPrestamo(rs.getInt("IdPrestamo"));
	            p.setFecha(rs.getDate("Fecha").toLocalDate());
	            p.setImporteApagar(rs.getBigDecimal("ImporteApagar"));
	            p.setImportePedido(rs.getBigDecimal("ImportePedido"));
	            p.setPlazoDePago(rs.getString("PlazoDePago"));
	            p.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
	            p.setEstadoSolicitud(rs.getString("EstadoSolicitud"));
	            p.setEstadoPago(rs.getString("EstadoPago"));

	            Usuario u = new Usuario();
	            u.setIdUsuario(rs.getInt("IdUsuario"));

	            Persona persona = new Persona();
	            persona.setNombre(rs.getString("Nombre"));
	            persona.setApellido(rs.getString("Apellido"));
	            persona.setDni(rs.getString("Dni"));

	            u.setPersona(persona);
	            p.setUsuario(u);

	            lista.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

	@Override
	public List<Prestamos> obtenerPorUsuario(int idUsuario) {
		List<Prestamos> lista = new ArrayList<>();
		String query = "SELECT * FROM Prestamos WHERE IdUsuario = ?";

		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, idUsuario);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Prestamos p = mapear(rs);
				lista.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	private Prestamos mapear(ResultSet rs) throws SQLException {
		Prestamos p = new Prestamos();
		daoCuentas dc = new daoCuentas();
		p.setIdPrestamo(rs.getInt("IdPrestamo"));

		Usuario u = new Usuario();
		u.setIdUsuario(rs.getInt("IdUsuario"));
		p.setUsuario(u);

		p.setFecha(rs.getDate("Fecha").toLocalDate());
		p.setImporteApagar(rs.getBigDecimal("ImporteAPagar"));
		p.setImportePedido(rs.getBigDecimal("ImportePedido"));
		p.setPlazoDePago(rs.getString("PlazoDePago"));
		p.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
		p.setEstadoSolicitud(rs.getString("EstadoSolicitud"));
		p.setEstadoPago(rs.getString("EstadoPago"));
		p.setCuenta(dc.BuscarPorNro(rs.getInt("idCuenta")));
		return p;
	}

	@Override
	public Prestamos obtenerPendientePorUsuario(int idUsuario) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = Conexion.getConexion().getSQLConnection();
			ps = con.prepareStatement("SELECT * FROM Prestamos WHERE IdUsuario = ? AND Estado = true LIMIT 1");
			ps.setInt(1, idUsuario);
			rs = ps.executeQuery();

			if (rs.next()) {
				Prestamos p = new Prestamos();
				p.setIdPrestamo(rs.getInt("IdPrestamo"));
				p.setImportePedido(rs.getBigDecimal("ImportePedido"));
				p.setImporteApagar(rs.getBigDecimal("ImporteApagar"));
				p.setPlazoDePago(rs.getString("PlazoDePago"));
				p.setFecha(rs.getDate("Fecha").toLocalDate());
				p.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
				p.setEstadoSolicitud(rs.getString("EstadoSolicitud"));
				p.setEstadoPago(rs.getString("EstadoPago"));
				return p;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public boolean cambiarEstadoPago(int idPrestamo, String nuevoEstadoPago, Connection conn) {
	    String sql = "UPDATE Prestamos SET EstadoPago = ? WHERE IdPrestamo = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, nuevoEstadoPago);
	        ps.setInt(2, idPrestamo);
	        int rows = ps.executeUpdate();
	        return rows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	@Override
	public boolean cambiarEstadoPago(int idPrestamo, String nuevoEstadoPago) {
	    String sql = "UPDATE Prestamos SET EstadoPago = ? WHERE IdPrestamo = ?";
	    try (Connection conn = Conexion.getConexion().getSQLConnection();
	    		PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, nuevoEstadoPago);
	        ps.setInt(2, idPrestamo);
	        int rows = ps.executeUpdate();
	        return rows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean cambiarEstadoSolicitado(int idPrestamo, String nuevoEstado, Connection conn) {
		String query = "UPDATE Prestamos SET EstadoSolicitud = ? WHERE IdPrestamo = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, nuevoEstado);
			stmt.setInt(2, idPrestamo);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Prestamos> obtenerPrestamosPendientes() {
		List<Prestamos> prestamos = new ArrayList<>();

		String query = """
				    SELECT p.*, per.Dni
				    FROM Prestamos p
				    INNER JOIN Usuarios u ON p.IdUsuario = u.IdUsuario
				    INNER JOIN Persona per ON u.Dni = per.Dni
				    WHERE p.EstadoSolicitud = 'Pendiente'
				""";

		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Prestamos prestamo = new Prestamos();
				prestamo.setIdPrestamo(rs.getInt("IdPrestamo"));
				prestamo.setFecha(rs.getDate("Fecha").toLocalDate());
				prestamo.setImporteApagar(rs.getBigDecimal("ImporteApagar"));
				prestamo.setImportePedido(rs.getBigDecimal("ImportePedido"));
				prestamo.setPlazoDePago(rs.getString("PlazoDePago"));
				prestamo.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
				prestamo.setEstadoSolicitud(rs.getString("EstadoSolicitud"));
				prestamo.setEstadoPago(rs.getString("EstadoPago"));

				Usuario u = new Usuario();
				Persona per = new Persona();
				per.setDni(rs.getString("Dni"));
				u.setPersona(per);
				prestamo.setUsuario(u);

				prestamos.add(prestamo);
			}
			System.out.println("Total préstamos encontrados en DAO: ");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return prestamos;
	}

	private Prestamos mapearPrestamo(ResultSet rs) throws SQLException {
		Prestamos p = new Prestamos();

		p.setIdPrestamo(rs.getInt("IdPrestamo"));
		p.setFecha(rs.getDate("Fecha").toLocalDate());
		p.setImporteApagar(rs.getBigDecimal("ImporteApagar"));
		p.setImportePedido(rs.getBigDecimal("ImportePedido"));
		p.setPlazoDePago(rs.getString("PlazoDePago"));
		p.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
		p.setEstadoSolicitud(rs.getString("EstadoSolicitud"));
		p.setEstadoPago(rs.getString("EstadoPago"));

		// Se usa para mapear Usuario
		Usuario u = new Usuario();
		u.setIdUsuario(rs.getInt("IdUsuario"));
		u.setNombreUsuario(rs.getString("NombreUsuario"));

		// Se usa para mapear Persona
		Persona per = new Persona();
		per.setDni(rs.getString("Dni"));
		per.setNombre(rs.getString("Nombre"));
		per.setApellido(rs.getString("Apellido"));
		
		// Si tu clase Persona tiene más campos, completalos aquí.
		// XD

		u.setPersona(per);
		p.setUsuario(u);

		return p;
	}

	@Override
	public List<Prestamos> obtenerPrestamosPendientesPorDni(String dni) {
		List<Prestamos> lista = new ArrayList<>();
		String query = "SELECT * FROM Prestamos p INNER JOIN Usuarios u ON p.IdUsuario = u.IdUsuario "
				+ "INNER JOIN Persona per ON u.dni = per.dni "
				+ "WHERE p.EstadoSolicitud = 'pendiente' AND per.dni = ?";

		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, dni);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Prestamos p = mapearPrestamo(rs);
				lista.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public int cantidadPrestamosActivosPorCuenta(int idCuenta) {
		String query = "SELECT COUNT(*) FROM Prestamos WHERE IdCuenta = ? AND EstadoPago = 'En curso'";
		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, idCuenta);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int insertarYObtenerId(Prestamos p) {
		String sql = "INSERT INTO Prestamos (IdUsuario, IdCuenta, Fecha, ImportePedido, ImporteApagar, PlazoDePago, MontoCuotasxMes, EstadoSolicitud, EstadoPago) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		daoCuentas dc = new daoCuentas();
		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			// Si usás transacciones, desactivá autocommit para controlar commit y rollback
			conn.setAutoCommit(false);

			stmt.setInt(1, p.getUsuario().getIdUsuario());
			stmt.setInt(2, p.getCuenta().getNroCuenta());
			stmt.setDate(3, Date.valueOf(p.getFecha()));
			stmt.setBigDecimal(4, p.getImportePedido());
			stmt.setBigDecimal(5, p.getImporteApagar());
			stmt.setString(6, p.getPlazoDePago());
			stmt.setBigDecimal(7, p.getMontoCuotasxMes());
			stmt.setString(8, p.getEstadoSolicitud());
			stmt.setString(9, p.getEstadoPago());

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException("Fallo al insertar el préstamo, no se afectó ninguna fila.");
			}

			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					int idGenerado = rs.getInt(1);
					conn.commit(); // Confirmar cambios
					return idGenerado;
				} else {
					throw new SQLException("No se pudo obtener el ID generado.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			// Si querés rollback en caso de error
			// Tenés que manejar la conexión fuera o usar try-with-resources con conexión
			// abierta antes.
		}
		return -1;
	}

	@Override
	public int contarPrestamosPendientes() {
		String sql = "SELECT COUNT(*) FROM Prestamos WHERE EstadoSolicitud = 'Pendiente'";
		int total = 0;

		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Si querés rollback en caso de error
			// Tenés que manejar la conexión fuera o usar try-with-resources con conexión
			// abierta antes.
		}

		return total;
	}

	@Override
	public int contarPrestamosPendientesPorDni(String dni) {
		String sql = "SELECT COUNT(*) FROM Prestamos p INNER JOIN Usuarios u ON p.IdUsuario = u.IdUsuario"
				+ "		                 INNER JOIN Persona pe ON u.dni = pe.Dni"
				+ "		                 WHERE p.EstadoSolicitud = 'Pendiente' AND pe.Dni =?";
		int total = 0;

		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, dni);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					total = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// Si querés rollback en caso de error
				// Tenés que manejar la conexión fuera o usar try-with-resources con conexión
				// abierta antes.
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Si querés rollback en caso de error
			// Tenés que manejar la conexión fuera o usar try-with-resources con conexión
			// abierta antes.
		}

		return total;
	}

	@Override
	public List<Prestamos> obtenerPrestamosPendientesPaginado(int pagina, int prestamosPorPagina) {
		int offset = (pagina - 1) * prestamosPorPagina;
		String sql = "SELECT p.*, u.IdUsuario, pe.Nombre, pe.Apellido, pe.Dni"
				+ "			                 FROM Prestamos p \r\n"
				+ "			                 INNER JOIN Usuarios u ON p.IdUsuario = u.IdUsuario"
				+ "			                 INNER JOIN Persona pe ON u.dni = pe.Dni "
				+ "			                 WHERE p.EstadoSolicitud = 'Pendiente' "
				+ "			                 ORDER BY p.Fecha DESC" + "			                 LIMIT ? OFFSET ?";

		List<Prestamos> lista = new ArrayList<>();

		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, prestamosPorPagina);
			ps.setInt(2, offset);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Prestamos p = new Prestamos();
					p.setIdPrestamo(rs.getInt("IdPrestamo"));

					Usuario usuario = new Usuario();
					usuario.setIdUsuario(rs.getInt("IdUsuario"));

					Persona persona = new Persona();
					persona.setNombre(rs.getString("Nombre"));
					persona.setApellido(rs.getString("Apellido"));
					persona.setDni(rs.getString("Dni"));

					usuario.setPersona(persona);
					p.setUsuario(usuario);

					p.setFecha(rs.getDate("Fecha").toLocalDate());
					p.setImporteApagar(rs.getBigDecimal("ImporteApagar"));
					p.setImportePedido(rs.getBigDecimal("ImportePedido"));
					p.setPlazoDePago(String.valueOf(rs.getInt("PlazoDePago")));
					p.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
					p.setEstadoSolicitud(rs.getString("EstadoSolicitud"));

					lista.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public List<Prestamos> obtenerPrestamosPendientesPorDniPaginado(String dni, int pagina, int prestamosPorPagina) {
		int offset = (pagina - 1) * prestamosPorPagina;
		
		String sql = "SELECT p.*, u.IdUsuario, pe.Nombre, pe.Apellido, pe.Dni "
		           + "FROM Prestamos p "
		           + "INNER JOIN Usuarios u ON p.IdUsuario = u.IdUsuario "
		           + "INNER JOIN Persona pe ON u.dni = pe.Dni "
		           + "WHERE p.EstadoSolicitud = 'Pendiente' AND pe.Dni = ? "
		           + "ORDER BY p.Fecha DESC "
		           + "LIMIT ? OFFSET ?";

		List<Prestamos> lista = new ArrayList<>();

		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, dni);
			ps.setInt(2, prestamosPorPagina);
			ps.setInt(3, offset);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Prestamos p = new Prestamos();
					p.setIdPrestamo(rs.getInt("IdPrestamo"));

					Usuario usuario = new Usuario();
					usuario.setIdUsuario(rs.getInt("IdUsuario"));

					Persona persona = new Persona();
					persona.setNombre(rs.getString("Nombre"));
					persona.setApellido(rs.getString("Apellido"));
					persona.setDni(rs.getString("Dni"));

					usuario.setPersona(persona);
					p.setUsuario(usuario);

					p.setFecha(rs.getDate("Fecha").toLocalDate());
					p.setImporteApagar(rs.getBigDecimal("ImporteApagar"));
					p.setImportePedido(rs.getBigDecimal("ImportePedido"));
					p.setPlazoDePago(String.valueOf(rs.getInt("PlazoDePago")));
					p.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
					p.setEstadoSolicitud(rs.getString("EstadoSolicitud"));
					lista.add(p);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// Si querés rollback en caso de error
				// Tenés que manejar la conexión fuera o usar try-with-resources con conexión
				// abierta antes.
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Si querés rollback en caso de error
			// Tenés que manejar la conexión fuera o usar try-with-resources con conexión
			// abierta antes.
		}

		return lista;
	}

	public Prestamos obtenerPorId(int idPrestamo, Connection conn) {
		String sql = "SELECT * FROM Prestamos p INNER JOIN Usuarios u ON p.IdUsuario = u.IdUsuario "
				+ "INNER JOIN Persona pe ON u.Dni = pe.Dni WHERE p.IdPrestamo = ?";
		daoCuentas dc = new daoCuentas();
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, idPrestamo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Prestamos p = new Prestamos();
				// Seteá los datos del préstamo, usuario y persona
				// Asumimos que tenés las clases Usuario, Persona, etc.
				Usuario u = new Usuario();
				Persona persona = new Persona();
				persona.setDni(rs.getString("Dni"));
				persona.setNombre(rs.getString("Nombre"));
				persona.setApellido(rs.getString("Apellido"));
				u.setIdUsuario(rs.getInt("IdUsuario"));
				u.setPersona(persona);
				p.setCuenta(dc.BuscarPorNro(rs.getInt("IdCuenta")));
				p.setIdPrestamo(rs.getInt("IdPrestamo"));
				p.setUsuario(u);
				p.setImportePedido(rs.getBigDecimal("ImportePedido"));
				p.setImporteApagar(rs.getBigDecimal("ImporteApagar"));
				p.setPlazoDePago(rs.getString("PlazoDePago"));
				p.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
				p.setFecha(rs.getDate("Fecha").toLocalDate()); // si es tipo DATE

				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int contarCuotasPendientesPorPrestamo(int idPrestamo) {
		System.out.println("DEBUG: contarCuotasPendientesPorPrestamo llamado con idPrestamo = " + idPrestamo);
		String sql = "SELECT COUNT(*) FROM Cuotas WHERE IdPrestamo = ? AND FechaPago IS NULL";
		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, idPrestamo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int obtenerIdPrestamoPorCuota(int idCuota) {
		String sql = "SELECT IdPrestamo FROM Cuotas WHERE IdCuota = ?";
		try (Connection conn = Conexion.getConexion().getSQLConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, idCuota);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("IdPrestamo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	
	public List<Prestamos> obtenerPrestamosPorFechaYEstado(java.util.Date desde, java.util.Date hasta, String estado) {
        List<Prestamos> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion().getSQLConnection();
            String sql = """
                SELECT p.*, u.IdUsuario, pe.Dni, pe.Nombre, pe.Apellido, c.NroCuenta
                FROM Prestamos p
                INNER JOIN Usuarios u ON p.IdUsuario = u.IdUsuario
                INNER JOIN Persona pe ON u.Dni = pe.Dni
                INNER JOIN Cuentas c ON p.IdCuenta = c.NroCuenta
                WHERE p.Fecha BETWEEN ? AND ?
                """;
            
            // Si el estado no es "todos" o es nulo/vacío, agregar la condición de estado
            if (estado != null && !estado.isEmpty() && !estado.equalsIgnoreCase("todos")) {
                sql += " AND p.EstadoSolicitud = ?";
            }
            sql += " ORDER BY p.Fecha DESC"; // Ordenar por fecha descendente

            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(desde.getTime()));
            stmt.setDate(2, new java.sql.Date(hasta.getTime()));
            
            if (estado != null && !estado.isEmpty() && !estado.equalsIgnoreCase("todos")) {
                stmt.setString(3, estado);
            }

            rs = stmt.executeQuery();
            while (rs.next()) {
                Prestamos p = new Prestamos();
                p.setIdPrestamo(rs.getInt("IdPrestamo"));
                p.setFecha(rs.getDate("Fecha").toLocalDate()); // Usar toLocalDate()
                p.setImporteApagar(rs.getBigDecimal("ImporteApagar"));
                p.setImportePedido(rs.getBigDecimal("ImportePedido"));
                p.setPlazoDePago(rs.getString("PlazoDePago")); // Asumo que es String o lo conviertes a String
                p.setMontoCuotasxMes(rs.getBigDecimal("MontoCuotasxMes"));
                p.setEstadoSolicitud(rs.getString("EstadoSolicitud"));
                p.setEstadoPago(rs.getString("EstadoPago"));

                // Mapear Usuario y Persona
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("IdUsuario"));

                Persona persona = new Persona();
                persona.setDni(rs.getString("Dni"));
                persona.setNombre(rs.getString("Nombre"));
                persona.setApellido(rs.getString("Apellido"));

                u.setPersona(persona);
                p.setUsuario(u);
                
                // Mapear Cuenta (si es necesario para el reporte, aunque no la mostremos explícitamente en la tabla de reportes)
                Cuenta c = new Cuenta();
                c.setNroCuenta(rs.getInt("NroCuenta"));
                p.setCuenta(c); // Asegúrate de que tu clase Prestamos tiene un setCuenta

                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
    
}






