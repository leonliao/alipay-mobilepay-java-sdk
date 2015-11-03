package com.leonoss.alipay.apppay.exception;

public class InvalidSignatureException extends AliPayServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935217785157476565L;

	public InvalidSignatureException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidSignatureException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidSignatureException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidSignatureException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidSignatureException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
