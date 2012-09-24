package com.LeagueWatch.Data;

import java.util.ArrayList;
import java.util.Collections;

import com.LeagueWatch.Streamer;
import com.LeagueWatch.StreamerListFragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;

public class DatabaseUpdater extends AsyncTask<Void, Void, ArrayList<Streamer>> {
	
	private StreamerListFragment fragmentToUpdate;
	private boolean running = false;
	
	public DatabaseUpdater (StreamerListFragment f) {
		this.fragmentToUpdate = f;
	}
	
	public void updateFragment () {
		if (!running)
			this.execute();
	}
	
	@Override
	protected ArrayList<Streamer> doInBackground(Void... params) {
		running = true;
		
		Twitch t = new Twitch("https://api.twitch.tv/kraken/streams?game=League+of+Legends");
		Own3d o = new Own3d("http://api.own3d.tv/live.php?game=LoL");
		ArrayList<Streamer> s = t.fetch();
		s.addAll(o.fetch());
		Collections.sort(s);
		
		return s;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Streamer> result) {
		fragmentToUpdate.listAdapter.database = result;
		Collections.sort(result);
		fragmentToUpdate.listAdapter.notifyDataSetChanged();
		running = false;
    }	
	
}
