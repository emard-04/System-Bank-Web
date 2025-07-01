package Entidades;

public class Usuario {
	    private int idUsuario;
	    private String nombreUsuario;
	    private String contrasena;
	    private Persona persona;  // referencia a Persona
	    private boolean tipoUsuario;
	    private String estado;

	    public Usuario() {}

	    public Usuario(int idUsuario, String nombreUsuario, String contrasena, Persona persona, boolean tipoUsuario) {
	        this.idUsuario = idUsuario;
	        this.nombreUsuario = nombreUsuario;
	        this.contrasena = contrasena;
	        this.persona = persona;
	        this.tipoUsuario = tipoUsuario;
	        
	    }

	    // Getters y Setters
	    public int getIdUsuario() {
	        return idUsuario;
	    }
	    public void setIdUsuario(int idUsuario) {
	        this.idUsuario = idUsuario;
	    }
	    public String getNombreUsuario() {
	        return nombreUsuario;
	    }
	    public void setNombreUsuario(String nombreUsuario) {
	        this.nombreUsuario = nombreUsuario;
	    }
	    public String getContrasena() {
	        return contrasena;
	    }
	    public void setContrasena(String contrasena) {
	        this.contrasena = contrasena;
	    }
	    public Persona getPersona() {
	        return persona;
	    }
	    public void setPersona(Persona persona) {
	        this.persona = persona;
	    }
	    public boolean isTipoUsuario() {
	        return tipoUsuario;
	    }
	    public void setTipoUsuario(boolean tipoUsuario) {
	        this.tipoUsuario = tipoUsuario;
	    }

	    

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		@Override
	    public String toString() {
	        return "Usuario: " + nombreUsuario + " | Persona: " + persona.getDni() + " " + persona.getApellido() + " | Tipo: " + (tipoUsuario ? "Admin" : "Cliente");
	    }
	}

