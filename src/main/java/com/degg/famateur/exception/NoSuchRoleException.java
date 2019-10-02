package com.degg.famateur.exception;

@SuppressWarnings("serial")
public class NoSuchRoleException extends Throwable {

	public NoSuchRoleException() {
		super();
	}
	
	public NoSuchRoleException(String msg) {
		super(msg);
	}
	
	public NoSuchRoleException(Throwable cause) {
		super(cause);
	}
	
	public NoSuchRoleException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
