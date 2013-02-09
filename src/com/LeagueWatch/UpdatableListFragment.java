package com.LeagueWatch;

import android.support.v4.app.FragmentActivity;

import com.LeagueWatch.Data.DatabaseUpdater;
import com.actionbarsherlock.app.SherlockListFragment;

public class UpdatableListFragment extends SherlockListFragment {

	public UpdatableAdapter listAdapter;
    
    public DatabaseUpdater updateDataSource(FragmentActivity activity) {
    	DatabaseUpdater dbUpdater = new DatabaseUpdater(this, activity);
    	dbUpdater.updateFragment(null);
    	
    	return dbUpdater;
    }
	
}
