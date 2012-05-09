package com.LoLStreamBrowser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class Twitch {

	public static ArrayList<StreamerInfo> pullDown() {
		
		ArrayList<StreamerInfo> database = new ArrayList<StreamerInfo>(); 

		DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://api.justin.tv/api/stream/list.xml?category=gaming&meta_game=League%20of%20Legends");
        HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        BufferedReader in = null;
		try { 
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        XmlPullParserFactory factory = null;
		try {
			factory = XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        factory.setNamespaceAware(true);
        XmlPullParser xpp = null;
		try {
			xpp = factory.newPullParser();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
			xpp.setInput( in );
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int eventType = 0;
		try {
			eventType = xpp.getEventType();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StreamerInfo s = null;
		String openTag = null;
		String curText = "";
		
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG && xpp.getName().compareTo("stream") == 0) {
				if (s != null && s.viewers > 10) {
					if (Settings.isFavorite(new Long(s.id)))
						s.favorite = true;
					database.add(s);
				}
				s = new StreamerInfo();
				s.service = "Twitch";
				s.favorite = false;
				s.featured = false;
			}
			
			if (eventType == XmlPullParser.START_TAG) {
				openTag = xpp.getName();
				curText = "";
			}
			
			if (eventType == XmlPullParser.TEXT) {
				curText += xpp.getText();
			}

			if (eventType == XmlPullParser.END_TAG) {
				String tagName = xpp.getName();
				if (tagName.compareTo("title") == 0)
					s.name = curText;
				else if (tagName.compareTo("channel_count") == 0)
					s.viewers = Integer.valueOf(curText.trim());
				else if (tagName.compareTo("id") == 0)
					s.id = Long.valueOf(curText.trim());
			}
			
			try {
				eventType = xpp.next();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return database;
	}
	
}
