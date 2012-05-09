package com.LoLStreamBrowser;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class StreamerNotification {
	private static final int HELLO_ID = 1;
	
	public static void sendNote(Context context, String title, String text) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		
		int icon = R.drawable.twitch_t;
		CharSequence tickerText = title + " is now online";
		long when = System.currentTimeMillis();
	
		Notification notification = new Notification(icon, tickerText, when);
		
		CharSequence contentTitle = title;
		CharSequence contentText = text;
		Intent notificationIntent = new Intent(context, CustomListViewDemo.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
	
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	
		mNotificationManager.notify(HELLO_ID, notification);
	}
}
