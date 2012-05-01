package com.LoLStreamBrowser;

import java.util.ArrayList;

public class Utilities {

	public static ArrayList<StreamerInfo> streamerListDifference(ArrayList<StreamerInfo> a, ArrayList<StreamerInfo> b) {
		
		for (int i = 0; i < b.size(); i++) {
			a.remove(b.get(i));
		}
		
		return a;
	}

	public static ArrayList<Integer> streamerListIntersectionPositions(ArrayList<StreamerInfo> a, ArrayList<StreamerInfo> b) {
		
		ArrayList<Integer> positions = new ArrayList<Integer>();
		
		for (int i = 0; i < b.size(); i++) {
			positions.add(a.indexOf(b.get(i)));
		}
				
		return positions;
	}
	
}
