package Entidades;

public class Pais {
private int IdPais;
private String Nombre;

public Pais(int idPais, String nombre) {
	super();
	IdPais = idPais;
	Nombre = nombre;
}
public Pais() {}

public int getIdPais() {
	return IdPais;
}

public void setIdPais(int idPais) {
	IdPais = idPais;
}

public String getNombre() {
	return Nombre;
}

public void setNombre(String nombre) {
	Nombre = nombre;
}

@Override
public String toString() {
	return "Pais [IdPais=" + IdPais + ", Nombre=" + Nombre + "]";
}

}
