package com.degg.famateur.exception;

@SuppressWarnings("serial")
public class NoSuchUserException extends Throwable {

	public NoSuchUserException() {
		super();
	}
	
	public NoSuchUserException(String msg) {
		super(msg);
	}
	
	public NoSuchUserException(Throwable cause) {
		super(cause);
	}
	
	public NoSuchUserException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
