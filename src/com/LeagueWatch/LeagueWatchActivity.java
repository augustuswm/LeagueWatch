package com.LeagueWatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

import com.LeagueWatch.Push.ServerUtilities;
import com.LeagueWatch.Push.CommonUtilities;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import com.google.android.gcm.GCMRegistrar;

public class LeagueWatchActivity extends SherlockFragmentActivity implements StreamerListFragment.OnItemSelectedListener {
		
	private StreamerListFragment f;
    StreamGroupAdapter mAdapter;
    ViewPager mPager;
    AsyncTask<Void, Void, Void> mRegisterTask;
    
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		checkNotNull(CommonUtilities.SERVER_URL, "SERVER_URL");
        checkNotNull(CommonUtilities.SENDER_ID, "SERVER_ID");
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);
        //mDisplay = (TextView) findViewById(R.id.display);
        registerReceiver(mHandleMessageReceiver, new IntentFilter(CommonUtilities.DISPLAY_MESSAGE_ACTION));
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
            // Automatically registers application on startup.
            Log.d("Stream", "Register this device");
        	GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
        } else {
            // Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(this)) {
            	//GCMRegistrar.
                // Skips registration.
            	Log.d("Stream", "Already registered");
                //mDisplay.append(getString(R.string.already_registered) + "\n");
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                Log.d("Stream", "Register this device again");
                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        ServerUtilities.register(context, regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }
		
				
		/*GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, "807621216747");
		} else {
			Log.d("Stream", "Already registered");
		}*/

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        
        setContentView(R.layout.main);

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            f = new StreamerListFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            f.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, f).commit();
        }
        
        
		
	}

    public void onStreamerSelected(Streamer selectedStreamer) {
        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout
        Player playerFrag = (Player)getSupportFragmentManager().findFragmentById(R.id.players);

        //if (articleFrag != null) {
    	if (playerFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
    		
    		playerFrag.updatePlayerView(0, selectedStreamer.getName(), selectedStreamer.getThumbnail(), selectedStreamer.getService(), selectedStreamer.getId());
    		
    		/*if (playerFrag instanceof Player)
    			playerFrag.updatePlayerView(position);
    		else {*/

    	    	/*StreamerListFragment streamerlist = (StreamerListFragment)getSupportFragmentManager().findFragmentById(R.id.streamers);
    			String playerid = ((Streamer)streamerlist.listAdapter.getItem(position)).getId();
    			String playername = ((Streamer)streamerlist.listAdapter.getItem(position)).getId();*/
    		
	    		// Create fragment and give it an argument for the selected article
	            /*WatchStreamer video = new WatchStreamer();
	            Bundle args = new Bundle();
	            args.putString("url", "http://www.twitch.tv/"+"robertxlee"+"/popout");
	            video.setArguments(args);
	            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	
	            // Replace whatever is in the fragment_container view with this fragment,
	            // and add the transaction to the back stack so the user can navigate back
	            //transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
	            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	            transaction.replace(R.id.players, video);
	            transaction.addToBackStack(null);
	            transaction.commit();*/
    		//}
    		

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...
        	
            getSupportActionBar().setTitle(selectedStreamer.getName());
        	
            // Create fragment and give it an argument for the selected article
            Player player = new Player();
            Bundle args = new Bundle();
            args.putInt(Player.ARG_POSITION, 0);
            args.putString(Player.ARG_NAME, selectedStreamer.getName());
            args.putString(Player.ARG_THUMBNAIL, selectedStreamer.getThumbnail());
            args.putString(Player.ARG_SERVICE, selectedStreamer.getService());
            args.putString(Player.ARG_ID, selectedStreamer.getId());
            player.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
          
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            //transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.streamers, player);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
                        
            //player.updatePlayerView(position, playername);
        }
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getSupportMenuInflater();
       inflater.inflate(R.menu.menu, menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		if (f != null && f.updateHandler != null && f.r != null)
			f.updateHandler.post(f.r);
		Log.d("Stream", "Resuming");
		/*if (dbUpdate == null)
	        dbUpdate = new DatabaseUpdater();
			
		dbUpdate.updateFragment(f);*/
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (f != null && f.r != null)
			f.r.killRunnable();
		Log.d("Stream", "Pausing");
		/*if (dbUpdate == null)
	        dbUpdate = new DatabaseUpdater();
			
		dbUpdate.updateFragment(f);*/
	}

    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        unregisterReceiver(mHandleMessageReceiver);
        GCMRegistrar.onDestroy(this);
        super.onDestroy();
    }

    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            //throw new NullPointerException(getString(R.string.error_config, name));
            throw new NullPointerException("");
        }
    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            //mDisplay.append(newMessage + "\n");
        }
    };

		
}