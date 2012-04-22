package com.LoLStreamBrowser;
 	  
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// Twitch key: 742fkrw94uwv3rc34f9q5lcvx


public class CustomListViewDemo extends ListActivity {
	
	private StreamerAdapter adap;
	private static ArrayList<StreamerInfo> database = new ArrayList<StreamerInfo>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		database = Twitch.pullDown();
		Collections.sort(database);
        
		adap = new StreamerAdapter(this, database);
		setListAdapter(adap);
		
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() { 
        	public void run() {
        		Log.d("Stream", "Start Update");
        		database = Twitch.pullDown();
        		Collections.sort(database);
        		adap.update(database);
        		Log.d("Stream", "End Update");
        	} 
        }, 60, 60, TimeUnit.SECONDS);
        
	}
	
}