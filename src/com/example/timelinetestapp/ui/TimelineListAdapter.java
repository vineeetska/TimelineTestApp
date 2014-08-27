package com.example.timelinetestapp.ui;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timelinetestapp.R;
import com.example.timelinetestapp.model.TimelineModel;
import com.example.timelinetestapp.util.ImageHelper;

public class TimelineListAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflator;
	private ArrayList<TimelineModel> mListData;
	
	public TimelineListAdapter(Context context, ArrayList<TimelineModel> data) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mInflator = LayoutInflater.from(context);
		mListData = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mListData != null)
			return mListData.size();
		return 0;
	}

	@Override
	public TimelineModel getItem(int position) {
		// TODO Auto-generated method stub
		return mListData.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
        if(convertView == null){
        	holder = new ViewHolder();
            convertView = mInflator.inflate(R.layout.list_layout, null);
            holder.avatar = (ImageView)convertView.findViewById(R.id.avatarPic);
            holder.posterName = (TextView)convertView.findViewById(R.id.posterName);
            holder.post = (TextView)convertView.findViewById(R.id.posterDesc);
            holder.time = (TextView)convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        
        if (getItem(position).getUser().getAvatarImage() != null &&
        		getItem(position).getUser().getAvatarImage().trim().length() > 0){
        	holder.avatar.setImageBitmap(ImageHelper.getRoundedCornerBitmap(
            		ImageHelper.decodeSampledBitmapFromFile(getItem(position).getUser().getAvatarImage(), 50, 50), 15));
        }
        else{
        	holder.avatar.setImageBitmap(ImageHelper.getRoundedCornerBitmap(
            		ImageHelper.decodeSampledBitmapFromResource(mContext.getResources(), R.drawable.noimage, 50, 50), 15));
        }
        
        holder.posterName.setText(getItem(position).getUser().getUsername());
        holder.post.setText(getItem(position).getPostText());
        holder.time.setText(getItem(position).getDateTime());
		return convertView;
	}
	
	class ViewHolder{
		ImageView avatar;
		TextView posterName;
		TextView post;
		TextView time;
	}

	public void setListData(ArrayList<TimelineModel> listData) {
		mListData = listData;
	}
	
	

	
}
