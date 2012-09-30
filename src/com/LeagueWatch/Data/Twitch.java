package com.LeagueWatch.Data;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.LeagueWatch.R;
import com.LeagueWatch.Streamer;

import android.util.Log;

public class Twitch extends FetchStream {

	public Map<String, String> streamerMap = new HashMap<String, String>();
	
	public Twitch(String url) {
		super(url);
	}

	@Override
	public ArrayList<Streamer> fetch () {
		ArrayList<Streamer> db = new ArrayList<Streamer>();
		
		streamerMap.put("tsm_oddone", "theoddone");
		streamerMap.put("tsm_chaox", "shan huang");
		streamerMap.put("tsm_dyrus", "dyrus");
		streamerMap.put("wingsofdeath", "wingsofdeathx");
		
		try {
			String rawJSON = getJSON();
			JSONArray jsonArray = new JSONArray((new JSONObject(rawJSON)).getString("streams"));
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject channel = new JSONObject(jsonObject.getString("channel"));

				try {
					Streamer s = new Streamer();
					String elophantUrl = "";
					
					s.setId("" + channel.getLong("_id"));
					s.setViewers(jsonObject.getInt("viewers"));
					
					s.setName(channel.getString("display_name"));
					
					s.setService("Twitch");
					
					if (s.getViewers() > 100) {
						
						if (streamerMap.get(s.getName().toLowerCase()) != null) {
							elophantUrl = "http://elophant.com/api/v1/na/getInProgressGameInfo?summonerName="+URLEncoder.encode(streamerMap.get(s.getName().toLowerCase()), "utf-8")+"&key=7Gys2czGWadX16TPrvmC";
							Elophant e = new Elophant(elophantUrl);
							s.setChampion(e.fetchChampion(streamerMap.get(s.getName().toLowerCase())));
						} else {
							elophantUrl = "http://elophant.com/api/v1/na/getInProgressGameInfo?summonerName="+URLEncoder.encode(s.getName().toLowerCase(), "utf-8")+"&key=7Gys2czGWadX16TPrvmC";
							Elophant e = new Elophant(elophantUrl);
							s.setChampion(e.fetchChampion(s.getName().toLowerCase()));
						}
							
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
