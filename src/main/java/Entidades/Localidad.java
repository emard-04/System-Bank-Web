package Entidades;

public class Localidad {
	private int idLocalidad;
    private String nombre;
    private Provincia provincia; 

   
    public int getIdLocalidad() {
        return idLocalidad;
    }
    public void setIdLocalidad(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }
    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}
