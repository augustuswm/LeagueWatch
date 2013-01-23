package com.LeagueWatch;

import java.io.Serializable;

public class Streamer implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String streamName;
	private String id;
	private int viewers;
	private int champion;
	private String championName;
	private String service;
	private int featured = 0;
	private String thumbnail;
	private boolean favorite;
	private boolean isSelected;
	
	@Override
	public int compareTo(Object another) {
		if (this.favorite != ((Streamer)another).favorite)
			return (this.favorite) ? -1 : 1;
		else
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
	
	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
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

	public String getChampionName() {
		return championName;
	}

	public void setChampionName(String championName) {
		this.championName = championName;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public int getFeatured() {
		return featured;
	}

	public void setFeatured(int featured) {
		if (featured > 0)
			this.featured = 0;
		else
			this.featured = 8;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public void select() {
		this.isSelected = true;
	}
	
	public void unselect() {
		this.isSelected = false;
	}
	
	public void toggle() {
		this.isSelected = !this.isSelected;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
}