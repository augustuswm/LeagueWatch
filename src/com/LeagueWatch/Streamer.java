package com.LeagueWatch;

import java.io.Serializable;

public class Streamer implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private int viewers;
	private int champion;
	private String service;
	boolean favorite;
	boolean featured;
	
	@Override
	public int compareTo(Object another) {
		return -1*this.getViewers() + ((Streamer)another).getViewers();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getViewers() {
		return viewers;
	}

	public void setViewers(int viewers) {
		this.viewers = viewers;
	}

	public int getChampion() {
		return champion;
	}

	public void setChampion(int champion) {
		this.champion = champion;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
}