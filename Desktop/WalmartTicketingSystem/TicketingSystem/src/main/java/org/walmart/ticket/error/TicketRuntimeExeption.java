package org.walmart.ticket.error;

public class TicketRuntimeExeption extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer errorCode;
	private ErrorId errorId;

	public TicketRuntimeExeption(String message, Throwable cause){
		super(message, cause);
	}
	
	public TicketRuntimeExeption(String message){
		super(message);
	}

	public TicketRuntimeExeption(String message, Integer errorCode, ErrorId errorId){
		super(message);
		this.errorCode = errorCode;
		this.errorId = errorId;
	}

	/**
	 * @return the errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorId
	 */
	public ErrorId getErrorId() {
		return errorId;
	}

	/**
	 * @param errorId the errorId to set
	 */
	public void setErrorId(ErrorId errorId) {
		this.errorId = errorId;
	}
}
