package negocioImpl;

import java.util.ArrayList;

import Daos.daoTipoCuenta;
import Entidades.TipoCuenta;
import Interfaces.inTipoCuenta;
import negocio.TipoCuentaNeg;

public class TipoCuentaNegImpl implements TipoCuentaNeg {
	private inTipoCuenta dTipCuenta=new daoTipoCuenta() ;
		
public TipoCuenta buscarXDescripcion(String Descripcion) {
	if(Descripcion!=null) {
	return dTipCuenta.buscarXDescripcion(Descripcion);
	}
	return null;
}
public ArrayList<TipoCuenta> ListarTodo(){
	return dTipCuenta.ListarTodo();
}
}
