package com.LeagueWatch.Data;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.LeagueWatch.Streamer;

public class LeagueWatch extends FetchStream {

	public LeagueWatch(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ArrayList<Streamer> fetch () {
		ArrayList<Streamer> db = new ArrayList<Streamer>();
		
		try {
			String rawJSON = getJSON();
			JSONArray jsonArray = new JSONArray(rawJSON);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject channel = new JSONObject(jsonObject.getString("fields"));

				try {
					Streamer s = new Streamer();
					String elophantUrl = "";
					
					s.setId(jsonObject.getString("pk"));
					s.setViewers(channel.getInt("viewers"));
					
					s.setName(channel.getString("name"));
					
					s.setService("Twitch");
					
					if (s.getViewers() > 100) {
							s.setChampion(0);
							
						db.add(s);
					}
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
