package com.LoLStreamBrowser;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;


public class StreamerAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Bitmap mIcon1;
	private Context context;
	private Bitmap twitch;
	private Bitmap owned;
	private Bitmap riot;
	private ArrayList<StreamerInfo> database;
	
	public StreamerAdapter(Context context, ArrayList<StreamerInfo> database) {
		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(context);
		this.database = database;
		this.context = context;
		twitch = BitmapFactory.decodeResource(context.getResources(), R.drawable.twitchtv_logo);
		owned = BitmapFactory.decodeResource(context.getResources(), R.drawable.own3dtv_logo);
		riot = BitmapFactory.decodeResource(context.getResources(), R.drawable.riot_logo);
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mInflater;
            view = inflator.inflate(R.layout.streamer, null);
            
            final Streamer viewHolder = new Streamer();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.viewers = (TextView) view.findViewById(R.id.viewers);
            viewHolder.featured = (ImageView) view.findViewById(R.id.featured);
            
            viewHolder.favorite = (CheckBox) view.findViewById(R.id.favorite);
            viewHolder.favorite
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        	
                            StreamerInfo element = (StreamerInfo) viewHolder.favorite.getTag();
                            element.favorite = buttonView.isChecked();
                            
                            StreamerNotification.sendNote(context, element.name, Integer.toString(element.viewers));
                            
                            if (element.id != null) {
	                            if (buttonView.isChecked())
	                            	Settings.addFavorite(new Long(element.id));
	                            else
	                            	Settings.removeFavorite(new Long(element.id));
                            }

                        }
                    });
            
            view.setTag(viewHolder);
            viewHolder.favorite.setTag(database.get(position));
            
        } else {
        	
            view = convertView;
            ((Streamer) view.getTag()).favorite.setTag(database.get(position));
            
        }
        
        Streamer holder = (Streamer) view.getTag();
        holder.id = database.get(position).id;
        holder.name.setText(database.get(position).name);
        holder.viewers.setText(database.get(position).viewers + " - " + database.get(position).service + ".tv");
        holder.name.setText(database.get(position).name);
        holder.favorite.setChecked(database.get(position).favorite);
        
		return view;
	}
	
	static class Streamer {
		protected Long id;
		protected TextView name;
		protected TextView viewers;
		protected CheckBox favorite;
		protected ImageView featured;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return database.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return database.get(position);
	}
	
	public void update(ArrayList<StreamerInfo> database) {
		this.database = database;
		//this.notifyDataSetChanged();
	}
}
