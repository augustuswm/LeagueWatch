package com.LeagueWatch;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class StreamerListFragment extends ListFragment {
	// ListFragment is a very useful class that provides a simple ListView inside of a Fragment.
    // This class is meant to be sub-classed and allows you to quickly build up list interfaces
    // in your app.
	public StreamerAdapter listAdapter;
	private DatabaseUpdater dbUpdate = null;
		
	/**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    static StreamerListFragment newInstance(int num) {
    	StreamerListFragment f = new StreamerListFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }
    
	/**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listAdapter = new StreamerAdapter(getActivity());
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.streamer_list, container, false);
        View tv = v.findViewById(R.id.text);
        dbUpdate = new DatabaseUpdater(this);
        dbUpdate.updateFragment();
        return v;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(listAdapter);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {   
    }
}