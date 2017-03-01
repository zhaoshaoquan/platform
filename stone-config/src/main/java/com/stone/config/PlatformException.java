package com.stone.config;

public class PlatformException extends RuntimeException {

	private static final long serialVersionUID = -5143695406381565749L;

	public PlatformException(){
		super();
	}

	public PlatformException(String msg){
		super(msg);
	}

	public PlatformException(String msg, Throwable cause){
		super(msg, cause);
	}

}
