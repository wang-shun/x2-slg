package com.xgame.logic.server.core.net.process;

public class UnknownMessageException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnknownMessageException() {
		super();
	}

	public UnknownMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnknownMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownMessageException(String message) {
		super(message);
	}

	public UnknownMessageException(Throwable cause) {
		super(cause);
	}

}
