package com.LeagueWatch.Push;

import java.util.Map;

import com.LeagueWatch.Data.FetchStream;

import android.content.SharedPreferences;
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
		FetchStream f = new FetchStream();
		String streamerId = "", rawJSON = "";

		for(Map.Entry<String,?> entry : prefs.entrySet()){
			if (entry.getKey().matches(".*?~streamer~.*") && (Boolean)entry.getValue()) {
				String[] stringParts = entry.getKey().split("~");
				f.setURL("http://www.leaguewat.ch/gcm/" + regId + "/add/" + stringParts[0]);
				rawJSON = f.getJSON();
				Log.d("Stream", "http://www.leaguewat.ch/gcm/" + regId + "/add/" + stringParts[0]);
				Log.d("Stream", rawJSON);
			}
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		
		
    }
	
}
