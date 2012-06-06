package com.LeagueWatch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class Twitch extends FetchStream {

	public Twitch(String url) {
		super(url);
	}

	@Override
	public ArrayList<Streamer> fetch () {
		ArrayList<Streamer> db = new ArrayList<Streamer>();
		
		try {
			String rawJSON = getJSON();
			JSONArray jsonArray = new JSONArray(rawJSON);
			Log.d("Stream", "Number of entries " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				
				try {
					Streamer s = new Streamer();
					s.id = jsonObject.getLong("id");
					s.viewers = jsonObject.getInt("channel_count");
					s.name = jsonObject.getString("title");
					s.service = "Twitch";
					
					if (s.viewers > 50)
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
