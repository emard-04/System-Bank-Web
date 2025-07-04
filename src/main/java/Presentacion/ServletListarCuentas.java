package Presentacion;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Daos.*;
import Entidades.*;
import negocio.CuentasNeg;
import negocio.TipoCuentaNeg;
import negocioImpl.CuentasNegImpl;
import negocioImpl.TipoCuentaNegImpl;

import javax.servlet.RequestDispatcher;
/**
 * Servlet implementation class ServletListarCuentas
 */
@WebServlet("/ServletListarCuentas")
public class ServletListarCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CuentasNeg nCuenta = new CuentasNegImpl();
   private TipoCuentaNeg nTipoCuenta= new TipoCuentaNegImpl();
    public ServletListarCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(request.getParameter("openListar")!=null) {
    	windowDefault(request, response);
    	}
    	if(request.getParameter("Filtrar")!=null) {
    		String dni=request.getParameter("Busqueda");
    		int TipoCuenta=0;
    		String Orden="DESC";// orden descendente por defecto
    		if(request.getParameter("TipoCuenta")!=null&&!request.getParameter("TipoCuenta").isEmpty())TipoCuenta=Integer.parseInt( request.getParameter("TipoCuenta"));
    		if(request.getParameter("orden")!=null&&!request.getParameter("orden").isEmpty())Orden=request.getParameter("orden");
    		Paginar(request, nCuenta.filtrar(dni, TipoCuenta, Orden));
    		RequestDispatcher rd= request.getRequestDispatcher("/AdminMode/cuentasAdmin.jsp");
    		rd.forward(request, response);
    	}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void windowDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        List<Cuenta> listaCuentas = nCuenta.ListarTodo();
	        Paginar(request, listaCuentas);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMode/cuentasAdmin.jsp");
	        dispatcher.forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("error.jsp");
	    }
	}
	public void Paginar(HttpServletRequest request, List<Cuenta> listaCuentas)throws ServletException, IOException {
		int cuentasPorPagina = 10;
        int paginaActual = 1;

        // Obtener número de página desde la URL
        String pagParam = request.getParameter("pagina");
        if (pagParam != null) {
            try {
                paginaActual = Integer.parseInt(pagParam);
            } catch (NumberFormatException e) {
                paginaActual = 1;
            }
        }

        // Calcular índices
        int totalCuentas = listaCuentas.size();
        int totalPaginas = (int) Math.ceil((double) totalCuentas / cuentasPorPagina);
        int desde = (paginaActual - 1) * cuentasPorPagina;
        int hasta = Math.min(desde + cuentasPorPagina, totalCuentas);

        // Sublista de la página actual
        List<Cuenta> cuentasPaginadas = listaCuentas.subList(desde, hasta);

        // Setear atributos
        request.setAttribute("cuentas", cuentasPaginadas);
        request.setAttribute("paginaActual", paginaActual);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("listaTipoCuenta", nTipoCuenta.ListarTodo());
	}
	}

