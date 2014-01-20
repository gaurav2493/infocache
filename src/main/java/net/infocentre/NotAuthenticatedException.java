package net.infocentre;

public class NotAuthenticatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	
	public NotAuthenticatedException() {
		super("Application not Authenticated to the server. Cookies not foud");
	}

}
