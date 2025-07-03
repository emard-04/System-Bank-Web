package Presentacion;

import java.io.IOException;
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

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        // Obtener teléfono
        TelefonoxPersona telefono = telefonoNeg.buscarPorDni(usuario.getPersona().getDni());
        String telefonoUsuario = telefono != null ? telefono.getTelefono() : "";
     // Obtener Provincia
        Provincia pro = proNeg.buscarPorId(usuario.getPersona().getProvincia().getIdProvincia());
        String proUsu = pro != null ? pro.getNombre() : "";
     // Obtener Localidad
        Localidad loc = LocNeg.buscarPorId(usuario.getPersona().getLocalidad().getIdLocalidad(),pro.getIdProvincia());
        String locUsu = loc != null ? loc.getNombre() : "";

        // Pasar datos al JSP
        request.setAttribute("usuario", usuario);
        request.setAttribute("telefonoUsuario", telefonoUsuario);
        
        String nombreProvincia = pro != null ? pro.getNombre() : "";
        String nombreLocalidad = loc != null ? loc.getNombre() : "";

        request.setAttribute("nombreProvincia", nombreProvincia);
        request.setAttribute("nombreLocalidad", nombreLocalidad);

        // Forward al JSP que muestra la info personal
        request.getRequestDispatcher("/ClientMode/personalClient.jsp").forward(request, response);
    }

	
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
