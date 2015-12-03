package com.stone.commons;

import java.io.Serializable;

public class Message implements Serializable{
	private static final long serialVersionUID = 8117244181993856881L;
	
	private int status;
	private String statusText;
	private boolean success = true;
	private Object data;

	public Message() {
	}

	public Message(int status, String statusText) {
		this(status, statusText, status>=0);
	}

	public Message(int status, String statusText, Object data) {
		this(status, statusText, status>=0);
		this.data = data;
	}

	public Message(int status, String statusText, boolean success) {
		this.statusText = statusText;
		this.status = status;
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Message [status=" + status + ", statusText=" + statusText
				+ ", success=" + success + ", data=" + data + "]";
	}

}
