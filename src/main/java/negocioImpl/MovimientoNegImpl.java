package negocioImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import Daos.daoMovimiento;
import Daos.daoUsuario;
import Entidades.Cuenta;
import Entidades.Movimiento;
import Entidades.Usuario;
import negocio.UsuarioNeg;
import negocio.MovimientoNeg;

public class MovimientoNegImpl implements MovimientoNeg {
	private final CuentasNegImpl nCuenta= new CuentasNegImpl();
	private final daoMovimiento dMov= new daoMovimiento();
public boolean Agregar(Movimiento movReceptor, Movimiento movEmisor) {
if(!(movEmisor.getCuentaEmisor().getSaldo().compareTo(movReceptor.getImporte()) > 0))return false;
BigDecimal saldoEmisor=movEmisor.getCuentaEmisor().getSaldo().subtract(movEmisor.getImporte());
BigDecimal saldoReceptor=movReceptor.getCuentaEmisor().getSaldo().add(movReceptor.getImporte());
Cuenta Emisor= new Cuenta();
Cuenta receptor = new Cuenta();
Emisor=movEmisor.getCuentaEmisor();
Emisor.setSaldo(saldoEmisor);
movEmisor.setImporte(movEmisor.getImporte().negate());
receptor=movEmisor.getCuentaReceptor();
receptor.setSaldo(saldoReceptor);
boolean exitoEmisor=nCuenta.Modificar(Emisor);
boolean exitoReceptor = nCuenta.Modificar(receptor);
if(!exitoEmisor)return false;
if(!exitoReceptor)return false;
if(dMov.Agregar(movEmisor)&&dMov.Agregar(movReceptor)) {
	return true;
}
return false;
}
public ArrayList<Movimiento> Listarxcuentas(Movimiento mov){
	return dMov.Listarxcuentas(mov);
}
}
