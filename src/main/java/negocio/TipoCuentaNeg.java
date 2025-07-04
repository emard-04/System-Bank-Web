package negocio;

import java.util.ArrayList;

import Entidades.TipoCuenta;

public interface TipoCuentaNeg {
public TipoCuenta buscarXDescripcion(String Descripcion);
ArrayList<TipoCuenta> ListarTodo();
}
