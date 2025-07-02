package Interfaces;
import java.util.List; 
import Entidades.*;
public interface inProvincia {
	List<Provincia> listarProvincias();
	 Provincia buscarPorId(int idProvincia);
}
