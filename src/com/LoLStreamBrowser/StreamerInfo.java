package com.LoLStreamBrowser;

public class StreamerInfo implements Comparable {
	String name;
	int viewers;
	String service;
	boolean favorite;
	boolean featured;
	
	@Override
	public int compareTo(Object another) {
		return -1*this.viewers + ((StreamerInfo)another).viewers;
	}
}