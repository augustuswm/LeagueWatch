package com.LeagueWatch;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.LeagueWatch.Data.DatabaseUpdater;
import com.commonsware.cwac.merge.MergeAdapter;

public class RecentGameListFragment extends UpdatableListFragment {
		
	MergeAdapter adapter;
	private ThumbnailRetriever asyncRetrieveThumbnail;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        adapter = new MergeAdapter();
        View playerControls = getLayoutInflater(savedInstanceState).inflate(R.layout.player, null);
        listAdapter = new RecentGameAdapter(getActivity());
        
    	final Bundle args = getArguments();
    	if (args != null) {
    		asyncRetrieveThumbnail = new ThumbnailRetriever(args.getString("thumbnail"));
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
	            	
	            	if (getFragmentManager().findFragmentById(R.id.streamer_list_fragment) != null) {
		            	StreamerListFragment streamerList = (StreamerListFragment) getFragmentManager().findFragmentById(R.id.streamer_list_fragment);
		            	int streamerCount = streamerList.listAdapter.getCount();
		            	boolean listChanged = false;
		            		            	
		            	for (int i = 0; i < streamerCount; i++) {
		            		Streamer s = (Streamer) streamerList.listAdapter.getItem(i);
		            		if (s.getId().equals(args.getString("streamer_id"))) {
		            			listChanged = true;
		            			s.setFavorite(isChecked);
		            		}
		            	}
		            	
		            	if (listChanged) {
		            		((StreamerAdapter)streamerList.listAdapter).sort();
		            		streamerList.listAdapter.notifyDataSetChanged();
		            	}
	            	}

	            }
	        });
	        
	        ApplicationInfo ai = null;
	        
	    	try {
		        PackageManager pm = getActivity().getPackageManager();
				ai = pm.getApplicationInfo("com.adobe.flashplayer", 0);
				//Log.d("Stream", ai.toString());				
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    		
	    	if (ai != null || sharedPref.getBoolean("opt_in_video", false)) {
				
		        ImageView playButton = (ImageView) playerControls.findViewById(R.id.playButton);
		        
		        playButton.setOnClickListener(new ImageView.OnClickListener() {

					@Override
					public void onClick(View v) {
						
			            Fragment singlePaneTest = null;
			            singlePaneTest = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
					
			    		// Create fragment and give it an argument for the selected article
			            WatchStreamer video = new WatchStreamer();
			            if (args.getString("service").equals("Own3d")) {
			            	args.putString("url", "http://static.llnw.own3d.tv/player/Own3dPlayerV3_27.swf?config=liveembedcfg/"+args.getString("streamer_id")+";autoPlay=true");	
			            	Log.d("Stream","http://static.llnw.own3d.tv/player/Own3dPlayerV3_27.swf?config=liveembedcfg/"+args.getString("streamer_id")+";autoPlay=true");
			            } else {
			            	Log.d("Stream", "http://www.twitch.tv/"+args.getString("streamName")+"/popout");
				            if (singlePaneTest == null)
				            	args.putString("url", "http://www.twitch.tv/"+args.getString("streamName")+"/new");
				            else
				            	args.putString("url", "http://www.twitch.tv/"+args.getString("streamName")+"/popout");
			            }
			            //"http://static.llnw.own3d.tv/player/Own3dPlayerV3_11.swf?config=liveembedcfg/112348;autoPlay=true";
			            video.setArguments(args);
			            
			            
			            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
			
			            // Replace whatever is in the fragment_container view with this fragment,
			            // and add the transaction to the back stack so the user can navigate back
			            //transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
			            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			            if (singlePaneTest == null)
			            	transaction.replace(R.id.large_container, video);
			            else
			            	transaction.replace(R.id.fragment_container, video);
			            transaction.addToBackStack(null);
			            transaction.commit();
						
					}
		        	
		        });
		        
			} else {
				
				ImageView playButton = (ImageView) playerControls.findViewById(R.id.playButton);
				playButton.setVisibility(8);
				
			}
	    	
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
	        if (result != null && getActivity() != null && getActivity().findViewById(R.id.profileImage) != null)
	        	((ImageView)getActivity().findViewById(R.id.profileImage)).setImageDrawable(result);	
	    }
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	asyncRetrieveThumbnail.cancel(true);
    }
}
