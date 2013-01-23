package com.LeagueWatch.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.util.Log;

import com.LeagueWatch.R;
import com.LeagueWatch.RecentGame;
import com.LeagueWatch.Streamer;

public class LeagueWatch extends FetchStream {
	
	private LoLDatabase LoLDB = new LoLDatabase();
	private SharedPreferences pref;
	
	public LeagueWatch(SharedPreferences pref) {
		super();
		this.pref = pref;
	}
	
	@Override
	public ArrayList fetch(String streamer_id) {
		ArrayList returnList = null;
		
		if (streamer_id == null || streamer_id.length() == 0) {
			this.setURL("http://www.leaguewat.ch/streamers");
			returnList = fetchStreamers();			
		} else {
			this.setURL("http://www.leaguewat.ch/streamers/" + streamer_id + "/games/");
			returnList = fetchRecentGames();				
		}
		
		return returnList;
	}

	public ArrayList<RecentGame> fetchRecentGames() {
		ArrayList<RecentGame> db = new ArrayList<RecentGame>();
		
		try {
			String rawJSON = getJSON();
			JSONArray jsonArray = new JSONArray(rawJSON);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject channel = new JSONObject(jsonObject.getString("fields"));

				try {
					
					RecentGame g = new RecentGame();
					
					g.setId(channel.getString("game_id"));
					g.setMap_id(channel.getInt("map_id"));
					try {
						g.setChampion(LoLDB.getIcon(channel.getInt("champion")));
					} catch (Exception e) {
						// Ignore Failure
					}
					g.setStreamer_id(channel.getString("streamer_id"));
					g.setSummoner_spell_1(channel.getInt("summoner_spell_1"));
					g.setSummoner_spell_2(channel.getInt("summoner_spell_2"));
					g.setKills(channel.getInt("kills"));
					g.setDeaths(channel.getInt("deaths"));
					g.setAssists(channel.getInt("assists"));
					if (channel.getInt("win") == 1)
						g.setWin(true);
					else
						g.setWin(false);
					
					for (int j = 0; j < 6; j++) {
						try {
							g.setItem(j, LoLDB.getItemIcon(channel.getInt("item_"+(j+1))));
						} catch (Exception e) {
							g.setItem(j, R.drawable.item);
						}
					}
					
					db.add(g);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return db;
	}
	
	public ArrayList<Streamer> fetchStreamers() {
		ArrayList<Streamer> db = new ArrayList<Streamer>();
		
		try {
			String rawJSON = getJSON();
			JSONArray jsonArray = new JSONArray(rawJSON);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject channel = new JSONObject(jsonObject.getString("fields"));

				try {
					Streamer s = new Streamer();
					
					s.setId(jsonObject.getString("pk"));
					s.setViewers(channel.getInt("viewers"));
					s.setStreamName(channel.getString("name"));
					
					String name = channel.getString("name");
					name = name.replace("_", " ");
					
				    Matcher m = Pattern.compile("(m5.benq|4not|crs|tsm|clg|sk|( [a-z])|(^[a-z]))").matcher(name);

				    StringBuilder sb = new StringBuilder();
				    int last = 0;
				    while (m.find()) {
				        sb.append(name.substring(last, m.start()));
				        sb.append(m.group(0).toUpperCase());
				        last = m.end();
				    }
				    sb.append(name.substring(last));
				    
				    name = sb.toString();
				    
				    name = name.replaceAll("( -|\\(.*| \\d{4}\\+?.*|&quot;)", "");
					
					if (name.length() > 30)
						s.setName(name.substring(0,24)+"...");
					else
						s.setName(name);
					
					s.setService(LoLDB.getService((jsonObject.getString("pk").substring(jsonObject.getString("pk").indexOf("_")+1))));
					
					if (channel.getInt("champion") != 0)
						s.setChampionName(LoLDB.getName(channel.getInt("champion")));
					
					s.setChampion(LoLDB.getIcon(channel.getInt("champion")));
					
					s.setFeatured(channel.getInt("featured"));
					
					s.setThumbnail(channel.getString("thumbnail"));
					
					s.setFavorite(pref.getBoolean(s.getId() + "~streamer~" + s.getName(), false));
							
					db.add(s);
					
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return db;
	}
}
