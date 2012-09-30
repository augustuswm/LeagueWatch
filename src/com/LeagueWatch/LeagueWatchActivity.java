package com.LeagueWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TitlePageIndicator;

import com.google.android.gcm.GCMRegistrar;

public class LeagueWatchActivity extends SherlockFragmentActivity {
		
	/** maintains the pager adapter*/
	private StreamGroupAdapter mPagerAdapter;
	private StreamerListFragment f;
    StreamGroupAdapter mAdapter;
    ViewPager mPager;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
		  GCMRegistrar.register(this, "807621216747");
		} else {
		  Log.d("Registration", "Already registered");
		}
		
        setContentView(R.layout.pager);
        
        mAdapter = new StreamGroupAdapter(getSupportFragmentManager(), null);

        mPager = (ViewPager)findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(1);
        
        //Bind the title indicator to the adapter
        TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.titles);
        titleIndicator.setViewPager(mPager);
        
        //register();
		//initialsie the pager
	}
	
	protected void onResume() {
		super.onResume();
		/*if (dbUpdate == null)
	        dbUpdate = new DatabaseUpdater();
			
		dbUpdate.updateFragment(f);*/
	}
	
	/*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbUpdate = new DatabaseUpdater();
        f = new StreamerListFragment();
        
     	// We default to building our Fragment at runtime, but you can switch the layout here
        // to R.layout.activity_fragment_xml in order to have the Fragment added during the
        // Activity's layout inflation.
        setContentView(R.layout.main);
        
        FragmentManager fm       = getSupportFragmentManager();
        Fragment        fragment = fm.findFragmentById(R.id.streamer_list); // You can find Fragments just like you would with a 
                                                                               // View by using FragmentManager.
        // More comments
        // If we are using activity_fragment_xml.xml then this the fragment will not be
        // null, otherwise it will be.
        if (fragment == null) {
            
            // We alter the state of Fragments in the FragmentManager using a FragmentTransaction. 
            // FragmentTransaction's have access to a Fragment back stack that is very similar to the Activity
            // back stack in your app's task. If you add a FragmentTransaction to the back stack, a user 
            // can use the back button to undo a transaction. We will cover that topic in more depth in
            // the second part of the tutorial.
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.streamer_list, f);
            ft.commit(); // Make sure you call commit or your Fragment will not be added. 
                         // This is very common mistake when working with Fragments!
        }
        
    }*/
	
}