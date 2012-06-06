package com.LeagueWatch;

import java.util.ArrayList;

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
		
		Twitch t = new Twitch("http://api.justin.tv/api/stream/list.json?category=gaming&meta_game=League%20of%20Legends");
		return t.fetch();
		
	}
	
	@Override
	protected void onPostExecute(ArrayList<Streamer> result) {
		fragmentToUpdate.listAdapter.database = result;
		fragmentToUpdate.listAdapter.notifyDataSetChanged();
		running = false;
    }	
	
}
