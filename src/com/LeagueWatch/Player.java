package com.LeagueWatch;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class Player extends SherlockFragment {
    final static String ARG_POSITION = "position", ARG_NAME = "name", ARG_THUMBNAIL = "thumnail", ARG_SERVICE = "service", ARG_ID = "id";
    int mCurrentPosition = -1;
    String currentName = "", currentThumbnail = "", streamService = "", streamId = "";
    
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
	        if (result != null) {
	        	//final double viewWidthToBitmapWidthRatio = (double)getActivity().findViewById(R.id.profileImage).getWidth() / (double)result.getMinimumWidth();
	        	//getActivity().findViewById(R.id.profileImage).getLayoutParams().height = (int) (result.getMinimumHeight() * viewWidthToBitmapWidthRatio);
	        	
	        	ImageView view = ((ImageView)getActivity().findViewById(R.id.profileImage));
	        	view.setImageDrawable(result);
	        	
	        }
	    }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	Log.d("Stream", "Create player view");
    	
        // Inflate the layout for this fragment

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        
    	//StreamerListFragment fragment = (StreamerListFragment)getFragmentManager().findFragmentById(R.id.streamers);
		//this.playerDetails = fragment.listAdapter;
		
		//Log.d("Stream", playerDetails.toString());
    	
        View playerView = inflater.inflate(R.layout.player, container, false);
        
        return playerView;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        /*Button watchButton = (Button)getActivity().findViewById(R.id.watch);
        
        watchButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				TextView playerName = (TextView) getActivity().findViewById(R.id.playerName);
			
	    		// Create fragment and give it an argument for the selected article
	            WatchStreamer video = new WatchStreamer();
	            Bundle args = new Bundle();
	            if (streamService.equals("Own3d")) {
	            	args.putString("url", "http://static.llnw.own3d.tv/player/Own3dPlayerV3_27.swf?config=liveembedcfg/"+streamId+";autoPlay=true");	
	            	Log.d("Stream","http://static.llnw.own3d.tv/player/Own3dPlayerV3_27.swf?config=liveembedcfg/"+streamId+";autoPlay=true");
	            } else {
	            	args.putString("url", "http://www.twitch.tv/"+currentName+"/popout");
	            }
	            //"http://static.llnw.own3d.tv/player/Own3dPlayerV3_11.swf?config=liveembedcfg/112348;autoPlay=true";
	            video.setArguments(args);
	            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
	
	            // Replace whatever is in the fragment_container view with this fragment,
	            // and add the transaction to the back stack so the user can navigate back
	            //transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
	            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	            transaction.replace(R.id.playerView, video);
	            transaction.addToBackStack(null);
	            transaction.commit();
				
			}
        	
        });*/

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
        	Log.d("Stream", "Player with args");
        	updatePlayerView(args.getInt(ARG_POSITION), args.getString(ARG_NAME), args.getString(ARG_THUMBNAIL), args.getString(ARG_SERVICE), args.getString(ARG_ID));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
        	Log.d("Stream", "Player no args");
        	updatePlayerView(mCurrentPosition, currentName, currentThumbnail, streamService, streamId);
        }
    }

    public void updatePlayerView(int position, String playerName, String playerThumbnail, String playerService, String playerId) {
        //StreamerListFragment streamerList = (StreamerListFragment)getFragmentManager().findFragmentById(R.id.streamers);
        ((TextView)getActivity().findViewById(R.id.playerName)).setText(playerName);

        ThumbnailRetriever asyncRetrieveThumbnail = new ThumbnailRetriever(playerThumbnail);
        asyncRetrieveThumbnail.retrieve();
        
        //article.setText(Ipsum.Articles[position]);
        mCurrentPosition = position;
        currentName = playerName;
        streamService = playerService;
        streamId = playerId;
        
        CheckBox favoriteBox = (CheckBox) (getActivity().findViewById(R.id.favorite));
        
        Log.d("Stream", "Checkbox created: " + favoriteBox);
        
        favoriteBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            	
                /*Streamer element = (Streamer) viewHolder.favorite.getTag();
                element.favorite = buttonView.isChecked();
                Collections.sort(database);
                adapterToUpdate.notifyDataSetChanged();*/
                
                Log.d("Stream", currentName + " favorited.");
                
                //if (element.id != null) {
                //    if (buttonView.isChecked())
                //    	Settings.addFavorite(new Long(element.id));
                //    else
                //    	Settings.removeFavorite(new Long(element.id));
                //}

            }
        });
        
        //currentThumbnail = playerThumbnail;
    }

}
