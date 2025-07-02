package negocio;

import java.util.List;

import Entidades.Provincia;

public interface ProvinciaNeg {
	List<Provincia> listarProvincias();
	 Provincia buscarPorId(int idProvincia);
}
