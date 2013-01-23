package com.LeagueWatch;

import java.util.ArrayList;
import java.util.Collections;

import com.LeagueWatch.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StreamerAdapter extends UpdatableAdapter {

	LayoutInflater mInflater;
	private ArrayList<Streamer> database;
	public StreamerAdapter adapterToUpdate = this;
    SharedPreferences sharedPref;
	
	private class ViewHolder {
		protected String id;
		protected TextView name;
		protected TextView championName;
		protected TextView viewers;
		protected CheckBox favorite;
		protected ImageView featured;
		protected ImageView riot;
		protected LinearLayout actions;
	}

	public StreamerAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        this.database = new ArrayList<Streamer>();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mInflater;
            view = inflator.inflate(R.layout.streamer, null);
            
            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.featured = (ImageView) view.findViewById(R.id.featured);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.favorite = (CheckBox) view.findViewById(R.id.favoriteStreamCheckBox);
            viewHolder.viewers = (TextView) view.findViewById(R.id.viewers);
            viewHolder.championName = (TextView) view.findViewById(R.id.playing);
            
            if (viewHolder.favorite != null) {
            	
            	viewHolder.favorite.setOnClickListener(new OnClickListener() {

    	            @Override
    	            public void onClick(View v) {
    	            	Streamer element = (Streamer) viewHolder.favorite.getTag();
    	            	boolean isChecked = ((CheckBox) v).isChecked();
    	            	Editor e = null;
    	            	//boolean isFavorite = sharedPref.getBoolean(args.getString("streamer_id"), false);
    	            	if (element != null) {
    	            		e = sharedPref.edit();
        	            	if (e != null) {
        	            		e.putBoolean(element.getId() + "~streamer~" + element.getName(), isChecked);
        	            		e.commit();
        	            	}
        	            	
    	            		element.setFavorite(isChecked);
    	            		Collections.sort(database);
    	            		adapterToUpdate.notifyDataSetChanged();
    	            	}

    	            }
    	        });
    	    }
            
            view.setTag(viewHolder);
            if (viewHolder.favorite != null)
            	viewHolder.favorite.setTag(database.get(position));
            
        } else {
        	
            view = convertView;
            ViewHolder temp = ((ViewHolder) view.getTag());
            if (temp != null && temp.favorite != null)
            	temp.favorite.setTag(database.get(position));
            
        }
        
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.id = database.get(position).getId();
        holder.name.setText(database.get(position).getName());
        if (database.get(position).getChampionName() != null) {
        	holder.championName.setText("Playing " + database.get(position).getChampionName());
        	holder.championName.setVisibility(0);
        } else
        	holder.championName.setVisibility(8);
        holder.featured.setImageResource(database.get(position).getChampion());
        //holder.riot.setVisibility(database.get(position).getFeatured());
        holder.viewers.setText(database.get(position).getViewers() + " - " + database.get(position).getService() + ".tv");
        
        if (holder.favorite != null) {
        	//Log.d("Stream", "Check for favorite " + database.get(position).getId() + "~streamer~" + database.get(position).getName());
	    	boolean isFavorite = sharedPref.getBoolean(database.get(position).getId() + "~streamer~" + database.get(position).getName(), false);
	    	holder.favorite.setChecked(isFavorite);
	    }
        
        /*if (database.get(position).isSelected())
        	view.setBackgroundResource(R.drawable.highlight);
        else
        	view.setBackgroundResource(R.drawable.transparent);*/
        
		return view;
	}

	public void sort() {
		Collections.sort(database);
	}
	
	@Override
	public int getCount() {
		return database.size();
	}

	@Override
	public Object getItem(int position) {
		return database.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setDatabase(ArrayList database) {
		this.database = (ArrayList<Streamer>)database;
	}
	
}