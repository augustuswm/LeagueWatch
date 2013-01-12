package com.LeagueWatch;

import java.util.ArrayList;

import com.LeagueWatch.Data.DatabaseUpdater;
import com.actionbarsherlock.app.SherlockListFragment;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public  class StreamerListFragment extends UpdatableListFragment {

	SharedPreferences sharedPref;
	
    OnItemSelectedListener mCallback;
    
    public class Updater implements Runnable {
    	private boolean killMe = false;

    	@Override
		public void run() {
			if (this.killMe)
				return;
			else {
				Log.d("Stream", "Spawning new check");
				String downloadTimingPref = sharedPref.getString(Preferences.KEY_PREF_DOWNLOAD_TIMING, "");
				int timeout = !downloadTimingPref.equals("") ? Integer.parseInt(downloadTimingPref) : 60000;
		    	Message msg;
		    	msg = Message.obtain();
		    	msg.obj = true;
		    	updateHandler.sendMessage(msg);
		    	updateHandler.postDelayed(r, timeout);
			}
		
		}

		public void killRunnable() {
			updateHandler.removeCallbacks(r);
			this.killMe = true;
		}

		public void revive() {
			updateHandler.post(r);
			this.killMe = false;
		}
		
		public boolean isKilled() {
			return this.killMe;
		}
    }

    public interface OnItemSelectedListener {
        public void onStreamerSelected(Streamer position);
    }

	public Handler updateHandler;
	
	public Updater r = new Updater();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        listAdapter = new StreamerAdapter(getActivity());
        setListAdapter(listAdapter);
    	
        updateHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	        	//Log.d("Stream", "Handling Message");
	        	if (getActivity() != null) {
	    	    	updateDataSource(getActivity());
				    
				    /*NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
				    Notification.Builder builder = new Notification.Builder(getActivity()).setContentTitle("New mail from").setContentText("Subject").setSmallIcon(R.drawable.ahri);
				    Notification noti = builder.getNotification();
				    notificationManager.notify(1, noti);*/
	        	} else {
	        		r.killRunnable();
	        	}
	        }
	    };
	    
	    //updateHandler.post(r);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.streamer_list, null);
	    return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnItemSelectedListener");
        }
    }
    
    public DatabaseUpdater updateDataSource(FragmentActivity activity) {
    	DatabaseUpdater dbUpdater = new DatabaseUpdater(this, activity);
    	dbUpdater.updateFragment(null);
    	
    	return dbUpdater;
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item    	
    	
        mCallback.onStreamerSelected((Streamer)listAdapter.getItem(position));
        //((Streamer)listAdapter.getItem(position)).select();
    	
    	//getActivity().setContentView(R.layout.player);
    	
    	/*LinearLayout actions = (LinearLayout) v.findViewById(R.id.extras);
    		
    	if (actions.getVisibility() > 0)
    		actions.setVisibility(0);
    	else
    		actions.setVisibility(8);*/
    }
    
    @Override
	public void onPause() {
    	super.onPause();
    	r.killRunnable();
    }
    
    @Override
	public void onResume() {
    	super.onPause();
    	r.revive();
    }
}