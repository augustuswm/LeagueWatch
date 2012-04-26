package com.LoLStreamBrowser;
 	  
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
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
	ScheduledExecutorService scheduler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

		try {
			File dir = new File(getExternalFilesDir(null), "streamers.db");
			ObjectInputStream i = new ObjectInputStream(new FileInputStream(dir));
			database = (ArrayList<StreamerInfo>)i.readObject();
		} catch (Exception e) {
			//e.printStackTrace();
		}

		Collections.sort(database);
		adap = new StreamerAdapter(this, database);
		setListAdapter(adap);
	}
	
	@Override
	public void onResume() {
        super.onResume();
        
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() { 
        	public void run() {
        		//Log.d("Stream", "Start Update");
        		database = Own3d.pullDown();
        		ArrayList<StreamerInfo> twitchDB = Twitch.pullDown();
        		database.addAll(twitchDB);
        		Collections.sort(database);
        		try {
        			File dir = new File(getExternalFilesDir(null), "streamers.db");
					ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(dir));
					o.writeObject(database);
					o.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		runOnUiThread(new Runnable() {
        		     public void run() {
        	        	adap.update(database);
        				adap.notifyDataSetChanged();
        		    }
        		});
        		//Log.d("Stream", "End Update");
        	} 
        }, 0, 180, TimeUnit.SECONDS);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		scheduler.shutdownNow();
	}
	
}