package Entidades;

public class TelefonoxPersona {
	private Persona dni;
    private String telefono;

    public TelefonoxPersona() {}

    public TelefonoxPersona(Persona dni, String telefono) {
        this.dni = dni;
        this.telefono = telefono;
    }

    public Persona getDni() {
        return dni;
    }
    public void setDni(Persona dni) {
        this.dni = dni;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Teléfono: " + telefono + " (DNI: " + dni + ")";
    }
}

