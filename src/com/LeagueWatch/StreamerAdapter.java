package com.LeagueWatch;

import java.util.ArrayList;
import java.util.Collections;

import com.LeagueWatch.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
        this.database = new ArrayList<Streamer>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mInflater;
            view = inflator.inflate(R.layout.streamer, null);
            
            final ViewHolder viewHolder = new ViewHolder();
                        
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.championName = (TextView) view.findViewById(R.id.playing);
            viewHolder.featured = (ImageView) view.findViewById(R.id.featured);
            //viewHolder.viewers = (TextView) view.findViewById(R.id.viewers);
            //viewHolder.riot = (ImageView) view.findViewById(R.id.riotfist);
            viewHolder.riot = (ImageView) view.findViewById(R.id.riotfist);
            viewHolder.viewers = (TextView) view.findViewById(R.id.viewers);
            
            /*viewHolder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                	
                    Streamer element = (Streamer) viewHolder.favorite.getTag();
                    element.favorite = buttonView.isChecked();
                    Collections.sort(database);
                    adapterToUpdate.notifyDataSetChanged();
                    
                    /*if (element.id != null) {
                        if (buttonView.isChecked())
                        	Settings.addFavorite(new Long(element.id));
                        else
                        	Settings.removeFavorite(new Long(element.id));
                    }*

                }
            });*/
            
            view.setTag(viewHolder);
            //viewHolder.favorite.setTag(database.get(position));
            
        } else {
        	
            view = convertView;            
            //((ViewHolder) view.getTag()).favorite.setTag(database.get(position));
            
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
        holder.riot.setVisibility(database.get(position).getFeatured());
        holder.viewers.setText(database.get(position).getViewers() + " - " + database.get(position).getService() + ".tv");
        
        /*if (position % 2 == 1)
        	view.setBackgroundResource(R.drawable.listitemalternate);
        else
        	view.setBackgroundResource(R.drawable.listitem);*/
        
		return view;
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