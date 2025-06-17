package Interfaces;

	import Entidades.*;
	import java.util.ArrayList;
	public interface inCuentas {
	    
	        boolean Agregar(Cuenta cuenta);
	        boolean Modificar(Cuenta cuenta);
	        boolean Eliminar(int nroCuenta);
	        ArrayList<Cuenta> ListarTodo();
	        Cuenta BuscarPorNro(int nroCuenta);
	        boolean existe(int nroCuenta);
	}

