package com.LeagueWatch;

/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static com.LeagueWatch.Push.CommonUtilities.SENDER_ID;
import static com.LeagueWatch.Push.CommonUtilities.displayMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import com.LeagueWatch.R;
import com.LeagueWatch.Push.ServerUtilities;
import com.google.android.gcm.GCMBaseIntentService;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

    @SuppressWarnings("hiding")
    private static final String TAG = "Stream";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, getString(R.string.gcm_registered));
        ServerUtilities.register(context, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        //String message = getString(R.string.gcm_message);
        //displayMessage(context, message);
    	
        String streamerName = intent.getStringExtra("streamer_name");
        
    	NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    	NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
    	  
    	Intent launchIntent = new Intent(context, LeagueWatchActivity.class);
    	PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
    	
    	String name = streamerName;
		name = name.replace("_", " ");
		
	    Matcher m = Pattern.compile("(m5.benq|4not|crs|tsm|clg|sk|( [a-z])|(^[a-z]))").matcher(name);

	    StringBuilder s = new StringBuilder();
	    int last = 0;
	    while (m.find()) {
	        s.append(name.substring(last, m.start()));
	        s.append(m.group(0).toUpperCase());
	        last = m.end();
	    }
	    s.append(name.substring(last));
	    
	    name = s.toString();
	    
	    name = name.replaceAll("( -|\\(.*| \\d{4}\\+?.*|&quot;)", "");
		
		if (name.length() > 30)
			name = name.substring(0,24)+"...";
    	
    	String content = name + " started streaming.";
    	Spannable sb = new SpannableString( content );
    	sb.setSpan(new ForegroundColorSpan(Color.rgb(215, 215, 215)), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    	
		builder
		.setSmallIcon(R.drawable.ic_stat_example)
		.setContentTitle("Favorite streamer is now online")
		.setTicker(name + " started streaming.")
		.setContentText(sb)
		.setContentIntent(pendingIntent)
		.setAutoCancel(true);
		  
		notificationManager.notify(435147, builder.build());
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        //generateNotification(context, message);
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error, errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
    	
        /*int icon = R.drawable.ic_stat_gcm;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, DemoActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);*/
    }

}