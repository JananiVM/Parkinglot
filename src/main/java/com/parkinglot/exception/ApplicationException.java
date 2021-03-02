package com.parkinglot.exception;

public class ApplicationException extends RuntimeException{
	 
	private static final long serialVersionUID = 1L;
	private String message;


	    public ApplicationException(String message) {
	        this.message = message;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }
}
