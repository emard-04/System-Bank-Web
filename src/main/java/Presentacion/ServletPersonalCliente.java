package Presentacion;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.*;
import negocioImpl.*;
import Entidades.*;

/**
 * Servlet implementation class ServletPersonalCliente
 */
@WebServlet("/ServletPersonalCliente")
public class ServletPersonalCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TelefonoNeg telefonoNeg = new TelefonoNegImpl();
	private ProvinciaNeg proNeg = new ProvinciaNegImpl();
	private LocalidadNeg LocNeg = new LocalidadNegImpl();
	private PaisNeg paisNeg = new PaisNegImpl();
       
  
    public ServletPersonalCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        String cuentaParam = request.getParameter("cuentaSeleccionada");
        if (cuentaParam != null && !cuentaParam.isEmpty()) {
            int nroCuenta = Integer.parseInt(cuentaParam);
            Cuenta cuenta = new CuentasNegImpl().BuscarPorNro(nroCuenta);
            if (cuenta != null) {
                session.setAttribute("cuenta", cuenta);
            }
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (session.getAttribute("cuenta") == null) {
            @SuppressWarnings("unchecked")
            ArrayList<Cuenta> cuentasUsuario = (ArrayList<Cuenta>) session.getAttribute("cuentasUsuario");
            if (cuentasUsuario != null && !cuentasUsuario.isEmpty()) {
                session.setAttribute("cuenta", cuentasUsuario.get(0)); 
            }
        }

        ArrayList<TelefonoxPersona> telefonosUsuario = telefonoNeg.listarTelefonos(usuario.getPersona().getDni());

        Provincia pro = proNeg.buscarPorId(usuario.getPersona().getProvincia().getIdProvincia());
        String proUsu = pro != null ? pro.getNombre() : "";

        Localidad loc = LocNeg.buscarPorId(usuario.getPersona().getLocalidad().getIdLocalidad(),pro.getIdProvincia());
        String locUsu = loc != null ? loc.getNombre() : "";

        Pais pa = paisNeg.buscarXID(usuario.getPersona().getPais().getIdPais());
        String PaUsu = pa !=null ? pa.getNombre() : "";

        request.setAttribute("usuario", usuario);
        
        request.setAttribute("telefonosUsuario", telefonosUsuario);
        
        String nombreProvincia = pro != null ? pro.getNombre() : "";
        String nombreLocalidad = loc != null ? loc.getNombre() : "";
        String nombrePais = pa != null ? pa.getNombre() : "";

        request.setAttribute("nombreProvincia", nombreProvincia);
        request.setAttribute("nombreLocalidad", nombreLocalidad);
        request.setAttribute("nombrePais", nombrePais);

        request.getRequestDispatcher("/ClientMode/personalClient.jsp").forward(request, response);
    }

	
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
