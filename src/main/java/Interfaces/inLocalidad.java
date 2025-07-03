package Interfaces;
import java.util.List;
import Entidades.*;
public interface inLocalidad {
	List<Localidad> listarLocalidadesPorProvincia(int idProvincia);
	 Localidad buscarPorId(int idLocalidad, int idProvincia);
}
