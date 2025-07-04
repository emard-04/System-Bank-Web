package Interfaces;

import java.util.ArrayList;

import Entidades.TipoCuenta;

public interface inTipoCuenta {
public TipoCuenta buscarXDescripcion(String Descripcion);
public TipoCuenta buscarXID(int id);
ArrayList<TipoCuenta> ListarTodo();
}
