package it.dipe.opencup.exception;

public class DatabaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1289308786395082557L;

	public DatabaseException() {
		super();
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(Throwable cause) {
		super(cause);
	}

}
