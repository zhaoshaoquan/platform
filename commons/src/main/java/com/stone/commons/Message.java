package com.stone.commons;

import java.io.Serializable;

public class Message implements Serializable{
	private static final long serialVersionUID = 8117244181993856881L;

	private int status;
	private String text;
	private boolean success = true;
	private Object data;

	public Message(){
	}

	public Message(String text, boolean success){
		this(200, text, success);
	}

	public Message(int status, String text, Object data){
		this(status, text, true, data);
	}

	public Message(int status, String text, boolean success){
		this(status, text, success, text);
	}

	public Message(String text, boolean success, Object data){
		this(200, text, success, data);
	}

	public Message(int status, String text, boolean success, Object data){
		this.text = text;
		this.status = status;
		this.success = success;
		this.data = data;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public String getText(){
		return text;
	}

	public void setText(String text){
		this.text = text;
	}

	public int getStatus(){
		return status;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public Object getData(){
		return data;
	}

	public void setData(Object data){
		this.data = data;
	}

	@Override
	public String toString(){
		return "Message [status=" + status + ", text=" + text + ", success=" + success + ", data=" + data + "]";
	}

}
