package com.LoLStreamBrowser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;

public class Settings {
	private static ArrayList<Long> favorites = new ArrayList<Long>();
	private static Context context;
	
	public static void initialize(Context pContext) {

		context = pContext;
		
		try {
			File dir = new File(context.getExternalFilesDir(null), "user.settings");
			ObjectInputStream i = new ObjectInputStream(new FileInputStream(dir));
			favorites = (ArrayList<Long>)i.readObject();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public static boolean isFavorite(Long streamer) {
		return favorites.contains(streamer);
	}
	
	public static boolean isFavorite(StreamerInfo streamer) {
		return favorites.contains(streamer.id);
	}
	
	public static ArrayList<StreamerInfo> areFavorites(ArrayList<StreamerInfo> a) {
		Iterator<StreamerInfo> i = a.iterator();
		while (i.hasNext()) {
			StreamerInfo b = i.next();
			if (!isFavorite(b)) {
				a.remove(b);
			}
		}
		return a;
	}
	
	public static void addFavorite(Long streamer) {
		if (!favorites.contains(streamer)) {
			favorites.add(streamer);
		}
	}
	
	public static void removeFavorite(Long streamer) {
		favorites.remove(streamer);
	}
	
	public static void save() {
		File dir = new File(context.getExternalFilesDir(null), "user.setttings");
		ObjectOutputStream o;
		
		try {
			o = new ObjectOutputStream(new FileOutputStream(dir));
			o.writeObject(favorites);
			o.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
