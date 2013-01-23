package com.LeagueWatch;

import java.util.Map;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.util.Log;

public class Preferences extends SherlockPreferenceActivity implements OnSharedPreferenceChangeListener, OnPreferenceChangeListener {
	
	public static final String KEY_PREF_DOWNLOAD_TIMING = "download_timing";
	public static final String KEY_PREF_UPLOAD_TIMING = "upload_timing";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        
        ApplicationInfo ai = null;
        
    	try {
	        PackageManager pm = this.getPackageManager();
			ai = pm.getApplicationInfo("com.adobe.flashplayer", 0);
			//Log.d("Stream", ai.toString());				
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    	
		CheckBoxPreference optInVideoPref = (CheckBoxPreference)findPreference("opt_in_video");

    	if (ai == null) {
    		optInVideoPref.setDefaultValue(false);
    	} else {
    		optInVideoPref.setDefaultValue(true);
    		//optInVideoPref.
    	}
        
        ListPreference downloadList = (ListPreference)findPreference("download_timing");
        downloadList.setSummary("Update streamer list every " + Integer.parseInt(downloadList.getValue())/60/1000 + " minute" + (Integer.parseInt(downloadList.getValue())/60/1000 > 1 ? "s" : ""));


        String ringtoneName = "Silent";
        String strRingtonePreference = sharedPref.getString("notify_tone", "DEFAULT_SOUND");
        if (!strRingtonePreference.equals("")) {
            Uri ringtoneUri = Uri.parse(strRingtonePreference);
            Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
            ringtoneName = ringtone.getTitle(this);  
        }
        RingtonePreference notify_tone = (RingtonePreference)findPreference("notify_tone");
        notify_tone.setSummary(ringtoneName);
        //notify_tone.setSummary(Uri.parse(strRingtonePreference));
        
		//ListPreference uploadList = (ListPreference)findPreference("upload_timing");
		//uploadList.setSummary("Update streamer list every " + Integer.parseInt(uploadList.getValue())/60/1000 + " minute" + (Integer.parseInt(uploadList.getValue())/60/1000 > 1 ? "s" : ""));
        
        PreferenceScreen screen = ((PreferenceScreen)findPreference("favorites_settings"));
        screen.removeAll();
        Map<String, ?> prefs = sharedPref.getAll();
        
		int streamerCount = 0;
        
		for(Map.Entry<String,?> entry : prefs.entrySet()){
			Log.d("Stream", entry.getKey() + ": " + entry.getValue());
			if (entry.getKey().matches(".*?~streamer~.*") && (Boolean)entry.getValue()) {
				//Log.d("Stream",entry.getKey() + ": " + entry.getValue().toString());
				CheckBoxPreference favoriteStreamer = new CheckBoxPreference(this);
				favoriteStreamer.setKey(entry.getKey());
				String[] stringParts = entry.getKey().split("~");
				favoriteStreamer.setTitle(stringParts[2]);
				favoriteStreamer.setChecked((Boolean) entry.getValue());
				
				streamerCount++;
				
				screen.addPreference(favoriteStreamer);
			}
		}
		
		screen.setSummary( "Following " + streamerCount + " streamer" + (streamerCount == 1 ? "" : "s") );
        
    }
    
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

    	Ringtone ringtone = RingtoneManager.getRingtone(this,  Uri.parse((String) newValue));

		RingtonePreference notify_tone = (RingtonePreference)findPreference("notify_tone");
		Log.d("Stream", ringtone.getTitle(this));
    	if (!ringtone.getTitle(this).equals("Unknown ringtone")) {
        	notify_tone.setSummary(ringtone.getTitle(this));
    	} else {
        	notify_tone.setSummary("Silent");    		
    	}
		
        return true;
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
		
		
		if (key.matches(".*?~streamer~.*")) {
			
	        PreferenceScreen screen = ((PreferenceScreen)findPreference("favorites_settings"));
			
			Map<String, ?> prefs = sharedPref.getAll();
			
			int streamerCount = 0;
	        
			for(Map.Entry<String,?> entry : prefs.entrySet()){
				//Log.d("Stream", "" + entry.getKey() + " : " + entry.getValue());
				if (entry.getKey().matches(".*?~streamer~.*") && (Boolean)entry.getValue())
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

	    // A patch to overcome OnSharedPreferenceChange not being called by RingtonePreference bug 
	    RingtonePreference pref = (RingtonePreference) findPreference("notify_tone");
	    pref.setOnPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
    
}