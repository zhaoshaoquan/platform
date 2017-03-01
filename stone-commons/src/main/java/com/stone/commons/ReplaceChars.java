package com.stone.commons;

public class ReplaceChars {
	
	private String ov;
	private String nv;
	
	public ReplaceChars(String ov, String nv){
		this.ov = ov;
		this.nv = nv;
	}

	public String getOv(){
		return ov;
	}

	public void setOv(String ov){
		this.ov = ov;
	}

	public String getNv(){
		return nv;
	}

	public void setNv(String nv){
		this.nv = nv;
	}
	
}
