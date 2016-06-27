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

    public Message(String statusText, boolean success) {
        this(200, statusText, success);
    }

    public Message(String statusText, boolean success, Object data) {
        this(200, statusText, success, data);
    }

    public Message(int status, String statusText) {
        this(status, statusText, status>=0);
    }

    public Message(int status, String statusText, Object data) {
        this(status, statusText, status>=0, data);
    }

    public Message(int status, String statusText, boolean success) {
        this(status, statusText, success, null);
    }

    public Message(int status, String statusText, boolean success, Object data) {
        this.status = status;
        this.statusText = statusText;
        this.success = success;
        this.data = data;
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
