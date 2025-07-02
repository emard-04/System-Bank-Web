package negocio;

import java.util.List;

import Entidades.Localidad;

public interface LocalidadNeg {
	List<Localidad> listarLocalidadesPorProvincia(int idProvincia);
    Localidad buscarPorId(int idLocalidad);
}
