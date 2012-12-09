package com.LeagueWatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.LeagueWatch.Data.DatabaseUpdater;
import com.actionbarsherlock.app.SherlockListFragment;

public class RecentGameListFragment extends UpdatableListFragment {
	
	private String url = "http://www.augustuswm.com/streamers";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listAdapter = new StreamerAdapter(getActivity());
        setListAdapter(listAdapter);

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
}
