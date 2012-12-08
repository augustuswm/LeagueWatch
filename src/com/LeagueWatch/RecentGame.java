package com.LeagueWatch;

import java.io.Serializable;

public class RecentGame implements Comparable<Object>, Serializable  {

	private String id;
	private String streamer_id;
	private int map_id;
	private long game_date;
	private boolean win;
	private int champion;
	private int summoner_spell_1;
	private int summoner_spell_2;
	private int[] items = new int[6];
	
	@Override
	public int compareTo(Object another) {
			return (int) (-1*this.getDate() + ((RecentGame)another).getDate());
	}

	public long getDate() {
		return this.game_date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStreamer_id() {
		return streamer_id;
	}

	public void setStreamer_id(String streamer_id) {
		this.streamer_id = streamer_id;
	}

	public int getMap_id() {
		return map_id;
	}

	public void setMap_id(int map_id) {
		this.map_id = map_id;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getChampion() {
		return champion;
	}

	public void setChampion(int champion) {
		this.champion = champion;
	}

	public int getSummoner_spell_1() {
		return summoner_spell_1;
	}

	public void setSummoner_spell_1(int summoner_spell_1) {
		this.summoner_spell_1 = summoner_spell_1;
	}

	public int getSummoner_spell_2() {
		return summoner_spell_2;
	}

	public void setSummoner_spell_2(int summoner_spell_2) {
		this.summoner_spell_2 = summoner_spell_2;
	}

	public int[] getItems() {
		return items;
	}

	public int getItem(int item) {
		return items[item];
	}
	
	public void setItems(int[] items) {
		this.items = items;
	}

	public int setItem(int index, int item) {
		return this.items[index] = item;
	}
	
}
