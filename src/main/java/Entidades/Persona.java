package Entidades;

import java.time.LocalDate;
public class Persona {

	    private String dni;
	    private String cuil;
	    private String nombre;
	    private String apellido;
	    private String sexo;
	    private String nacionalidad;
	    private LocalDate fechaNacimiento;
	    private String direccion;
	    private String localidad;
	    private String provincia;
	    private String correoElectronico;

	    public Persona() {}

	    public Persona(String dni, String cuil, String nombre, String apellido, String sexo, String nacionalidad,
	                   LocalDate fechaNacimiento, String direccion, String localidad, String provincia, String correoElectronico) {
	        this.dni = dni;
	        this.cuil = cuil;
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.sexo = sexo;
	        this.nacionalidad = nacionalidad;
	        this.fechaNacimiento = fechaNacimiento;
	        this.direccion = direccion;
	        this.localidad = localidad;
	        this.provincia = provincia;
	        this.correoElectronico = correoElectronico;
	    }

	    // Getters y Setters

	    public String getDni() {
	        return dni;
	    }
	    public void setDni(String dni) {
	        this.dni = dni;
	    }
	    public String getCuil() {
	        return cuil;
	    }
	    public void setCuil(String cuil) {
	        this.cuil = cuil;
	    }
	    public String getNombre() {
	        return nombre;
	    }
	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }
	    public String getApellido() {
	        return apellido;
	    }
	    public void setApellido(String apellido) {
	        this.apellido = apellido;
	    }
	    public String getSexo() {
	        return sexo;
	    }
	    public void setSexo(String sexo) {
	        this.sexo = sexo;
	    }
	    public String getNacionalidad() {
	        return nacionalidad;
	    }
	    public void setNacionalidad(String nacionalidad) {
	        this.nacionalidad = nacionalidad;
	    }
	    public LocalDate getFechaNacimiento() {
	        return fechaNacimiento;
	    }
	    public void setFechaNacimiento(LocalDate fechaNacimiento) {
	        this.fechaNacimiento = fechaNacimiento;
	    }
	    public String getDireccion() {
	        return direccion;
	    }
	    public void setDireccion(String direccion) {
	        this.direccion = direccion;
	    }
	    public String getLocalidad() {
	        return localidad;
	    }
	    public void setLocalidad(String localidad) {
	        this.localidad = localidad;
	    }
	    public String getProvincia() {
	        return provincia;
	    }
	    public void setProvincia(String provincia) {
	        this.provincia = provincia;
	    }
	    public String getCorreoElectronico() {
	        return correoElectronico;
	    }
	    public void setCorreoElectronico(String correoElectronico) {
	        this.correoElectronico = correoElectronico;
	    }

	    @Override
	    public String toString() {
	        return nombre + " " + apellido + " | DNI: " + dni + " | CUIL: " + cuil + " | " + nacionalidad + " | Nacido: " + fechaNacimiento;
	    }
	}

