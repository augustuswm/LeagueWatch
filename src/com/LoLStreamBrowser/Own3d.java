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

public class Own3d {

	public static ArrayList<StreamerInfo> pullDown() {
		
		ArrayList<StreamerInfo> database = new ArrayList<StreamerInfo>(); 

		DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://api.own3d.tv/live.php?game=LoL");
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
		String curText = "";
		
		while (eventType != XmlPullParser.END_DOCUMENT) {
			//Log.d("Stream", Integer.toString(eventType));
			//if (eventType == XmlPullParser.START_TAG)
			//Log.d("Stream", Integer.toString(eventType) + " " + XmlPullParser.END_TAG + " " + xpp.getName() + " " + curText);
			if (eventType == XmlPullParser.START_TAG) {
				curText = "";
				
				if (xpp.getName().compareTo("item") == 0) {
					if (s != null && s.viewers > 10)
						database.add(s);
					s = new StreamerInfo();
					s.service = "Own3d";
					s.favorite = false;
					s.featured = false;
				}
				
				if (xpp.getName().compareTo("misc") == 0) {
					s.viewers = Integer.valueOf(xpp.getAttributeValue(2));
				}
			}
						
			if (eventType == XmlPullParser.TEXT) {
				curText += xpp.getText();
			}

			if (eventType == XmlPullParser.END_TAG) {
				String tagName = xpp.getName();
				if (tagName.compareTo("title") == 0) {
					try {
						s.name = curText;
					} catch (Exception e) {}
				}
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
