package com.LeagueWatch;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.LeagueWatch.Data.DatabaseUpdater;
import com.actionbarsherlock.app.SherlockListFragment;
import com.commonsware.cwac.merge.MergeAdapter;

public class RecentGameListFragment extends UpdatableListFragment {
		
	MergeAdapter adapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        adapter = new MergeAdapter();
        View playerControls = getLayoutInflater(savedInstanceState).inflate(R.layout.player, null);
        listAdapter = new RecentGameAdapter(getActivity());
        
    	final Bundle args = getArguments();
    	if (args != null) {
    		ThumbnailRetriever asyncRetrieveThumbnail = new ThumbnailRetriever(args.getString("thumbnail"));
    		asyncRetrieveThumbnail.retrieve();
    		((TextView)playerControls.findViewById(R.id.playerName)).setText(args.getString("name"));
        	
    		// Check to see if this is a favorite streamer
        	boolean isFavorite = sharedPref.getBoolean(args.getString("streamer_id") + "~streamer~" + args.getString("name"), false);
    		
    		CheckBox favoriteBox = (CheckBox) (playerControls.findViewById(R.id.favorite));
    		favoriteBox.setChecked(isFavorite);
	        
	        favoriteBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	            @Override
	            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	            	
	            	SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
	            	//boolean isFavorite = sharedPref.getBoolean(args.getString("streamer_id"), false);
	            	Editor e = pref.edit().putBoolean(args.getString("streamer_id") + "~streamer~" + args.getString("name"), isChecked);
	            	e.commit();
	            		            	
	                /*Streamer element = (Streamer) viewHolder.favorite.getTag();
	                element.favorite = buttonView.isChecked();
	                Collections.sort(database);
	                adapterToUpdate.notifyDataSetChanged();*/
	                
	                //Log.d("Stream", args.getString("name") + (isChecked ? " " : " un") + "favorited.");
	                
	                //if (element.id != null) {
	                //    if (buttonView.isChecked())
	                //    	Settings.addFavorite(new Long(element.id));
	                //    else
	                //    	Settings.removeFavorite(new Long(element.id));
	                //}

	            }
	        });
    		
            adapter.addView(playerControls);
    	}
    	
        adapter.addAdapter(listAdapter);
        updateDataSource(getActivity());
        setListAdapter(adapter);
        //setListAdapter(listAdapter);

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.recent_games_list, null);
	    return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    
    @Override
    public DatabaseUpdater updateDataSource(FragmentActivity activity) {
    	DatabaseUpdater dbUpdater = new DatabaseUpdater(this, activity);
    	Bundle args = getArguments();
    	if (args != null)
    		dbUpdater.updateFragment(args.getString("streamer_id"));
    	
    	return dbUpdater;
    }
    
    private class ThumbnailRetriever extends AsyncTask <Void, Void, Drawable> {
    	
    	private String url = "";
    	private boolean running = false;
    	
    	public ThumbnailRetriever(String url) {
    		this.url = url;
    	}

    	public Object fetch(String address) throws MalformedURLException,IOException {
    		URL url = new URL(address);
    		Object content = url.getContent();
    		return content;
    	}
    	
    	public void retrieve () {
    		if (!running)
    			this.execute();
    	}

		@Override
		protected Drawable doInBackground(Void... params) {
    		if (url == null)
    			return null;
    		try {
    			InputStream is = (InputStream) this.fetch(url);
    			Drawable d = Drawable.createFromStream(is, "src");
    			return d;
    		} catch (MalformedURLException e) {
    			e.printStackTrace();
    			return null;
    		} catch (IOException e) {
    			e.printStackTrace();
    			return null;
    		}
		}
		
		@Override
		protected void onPostExecute(Drawable result) {
			running = false;
	        //Drawable thumbnail = ImageOperations(playerThumbnail);
	        if (result != null)
	        	((ImageView)getActivity().findViewById(R.id.profileImage)).setImageDrawable(result);	
	    }
    }
}
