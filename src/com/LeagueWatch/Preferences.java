package com.LeagueWatch;

import java.util.Map;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

public class Preferences extends SherlockPreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static final String KEY_PREF_DOWNLOAD_TIMING = "download_timing";
	public static final String KEY_PREF_UPLOAD_TIMING = "upload_timing";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        ListPreference downloadList = (ListPreference)findPreference("download_timing");
        downloadList.setSummary("Update streamer list every " + Integer.parseInt(downloadList.getValue())/60/1000 + " minute" + (Integer.parseInt(downloadList.getValue())/60/1000 > 1 ? "s" : ""));
		
		//ListPreference uploadList = (ListPreference)findPreference("upload_timing");
		//uploadList.setSummary("Update streamer list every " + Integer.parseInt(uploadList.getValue())/60/1000 + " minute" + (Integer.parseInt(uploadList.getValue())/60/1000 > 1 ? "s" : ""));
        
        PreferenceScreen screen = ((PreferenceScreen)findPreference("favorites_settings"));
        screen.removeAll();
        
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, ?> prefs = sharedPref.getAll();
        
		int streamerCount = 0;
        
		for(Map.Entry<String,?> entry : prefs.entrySet()){
			//Log.d("Stream", entry.getKey() + ": " + entry.getValue());
			if (entry.getKey().matches("~streamer~.*") && (Boolean)entry.getValue()) {
				//Log.d("Stream",entry.getKey() + ": " + entry.getValue().toString());
				CheckBoxPreference favoriteStreamer = new CheckBoxPreference(this);
				favoriteStreamer.setKey(entry.getKey());
				favoriteStreamer.setTitle(entry.getKey().replace("~streamer~", ""));
				favoriteStreamer.setChecked((Boolean) entry.getValue());
				
				streamerCount++;
				
				screen.addPreference(favoriteStreamer);
			}
		}
		
		screen.setSummary( "Following " + streamerCount + " streamer" + (streamerCount == 1 ? "" : "s") );
        
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPref, String key) {
		
		//Log.d("Stream", "Pref changed " + key);
		
		if (key.equals("download_timing")) {
			ListPreference list = (ListPreference)findPreference(key);
			list.setSummary("Update streamer list every " + Integer.parseInt(list.getValue())/60/1000 + " minute" + (Integer.parseInt(list.getValue())/60/1000 > 1 ? "s" : ""));
		}
		
		/*if (key.equals("upload_timing")) {
			ListPreference list = (ListPreference)findPreference(key);
			if (Integer.parseInt(list.getValue()) > 0)
				list.setSummary("Synchronize favoite streamers every " + Integer.parseInt(list.getValue())/60/1000 + " minute" + (Integer.parseInt(list.getValue())/60/1000 > 1 ? "s" : ""));
			else
				list.setSummary("Synchronizing favorite streamers disabled");
		}*/
		
		
		if (key.matches("~streamer~.*")) {
			
	        PreferenceScreen screen = ((PreferenceScreen)findPreference("favorites_settings"));
			
			Map<String, ?> prefs = sharedPref.getAll();
			
			int streamerCount = 0;
	        
			for(Map.Entry<String,?> entry : prefs.entrySet()){
				//Log.d("Stream", "" + entry.getKey() + " : " + entry.getValue());
				if (entry.getKey().matches("~streamer~.*") && (Boolean)entry.getValue())
					streamerCount++;
			}
			
			screen.setSummary( "Following " + streamerCount + " streamer" + (streamerCount == 1 ? "" : "s") );
			onContentChanged();
			//Log.d("Stream", "Following " + streamerCount + " streamer" + (streamerCount == 1 ? "" : "s"));
			//Log.d("Stream", screen.getSummary().toString());
			
		}
		
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
    
}