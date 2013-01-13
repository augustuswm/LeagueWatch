package com.LeagueWatch.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.LeagueWatch.Streamer;

import android.util.Log;

public class FetchStream {

	private String url;
	
	public FetchStream () {
	}
	
	public void setURL (String url) {
		this.url = url;
	}

	public ArrayList fetch(String arg) {
		return null;
	}
	
	public String getJSON() {
		
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.d("Stream", "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	protected InputStream downloadUrl() throws IOException {
	    URL uri = new URL(url);
	    HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
	    conn.setReadTimeout(10000 /* milliseconds */);
	    conn.setConnectTimeout(15000 /* milliseconds */);
	    conn.setRequestMethod("GET");
	    conn.setDoInput(true);
	    // Starts the query
	    conn.connect();
	    InputStream stream = conn.getInputStream();
		return stream;      
	}
	
}