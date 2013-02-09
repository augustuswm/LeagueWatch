package com.LeagueWatch;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class WelcomeFragment extends SherlockFragment {

	ImageAdapter imageAdapter;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	 }
	 
	 @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome,container,false);
        TextView feedback = (TextView) view.findViewById(R.id.feedbackLink);
        Linkify.addLinks(feedback, Linkify.EMAIL_ADDRESSES);
        //GridView gridView = (GridView) view.findViewById(R.id.gridview);
        //gridView.setAdapter(new ImageAdapter(view.getContext())); // uses the view to get the context instead of getActivity().
        return view;
    }

	 @Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	     super.onActivityCreated(savedInstanceState);
	 }

    @Override
    public void onStart() {
        super.onStart();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    }
	
}
