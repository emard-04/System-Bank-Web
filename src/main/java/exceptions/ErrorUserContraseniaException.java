package exceptions;

public class ErrorUserContraseniaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorUserContraseniaException() {

	}

	public String getMessage() {
		return "El Usuario o contrasenia son incorrectos";
	}

}
