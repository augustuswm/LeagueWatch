package com.LeagueWatch;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecentGameAdapter extends UpdatableAdapter {

	LayoutInflater mInflater;
	private ArrayList<RecentGame> database;
	public RecentGameAdapter adapterToUpdate = this;
	
	private class ViewHolder {
		protected String id;
		protected ImageView championIcon;
		protected TextView score;
		protected ImageView[] items = new ImageView[6];
	}

	public RecentGameAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.database = new ArrayList<RecentGame>();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mInflater;
            view = inflator.inflate(R.layout.streamer, null);
            
            final ViewHolder viewHolder = new ViewHolder();
                        
            viewHolder.championIcon = (ImageView) view.findViewById(R.id.championIcon);
            viewHolder.score = (TextView) view.findViewById(R.id.score);
            viewHolder.items[0] = (ImageView) view.findViewById(R.id.item0);
            viewHolder.items[0] = (ImageView) view.findViewById(R.id.item1);
            viewHolder.items[0] = (ImageView) view.findViewById(R.id.item2);
            viewHolder.items[0] = (ImageView) view.findViewById(R.id.item3);
            viewHolder.items[0] = (ImageView) view.findViewById(R.id.item4);
            viewHolder.items[0] = (ImageView) view.findViewById(R.id.item5);
            
            view.setTag(viewHolder);
            
        } else {        	
            view = convertView;
        }
        
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.id = database.get(position).getId();
        holder.score.setText(database.get(position).getId());
        holder.championIcon.setImageResource(database.get(position).getChampion());
        
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
		this.database = (ArrayList<RecentGame>)database;
	}

}
