package com.LeagueWatch;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
            view = inflator.inflate(R.layout.recent_game, null);
            
            final ViewHolder viewHolder = new ViewHolder();
                        
            viewHolder.championIcon = (ImageView) view.findViewById(R.id.championIcon);
            viewHolder.score = (TextView) view.findViewById(R.id.score);
            viewHolder.items[0] = (ImageView) view.findViewById(R.id.item0);
            viewHolder.items[1] = (ImageView) view.findViewById(R.id.item1);
            viewHolder.items[2] = (ImageView) view.findViewById(R.id.item2);
            viewHolder.items[3] = (ImageView) view.findViewById(R.id.item3);
            viewHolder.items[4] = (ImageView) view.findViewById(R.id.item4);
            viewHolder.items[5] = (ImageView) view.findViewById(R.id.item5);
            
            view.setTag(viewHolder);
            
        } else {        	
            view = convertView;
        }
        
        ViewHolder holder = (ViewHolder) view.getTag();
        RecentGame game = database.get(position);
        holder.id = game.getId();
        //holder.score.setText();
        
        final SpannableStringBuilder sb = new SpannableStringBuilder(game.getKills() + " / " + game.getDeaths() + " / " + game.getAssists() + "\n" + game.isWinAsString());
        final ForegroundColorSpan fcs;
        if (game.isWin())
        	fcs = new ForegroundColorSpan(Color.rgb(0, 200, 0));
        else
        	fcs = new ForegroundColorSpan(Color.rgb(200, 0, 0));        	

        sb.setSpan(fcs, sb.length() - game.isWinAsString().length(), sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE); 

        holder.score.setText(sb);
        
        holder.championIcon.setImageResource(game.getChampion());
        
        for (int i = 0; i < 6; i++) {
        	int image = game.getItem(i);
            holder.items[i].setImageResource(image);
        }
        
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
	
	@Override
	public void setDatabase(ArrayList database) {
		this.database = database;
	}
	
	

}
