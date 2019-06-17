package com.comntool.util.utility.bean;

import java.io.Serializable;

public class DisPair implements Serializable{
	private static final long serialVersionUID = 123432L;
	private String dis = null;
	private String key = null;

	public DisPair() {
		super();
	}
	public DisPair(String dis, String key) {
		super();
		this.dis = dis;
		this.key = key;
	}

	@Override
    public int hashCode() {
        return this.key.hashCode();
    }
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj instanceof DisPair) {
			DisPair obje = (DisPair)obj;
			return this.getKey().equals(obje.getKey());
		}
		return false;
	}
	@Override
	public String toString() {
		return dis;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDis() {
		return dis;
	}
	public void setDis(String dis) {
		this.dis = dis;
	}

	
}
