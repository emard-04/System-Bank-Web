package exceptions;

public class ErrorAlEliminarException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "No se a podido eliminar el contenido deseado";
	}

}
