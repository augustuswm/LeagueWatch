package com.LeagueWatch.Data;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.LeagueWatch.R;
import com.LeagueWatch.Streamer;

public class Own3d extends FetchStream {
	// We don't use namespaces
    private static final String ns = null;

	public Map<String, String> streamerMap = new HashMap<String, String>();

	public Own3d(String url) {
		super(url);
	}

	@Override
	public ArrayList<Streamer> fetch () {
		ArrayList<Streamer> db = new ArrayList<Streamer>();
		
		streamerMap.put("tsm_oddone", "theoddone");
		streamerMap.put("tsm_chaox", "shan huang");
		streamerMap.put("tsm_chaox", "dyrus");
		streamerMap.put("wingsofdeath", "wingsofdeathx");
		
		InputStream in = null;
		try {
			in = downloadUrl();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			try {
	            XmlPullParser parser = Xml.newPullParser();
	            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in, null);
	            parser.nextTag();
	            parser.nextTag();
	            
	            parser.require(XmlPullParser.START_TAG, ns, "channel");
	            while (parser.next() != XmlPullParser.END_TAG) {
	                if (parser.getEventType() != XmlPullParser.START_TAG) {
	                    continue;
	                }
	                String name = parser.getName();
	                // Starts by looking for the entry tag
	                if (name.equals("item")) {
	                	Streamer s = readEntry(parser);
						String elophantUrl = "";
						
	                    if (s.getViewers() > 100) {

							if (streamerMap.get(s.getName().toLowerCase()) != null) {
								elophantUrl = "http://elophant.com/api/v1/na/getInProgressGameInfo?summonerName="+URLEncoder.encode(streamerMap.get(s.getName().toLowerCase()), "utf-8")+"&key=7Gys2czGWadX16TPrvmC";
								Elophant e = new Elophant(elophantUrl);
								s.setChampion(e.fetchChampion(streamerMap.get(s.getName().toLowerCase())));
							} else {
								elophantUrl = "http://elophant.com/api/v1/na/getInProgressGameInfo?summonerName="+URLEncoder.encode(s.getName().toLowerCase(), "utf-8")+"&key=7Gys2czGWadX16TPrvmC";
								Elophant e = new Elophant(elophantUrl);
								s.setChampion(e.fetchChampion(s.getName().toLowerCase()));
							}
								
	                    	db.add(s);             
	                    }
	                } else {
	                    skip(parser);
	                }
	            }
	            
	        } catch (Exception e) {
				e.printStackTrace();
			} finally {
	            in.close();
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return db;
	}
	
	private Streamer readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
	    parser.require(XmlPullParser.START_TAG, ns, "item");

	    Streamer s = new Streamer();
	    
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        if (name.equals("title")) {
	            s.setName(readTag(parser, "title"));
	        } else if (name.equals("misc")) {
	            s.setViewers(Integer.parseInt(readAttribute(parser, "misc", "viewers")));
	        } else if (name.equals("link")) {
	            String idString = readTag(parser, "link");
	            s.setId("" + Long.getLong(idString.substring(idString.lastIndexOf("/"))));
	        } else {
	            skip(parser);
	        }
	    }

		s.setChampion(R.drawable.akali);
	    s.setService("Own3d");
	    
	    return s;
	}
	
	private String readTag (XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, tag);
	    String value = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, tag);
	    return value;
	}

	private String readAttribute (XmlPullParser parser, String tag, String attr) throws IOException, XmlPullParserException {
		String result = "";
	    parser.require(XmlPullParser.START_TAG, ns, "misc");
	    String tagName = parser.getName();
	    if (tagName.equals("misc")) {
	    	result = parser.getAttributeValue(null, "viewers");
            parser.nextTag();
	    }
	    parser.require(XmlPullParser.END_TAG, ns,  "misc");
	    return result;
	}
	
	private String readText (XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}
	
	private void skip (XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }

}
