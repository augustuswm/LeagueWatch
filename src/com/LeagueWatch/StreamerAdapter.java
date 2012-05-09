package com.LeagueWatch;

import java.util.ArrayList;

import com.LeagueWatch.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StreamerAdapter extends BaseAdapter {

	private Context context;
	LayoutInflater mInflater;
	private ArrayList<Streamer> database;
	
	private class ViewHolder {
		protected Long id;
		protected TextView name;
		protected TextView viewers;
		protected CheckBox favorite;
		protected ImageView featured;
	}
	
	public StreamerAdapter(Context context, ArrayList<Streamer> database) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.database = database;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mInflater;
            view = inflator.inflate(R.layout.streamer, null);
            
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.viewers = (TextView) view.findViewById(R.id.viewers);
            viewHolder.featured = (ImageView) view.findViewById(R.id.featured);
            
            viewHolder.favorite = (CheckBox) view.findViewById(R.id.favorite);
            viewHolder.favorite
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        	
                            Streamer element = (Streamer) viewHolder.favorite.getTag();
                            element.favorite = buttonView.isChecked();
                                                        
                            /*if (element.id != null) {
	                            if (buttonView.isChecked())
	                            	Settings.addFavorite(new Long(element.id));
	                            else
	                            	Settings.removeFavorite(new Long(element.id));
                            }*/

                        }
                    });
            
            view.setTag(viewHolder);
            viewHolder.favorite.setTag(database.get(position));
            
        } else {
        	
            view = convertView;
            ((ViewHolder) view.getTag()).favorite.setTag(database.get(position));
            
        }
        
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.id = database.get(position).id;
        holder.name.setText(database.get(position).name);
        holder.viewers.setText(database.get(position).viewers + " - " + database.get(position).service + ".tv");
        holder.name.setText(database.get(position).name);
        holder.favorite.setChecked(database.get(position).favorite);
        
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
	
}