package negocioImpl;

import java.util.List;

import Daos.daoLocalidad;
import Entidades.Localidad;
import Interfaces.inLocalidad;
import negocio.LocalidadNeg;

public class LocalidadNegImpl implements LocalidadNeg{
	 private inLocalidad daoL = new daoLocalidad();
	@Override
	public List<Localidad> listarLocalidadesPorProvincia(int idProvincia) {
		 return daoL.listarLocalidadesPorProvincia(idProvincia);
	}
	@Override
    public Localidad buscarPorId(int idLocalidad) {
        return daoL.buscarPorId(idLocalidad);
    }

}
