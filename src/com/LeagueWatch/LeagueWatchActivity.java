package com.LeagueWatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

import com.LeagueWatch.Push.CommonUtilities;
import com.LeagueWatch.Push.Favorites;
import com.LeagueWatch.Push.ServerUtilities;
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

	public Handler updateHandler;
	
	Updater r = null;
    
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getIntent() != null) {
			boolean clearPending = getIntent().getBooleanExtra("clearPending", false);
			
			if (clearPending) {
				clearPendingNotifications();
			}
		}
    	//GCMRegistrar.unregister(getApplicationContext());
		
		checkNotNull(CommonUtilities.SERVER_URL, "SERVER_URL");
        checkNotNull(CommonUtilities.SENDER_ID, "SERVER_ID");
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        //GCMRegistrar.checkManifest(this);
        //mDisplay = (TextView) findViewById(R.id.display);
        registerReceiver(mHandleMessageReceiver, new IntentFilter(CommonUtilities.DISPLAY_MESSAGE_ACTION));
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
            // Automatically registers application on startup.
        	//Log.d("Stream", "Register this device");
        	GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
        } else {
            // Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(this)) {
            	//GCMRegistrar.
                // Skips registration.
            	//Log.d("Stream", "Already registered");
                //mDisplay.append(getString(R.string.already_registered) + "\n");
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                //Log.d("Stream", "Register this device again");
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
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, f).commit();
        } else {

        	//Log.d("Stream", "Two pane layout");
        	
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
            getSupportFragmentManager().beginTransaction().add(R.id.streamer_list_fragment, f).commit();     	
            
            WelcomeFragment welcome = new WelcomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.recent_games_list_fragment, welcome);
            transaction.commit();
        	
        }

	    final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
	    //Editor editor = sharedPref.edit();
	    //editor.clear();
	    //editor.commit();
	    
	    r = new Updater() {

	    	@Override
			public void run() {
                Log.d("Stream", "\nRunning sync");
	    		try {
	                Favorites favoriteSync = new Favorites(regId, sharedPref);
	                // PerformBackgroundTask this class is the class that extends AsynchTask 
	                favoriteSync.execute();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	            }
	    		
	    		String downloadTimingPref = sharedPref.getString(Preferences.KEY_PREF_DOWNLOAD_TIMING, "");
				int timeout = !downloadTimingPref.equals("") ? Integer.parseInt(downloadTimingPref) : 60000;
		    	Message msg;
		    	msg = Message.obtain();
		    	msg.obj = true;
		    	updateHandler.sendMessage(msg);
		    	updateHandler.postDelayed(this, timeout);
			}
	    	
	    	@Override
			public void killRunnable() {
				updateHandler.removeCallbacks(this);
				this.killMe = true;
			}
	    	
	    	@Override
			public void revive() {
				updateHandler.post(this);
				this.killMe = false;
			}
	    	
	    	@Override
			public boolean isKilled() {
				return this.killMe;
			}
	    };
	    
	    updateHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	        	//Log.d("Stream", "Handling Message");
	        	///if (getActivity() != null) {
	    	    	//updateDataSource(getActivity());
				    
				    /*NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
				    Notification.Builder builder = new Notification.Builder(getActivity()).setContentTitle("New mail from").setContentText("Subject").setSmallIcon(R.drawable.ahri);
				    Notification noti = builder.getNotification();
				    notificationManager.notify(1, noti);*/
	        	//} else {
	        		//r.killRunnable();
	        	//}
	        }
	    };
	}
	
	private class Updater implements Runnable {
    	protected boolean killMe = false;

    	@Override
		public void run() {		
		}

		public void killRunnable() {
		}

		public void revive() {
		}
		
		public boolean isKilled() {
			return killMe;
		}
    }
	
    @Override
	public void onStreamerSelected(Streamer selectedStreamer) {
    	
        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout
    	Fragment playerFrag = getSupportFragmentManager().findFragmentById(R.id.recent_games_list_fragment);
    	
        //if (articleFrag != null) {
    	if (playerFrag != null) {
            // If article frag is available, we're in two-pane layout...

    		RecentGameListFragment recentGames = new RecentGameListFragment();
            Bundle args = new Bundle();
            args.putString("streamer_id", selectedStreamer.getId());
            args.putString("name", selectedStreamer.getName());
            args.putString("streamName", selectedStreamer.getStreamName());
            args.putString("thumbnail", selectedStreamer.getThumbnail());
            args.putString("service", selectedStreamer.getService());
            recentGames.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.recent_games_list_fragment, recentGames);
            //transaction.addToBackStack(null);
            transaction.commit();
    		
        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...
        	
        	getSupportActionBar().setTitle(selectedStreamer.getName());
        	
        	RecentGameListFragment recentGames = new RecentGameListFragment();
            Bundle args = new Bundle();
            args.putString("streamer_id", selectedStreamer.getId());
            args.putString("name", selectedStreamer.getName());
            args.putString("streamName", selectedStreamer.getStreamName());
            args.putString("thumbnail", selectedStreamer.getThumbnail());
            args.putString("service", selectedStreamer.getService());
            recentGames.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.fragment_container, recentGames);
            transaction.addToBackStack(null);
            transaction.commit();
        	
        }
    }
    
    public void showWelcome() {
    	Fragment playerFrag = getSupportFragmentManager().findFragmentById(R.id.recent_games_list_fragment);
	
	    //if (articleFrag != null) {
		if (playerFrag != null) {
	        // If article frag is available, we're in two-pane layout...
	
			WelcomeFragment welcome = new WelcomeFragment();
	        Bundle args = new Bundle();
	        welcome.setArguments(args);
	        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	
	        // Replace whatever is in the fragment_container view with this fragment,
	        // and add the transaction to the back stack so the user can navigate back
	        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
	        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	        transaction.replace(R.id.recent_games_list_fragment, welcome);
	        //transaction.addToBackStack(null);
	        transaction.commit();
			
	    } else {
	        // If the frag is not available, we're in the one-pane layout and must swap frags...
	    		    	
	    	WelcomeFragment welcome = new WelcomeFragment();
	        Bundle args = new Bundle();
	        welcome.setArguments(args);
	        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	
	        // Replace whatever is in the fragment_container view with this fragment,
	        // and add the transaction to the back stack so the user can navigate back
	        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
	        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	        transaction.replace(R.id.fragment_container, welcome);
	        transaction.addToBackStack(null);
	        transaction.commit();
	    	
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
    	// Handle item selection
        switch (item.getItemId()) {
            case R.id.Settings:
            	Intent intent = new Intent(this, Preferences.class);
                startActivity(intent);
                return true;
            /*case R.id.Register:
            	register();
                return true;
            case R.id.Unregister:
            	unregister();
                return true;
            case R.id.ClearPref:
            	clearPreferences();
                return true;
            case R.id.ClearPending:
            	clearPendingNotifications();
                return true;
            case R.id.Feedback:
                return true;*/
            case R.id.About:
            	showWelcome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void clearPreferences() {
    	SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);    	
    	Editor editor = sharedPref.edit();
    	editor.clear();
    	editor.commit();    	
    }
    
    public void clearPendingNotifications() {
    	SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);    	
    	Editor editor = sharedPref.edit();
    	editor.remove("pendingNotifications");
    	editor.commit();
    }
    
    public void register() {
    	checkNotNull(CommonUtilities.SERVER_URL, "SERVER_URL");
        checkNotNull(CommonUtilities.SENDER_ID, "SERVER_ID");
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        //GCMRegistrar.checkManifest(this);
        //mDisplay = (TextView) findViewById(R.id.display);
        registerReceiver(mHandleMessageReceiver, new IntentFilter(CommonUtilities.DISPLAY_MESSAGE_ACTION));
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
            // Automatically registers application on startup.
        	//Log.d("Stream", "Register this device");
        	GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
        } else {
            // Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(this)) {
            	//GCMRegistrar.
                // Skips registration.
            	//Log.d("Stream", "Already registered");
                //mDisplay.append(getString(R.string.already_registered) + "\n");
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                //Log.d("Stream", "Register this device again");
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
    }
    
    public void unregister() {
    	GCMRegistrar.unregister(this);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (r != null)
			r.revive();
		
		/*if (findViewById(R.id.fragment_container) != null) {
			f = (StreamerListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
			if (f != null && f.r != null)
				f.r.revive();			
		} else {
			f = (StreamerListFragment) getSupportFragmentManager().findFragmentById(R.id.streamer_list_fragment);
			if (f != null && f.r != null)
				f.r.revive();
		}*/
		//Log.d("Stream", "Resuming");
		//Log.d("Stream", "Fragment: " + f);
		//if (f != null)
		//	Log.d("Stream", "Runnable: " + f.r);
		//if (f != null && f.r != null)
			//Log.d("Stream", "Runnable is alive: " + (!f.r.isKilled()));
		//if (f != null && f.updateHandler != null && f.r != null)
			//f.updateHandler.post(f.r);
		//Log.d("Stream", "Resuming");
		/*if (dbUpdate == null)
	        dbUpdate = new DatabaseUpdater();
			
		dbUpdate.updateFragment(f);*/
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		if (r != null)
			r.killRunnable();
		
		/*if (findViewById(R.id.fragment_container) != null) {
			f = (StreamerListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
			if (f != null && f.r != null)
				f.r.killRunnable();			
		} else {
			f = (StreamerListFragment) getSupportFragmentManager().findFragmentById(R.id.streamer_list_fragment);
			if (f != null && f.r != null)
				f.r.killRunnable();
		}*/
		//Log.d("Stream", "Pausing");
		//Log.d("Stream", "Fragment: " + f);
		//if (f != null)
		//	Log.d("Stream", "Runnable: " + f.r);
		//if (f != null && f.r != null)
			//Log.d("Stream", "Runnable is dead: " + f.r.isKilled());
		
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