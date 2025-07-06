package Entidades;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Prestamos {
    private int idPrestamo;
    private Usuario usuario;
    private LocalDate fecha;
    private BigDecimal importeApagar;
    private BigDecimal importePedido;
    private String plazoDePago;
    private BigDecimal montoCuotasxMes;
    private String estadoSolicitud;
    private String estadoPago;
    private Cuenta cuenta;

    public Prestamos() {}

    public Prestamos(int idPrestamo, Usuario usuario, LocalDate fecha, BigDecimal importeApagar, BigDecimal importePedido,
                    String plazoDePago, BigDecimal montoCuotasxMes) {
        this.idPrestamo = idPrestamo;
        this.usuario = usuario;
        this.fecha = fecha;
        this.importeApagar = importeApagar;
        this.importePedido = importePedido;
        this.plazoDePago = plazoDePago;
        this.montoCuotasxMes = montoCuotasxMes;
    }
    
    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    public int getIdPrestamo() {
        return idPrestamo;
    }
    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public BigDecimal getImporteApagar() {
        return importeApagar;
    }
    public void setImporteApagar(BigDecimal importeApagar) {
        this.importeApagar = importeApagar;
    }
    public BigDecimal getImportePedido() {
        return importePedido;
    }
    public void setImportePedido(BigDecimal importePedido) {
        this.importePedido = importePedido;
    }
    public String getPlazoDePago() {
        return plazoDePago;
    }
    public void setPlazoDePago(String plazoDePago) {
        this.plazoDePago = plazoDePago;
    }
    public BigDecimal getMontoCuotasxMes() {
        return montoCuotasxMes;
    }
    public void setMontoCuotasxMes(BigDecimal montoCuotasxMes) {
        this.montoCuotasxMes = montoCuotasxMes;
    }

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getEstadoPago() {
		return estadoPago;
	}

	public void setEstadoPago(String estadoPago) {
		this.estadoPago = estadoPago;
	}

	@Override
    public String toString() {
        return "Préstamo ID: " + idPrestamo + " | Usuario: " + usuario.getNombreUsuario() + " | Fecha: " + fecha +
                "\n  Importe Pedido: $" + importePedido + " | Importe a Pagar: $" + importeApagar +
                "\n  Plazo: " + plazoDePago + " | Cuotas Mensuales: $" + montoCuotasxMes;
    }
}
