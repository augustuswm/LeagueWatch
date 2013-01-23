package com.LeagueWatch.Data;

import java.util.ArrayList;
import java.util.Collections;

import com.LeagueWatch.OnTaskCompleted;
import com.LeagueWatch.RecentGameListFragment;
import com.LeagueWatch.Streamer;
import com.LeagueWatch.StreamerListFragment;
import com.LeagueWatch.UpdatableListFragment;

import android.R;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class DatabaseUpdater extends AsyncTask<Void, Void, ArrayList<Streamer>> {
	
	private UpdatableListFragment fragmentToUpdate;
	private FragmentActivity activity;
	private boolean running = false;
	private String fetchArg = null;
	
	public DatabaseUpdater (UpdatableListFragment updatableListFragment, FragmentActivity activity) {
		this.fragmentToUpdate = updatableListFragment;
		this.activity = activity;
	}
	
	public void updateFragment (String fetchArg) {
		if (fetchArg != null && fetchArg.length() > 0)
			this.fetchArg = fetchArg;
		
		if (!running)
			this.execute();
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (activity != null)
        	activity.setProgressBarIndeterminateVisibility(Boolean.TRUE); 
    }
	
	@Override
	protected ArrayList<Streamer> doInBackground(Void... params) {
		running = true;
		
		LeagueWatch t = new LeagueWatch(PreferenceManager.getDefaultSharedPreferences(activity));
		ArrayList<Streamer> s = t.fetch(fetchArg);
		Collections.sort(s);
		
		return s;
	}
	
	@Override
	protected void onPostExecute(ArrayList result) {
		
		fragmentToUpdate.listAdapter.setDatabase(result);
		//Collections.sort(result);
		fragmentToUpdate.listAdapter.notifyDataSetChanged();
		running = false;
		
        if (activity != null)
        	activity.setProgressBarIndeterminateVisibility(Boolean.FALSE); 
		
    }
	
}
