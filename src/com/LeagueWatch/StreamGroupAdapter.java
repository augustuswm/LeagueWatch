package com.LeagueWatch;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class StreamGroupAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	/**
	 * @param fm
	 * @param fragments
	 */
	public StreamGroupAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}
	
	public StreamGroupAdapter(FragmentManager fm, Object fragments2) {
		super(fm);
		this.fragments = null;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int position) {
        return StreamerListFragment.newInstance(position);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return 3;
	}

}
