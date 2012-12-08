package com.LeagueWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class StreamGroupAdapter extends FragmentPagerAdapter {

	private Map<Integer, StreamerListFragment> fragments = new HashMap<Integer, StreamerListFragment>();
	
	private static final String[] TITLES = new String[] { 
        "In Game", 
        "Popular", 
        "Tournaments" 
    };
	
	private static final String[] URLS = new String[] { 
        "http://www.augustuswm.com/streamers/ingame", 
        "http://www.augustuswm.com/streamers", 
        "http://www.augustuswm.com/streamers/tournaments" 
    };
	
	/*
	public StreamGroupAdapter(FragmentManager fm, Map<Integer, Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}*/
	
	/**
	 * @param fm
	 * @param fragments
	 */
	public StreamGroupAdapter(FragmentManager fm) {
		super(fm);
		//this.fragments = fragments;
	}
	
	/*public StreamGroupAdapter(FragmentManager fm, Object fragments2) {
		super(fm);
		this.fragments = new HashMap<Integer, Fragment>();
	}*/

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int position) {
        //StreamerListFragment f = StreamerListFragment.newInstance(position, TITLES[position], URLS[position]);
		//StreamerListFragment f = new StreamerListFragment();
        //fragments.put(Integer.valueOf(position), f);
        return null;
    	//return fragments.get(Integer.valueOf(position));
	}

	public void destroyItem(ViewGroup container, int position, Object object) {
	    super.destroyItem(container, position, object);
	    fragments.remove(position);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return 3;
		//return fragments.size();
	}

    @Override
	public String getPageTitle(int position) {
    	return TITLES[position];
    	//return ((StreamerListFragment)fragments.get(Integer.valueOf(position))).getTitle();
    }
    
    public StreamerListFragment getFragment(int key) {
        return (StreamerListFragment) fragments.get(key);
    }

}
