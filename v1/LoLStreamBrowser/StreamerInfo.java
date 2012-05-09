package com.LoLStreamBrowser;

import java.io.Serializable;

public class StreamerInfo implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	String name;
	Long id;
	int viewers;
	String service;
	boolean favorite;
	boolean featured;
	
	@Override
	public int compareTo(Object another) {
		return -1*this.viewers + ((StreamerInfo)another).viewers;
	}
}