package com.LeagueWatch.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.LeagueWatch.R;
import com.LeagueWatch.Streamer;
import com.LeagueWatch.Push.LoLDatabase;

public class LeagueWatch extends FetchStream {
	
	private LoLDatabase LoLDB = new LoLDatabase();
	
	public LeagueWatch(String url) {
		super(url);
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
					
					s.setId(jsonObject.getString("pk").substring(0,jsonObject.getString("pk").indexOf("_")));
					s.setViewers(channel.getInt("viewers"));
					
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
