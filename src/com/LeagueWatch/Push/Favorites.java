package com.LeagueWatch.Push;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.LeagueWatch.Data.FetchStream;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.CheckBoxPreference;
import android.util.Log;

public class Favorites extends AsyncTask<Void, Void, Boolean> {
	
	String regId;
	SharedPreferences sharedPref;
	
	public Favorites (String regId, SharedPreferences sharedPref) {
		this.regId = regId;
		this.sharedPref = sharedPref;
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
	
	@Override
	protected Boolean doInBackground(Void... arg0) {
		
		Map<String, ?> prefs = sharedPref.getAll();
		Editor editor = sharedPref.edit();
		FetchStream f = new FetchStream();
		String streamerId = "", rawJSON = "";

		for(Map.Entry<String,?> entry : prefs.entrySet()){
			if (entry.getKey().matches(".*?~streamer~.*")) {
				String[] stringParts = entry.getKey().split("~");
				if ((Boolean)entry.getValue()) {
					f.setURL("http://www.leaguewat.ch/gcm/" + regId + "/add/" + stringParts[0]);
					rawJSON = f.getJSON();
				} else {
					//Log.d("Stream", "http://www.leaguewat.ch/gcm/" + regId + "/remove/" + stringParts[0]);
					f.setURL("http://www.leaguewat.ch/gcm/" + regId + "/remove/" + stringParts[0]);
					rawJSON = f.getJSON();
					try {
						JSONObject response = new JSONObject(rawJSON);
						Log.d("Stream", Boolean.toString(response.getBoolean("success")));
						if (response.getBoolean("success")) {
							editor.remove(entry.getKey());
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//Log.d("Stream", "http://www.leaguewat.ch/gcm/" + regId + "/add/" + stringParts[0]);
				//Log.d("Stream", rawJSON);
			}
		}
		
		editor.commit();
		
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		
		
    }
	
}
