package negocioImpl;

import java.util.List;


import Daos.daoProvincia;
import Entidades.Provincia;
import Interfaces.inProvincia;
import negocio.ProvinciaNeg;

public class ProvinciaNegImpl implements ProvinciaNeg{
	private inProvincia daoP = new daoProvincia();
	@Override
	public List<Provincia> listarProvincias() {
		 return daoP.listarProvincias();
	}
	@Override
    public Provincia buscarPorId(int idProvincia) {
        return daoP.buscarPorId(idProvincia);
    }
	public List<Provincia>listarProvinciasxPais(int idPais){
		return daoP.listarProvinciasxPais(idPais);
	}
}
