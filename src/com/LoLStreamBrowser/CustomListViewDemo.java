package com.LoLStreamBrowser;
 	  
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListViewDemo extends ListActivity {
	
	private EfficientAdapter adap;
	private static String[] data = new String[] { "Scarra,132,Twitch,0,1", "Scarra,132,Own3d,0,1" };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		adap = new EfficientAdapter(this);
		setListAdapter(adap);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Toast.makeText(this, "Click-" + String.valueOf(position), Toast.LENGTH_SHORT).show();
	}
	
	public static class EfficientAdapter extends BaseAdapter implements Filterable {
		
		private LayoutInflater mInflater;
		private Bitmap mIcon1;
		private Context context;
		
		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
			this.context = context;
		}
		/**
		 * Make a view to hold each row.
		 * 
		 * @see android.widget.ListAdapter#getView(int, android.view.View,
		 *      android.view.ViewGroup)
		 */
		public View getView(final int position, View convertView, ViewGroup parent) {
			// A ViewHolder keeps references to children views to avoid
			// unneccessary calls
			// to findViewById() on each row.
			Streamer holder;
			// When convertView is not null, we can reuse it directly, there is
			// no need
			// to reinflate it. We only inflate a new View when the convertView
			// supplied
			// by ListView is null.
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.streamer, null);
				// Creates a ViewHolder and store references to the two children
				// views
				// we want to bind data to.
				holder = new Streamer();
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.viewers = (TextView) convertView.findViewById(R.id.viewers);
				holder.service = (ImageView) convertView.findViewById(R.id.service);
				holder.favorite = (ImageView) convertView.findViewById(R.id.favorite);
				holder.featured = (ImageView) convertView.findViewById(R.id.featured);
		
				convertView.setOnClickListener(new OnClickListener() {
					private int pos = position;
					
					@Override
					public void onClick(View v) {
						Toast.makeText(context, "Click-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();    
					}
				});
				
				/*holder.buttonLine.setOnClickListener(new OnClickListener() {
					private int pos = position;
					
					@Override
					public void onClick(View v) {
						Toast.makeText(context, "Delete-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();
					}
				});*/
		
				convertView.setTag(holder);
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (Streamer) convertView.getTag();
			}
			// Get flag name and id
			//String filename = "flag_" + String.valueOf(position);
			
			String[] streamerData = ((String) this.getItem(position)).split(",");
			
			//int id = context.getResources().getIdentifier(filename, "drawable", context.getString(R.string.package_str));
			// Icons bound to the rows.
			//if (id != 0x0) {
			if (streamerData[2].compareTo("Twitch") == 0)
				mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.twitchtv_logo);
			else	
				mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.own3dtv_logo);
			//}
			// Bind the data efficiently with the holder.
			holder.service.setImageBitmap(mIcon1);
			holder.name.setText(streamerData[0]);
			holder.viewers.setText(streamerData[1]);
			return convertView;
		}
		
		static class Streamer {
			TextView name;
			TextView viewers;
			ImageView service;
			ImageView favorite;
			ImageView featured;
		}
		
		@Override
		public Filter getFilter() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.length;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data[position];
		}
	}
}