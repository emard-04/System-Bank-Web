package Entidades;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Cuenta {
	private int nroCuenta;
    private Usuario usuario;
    private LocalDate fechaCreacion;
    private TipoCuenta tipoCuenta;
    private String cbu;
    private BigDecimal saldo;

    public Cuenta() {}

    public Cuenta(int nroCuenta, Usuario usuario, LocalDate fechaCreacion, TipoCuenta tipoCuenta, String cbu, BigDecimal saldo) {
        this.nroCuenta = nroCuenta;
        this.usuario = usuario;
        this.fechaCreacion = fechaCreacion;
        this.tipoCuenta = tipoCuenta;
        this.cbu = cbu;
        this.saldo = saldo;
    }

    public int getNroCuenta() {
        return nroCuenta;
    }
    public void setNroCuenta(int nroCuenta) {
        this.nroCuenta = nroCuenta;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }
    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    public String getCbu() {
        return cbu;
    }
    public void setCbu(String cbu) {
        this.cbu = cbu;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cuenta #" + nroCuenta + " | Usuario: " + usuario.getNombreUsuario() + " | Tipo: " + tipoCuenta.getDescripcion() + 
               " | Saldo: $" + saldo + " | CBU: " + cbu + " | Creada: " + fechaCreacion;
    }
}

