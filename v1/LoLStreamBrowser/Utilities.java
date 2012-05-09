package com.LoLStreamBrowser;

import java.util.ArrayList;

public class Utilities {

	public static ArrayList<Object> streamerListDifference(ArrayList<Object> a, ArrayList<Object> b) {
		
		for (int i = 0; i < b.size(); i++) {
			a.remove(b.get(i));
		}
		
		return a;
	}

}
