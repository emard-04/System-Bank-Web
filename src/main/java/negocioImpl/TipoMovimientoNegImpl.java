package negocioImpl;

import java.util.ArrayList;

import Daos.daoTipoMovimiento;
import Entidades.TipoMovimiento;
import Interfaces.inTipoMovimiento;
import negocio.TipoMovimientoNeg;

public class TipoMovimientoNegImpl implements TipoMovimientoNeg{
	private static final inTipoMovimiento dtp= new daoTipoMovimiento();
	public TipoMovimiento buscarXDescripcion(String Descripcion) {
		return dtp.buscarXDescripcion(Descripcion);
	}
	public ArrayList<TipoMovimiento> listarTodo(){
		return dtp.listarTodo();
	}
}
