package negocioImpl;

import java.time.LocalDate;

import Daos.daoMovimiento;
import Daos.daoUsuario;
import Entidades.Cuenta;
import Entidades.Movimiento;
import Entidades.Usuario;
import negocio.UsuarioNeg;
import negocio.MovimientoNeg;

public class MovimientoNegImpl implements MovimientoNeg {
	private final CuentasNegImpl nCuenta= new CuentasNegImpl();
	private final UsuarioNeg nUsuario= new UsuarioNegImpl();
	private final daoMovimiento dMov= new daoMovimiento();
public boolean Agregar(Movimiento mov) {

	return dMov.Agregar(mov);
}
}
