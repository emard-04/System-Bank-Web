package Entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movimiento {
    private int idMovimiento;
    private Usuario usuario;
    private TipoMovimiento tipoMovimiento;
    private Cuenta CuentaEmisor;


	private Cuenta CuentaReceptor;
    private String detalle;
    private BigDecimal importe;
    private LocalDate fecha;

    public Movimiento() {}

    public Movimiento(int idMovimiento, Usuario usuario, TipoMovimiento tipoMovimiento, Cuenta cuenta,
    		 Cuenta cuentaEmisor, Cuenta cuentaReceptor,String detalle, BigDecimal importe, LocalDate fecha) {
        this.idMovimiento = idMovimiento;
        this.usuario = usuario;
        this.tipoMovimiento = tipoMovimiento;
        this.CuentaEmisor = cuentaEmisor;
        this.CuentaReceptor = cuentaReceptor;
        this.detalle = detalle;
        this.importe = importe;
        this.fecha = fecha;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }
    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    } 
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
    public Cuenta getCuentaEmisor() {
		return CuentaEmisor;
	}

	public void setCuentaEmisor(Cuenta cuentaEmisor) {
		CuentaEmisor = cuentaEmisor;
	}

	public Cuenta getCuentaReceptor() {
		return CuentaReceptor;
	}

	public void setCuentaReceptor(Cuenta cuentaReceptor) {
		CuentaReceptor = cuentaReceptor;
	}
    public String getDetalle() {
        return detalle;
    }
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    public BigDecimal getImporte() {
        return importe;
    }
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return  "Movimiento" +
                "ID=" + idMovimiento +
                ", Usuario=" + usuario.getIdUsuario() +
                ", TipoMovimiento=" +tipoMovimiento.getIdTipoMovimiento() +
                ", Cuenta=" + CuentaEmisor.getNroCuenta() +
                ", Detalle='" + detalle + '\'' +
                ", Importe=" + importe +
                ", Fecha=" + fecha+""
                ;}
}
