package com.LeagueWatch.Push;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	@Override
	protected void onError(Context context, String arg1) {
		Log.d("Stream", "Stuff1");
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMessage(Context context, Intent arg1) {
		Log.d("Stream", "Stuff2");
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onRegistered(Context context, String arg1) {
		Log.d("Stream", "Stuff3");
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onUnregistered(Context context, String arg1) {
		Log.d("Stream", "Stuff4");
		// TODO Auto-generated method stub
		
	}
	
}
