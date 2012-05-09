package com.LeagueWatch;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class StreamerListFragment extends ListFragment {
	// ListFragment is a very useful class that provides a simple ListView inside of a Fragment.
    // This class is meant to be sub-classed and allows you to quickly build up list interfaces
    // in your app.
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        Activity activity = getActivity();
        
        if (activity != null) {
            // Create an instance of the custom adapter for the GridView. A static array of location data
            // is stored in the Application sub-class for this app. This data would normally come
            // from a database or a web service.
            ListAdapter listAdapter = new StreamerAdapter(activity, new ArrayList<Streamer>());
            setListAdapter(listAdapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity activity = getActivity();
        
        if (activity != null) {   
            ListAdapter listAdapter = getListAdapter();
            Streamer locationModel = (Streamer) listAdapter.getItem(position);
        }
    }
}
