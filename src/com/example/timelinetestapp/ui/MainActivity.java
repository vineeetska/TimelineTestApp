package com.example.timelinetestapp.ui;

import java.util.ArrayList;
import java.util.Collections;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timelinetestapp.R;
import com.example.timelinetestapp.Callback.ICallback;
import com.example.timelinetestapp.model.TimelineModel;
import com.example.timelinetestapp.network.FetchTimelineTask;
import com.example.timelinetestapp.service.MediaFileDownlaodManager;
import com.example.timelinetestapp.ui.widget.PullToRefreshListView;
import com.example.timelinetestapp.util.ProjectUtil;

public class MainActivity extends ActionBarActivity implements ICallback{

	private PullToRefreshListView mListView;
	private TextView memptyView;
	private TimelineListAdapter mAdapter = null;
	private ArrayList<TimelineModel> mTimelineData = new ArrayList<TimelineModel>();;
	
	private FetchTimelineTask mFetchTimelineTask = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListView = (PullToRefreshListView)findViewById(R.id.timeLineList);
		
		memptyView = new TextView(this);
		memptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		memptyView.setText("No Transaction Found.");
		memptyView.setTextSize(18);
		memptyView.setTextColor(getResources().getColor( android.R.color.black ));
		memptyView.setVisibility(View.GONE);
		memptyView.setGravity(Gravity.CENTER_HORIZONTAL);
		((ViewGroup) mListView.getParent()).addView(memptyView);
		
		registerAvatarReceiver(true);
		
		mListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				getTimelineData(false);
			}
		});
		
		getTimelineData(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setAdapter(){
		if (mTimelineData != null && mTimelineData.size() > 0)
			Collections.sort(mTimelineData, Collections.reverseOrder());
		
		if (mAdapter == null){
			mAdapter = new TimelineListAdapter(getApplicationContext(), mTimelineData);
			mListView.setAdapter(mAdapter);
		}
		else{
			mAdapter.setListData(mTimelineData);
			mAdapter.notifyDataSetChanged();
		}
	}
	private void registerAvatarReceiver(boolean register){
		if (register){
			IntentFilter filter = new IntentFilter(MediaFileDownlaodManager.ACTION_DWN_UPDATE);
			filter.addAction(MediaFileDownlaodManager.ACTION_DWN_COMPLETE);
			registerReceiver(mAvatarDwnReceiver, filter);
		}
		else
			unregisterReceiver(mAvatarDwnReceiver);
	}
	private BroadcastReceiver mAvatarDwnReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(MediaFileDownlaodManager.ACTION_DWN_UPDATE)){
				mTimelineData = intent.getParcelableArrayListExtra("data");
				setAdapter();
			}
			else if (intent.getAction().equals(MediaFileDownlaodManager.ACTION_DWN_COMPLETE))
				registerAvatarReceiver(false);
		}
	};
	
	private void getTimelineData(boolean showSpinner){
		if (!ProjectUtil.isDataConnectionAvailable(getApplicationContext())){
			Toast.makeText(getApplicationContext(), "No internet connection available.", Toast.LENGTH_SHORT).show();
			return;
		}	
		
		if (mFetchTimelineTask == null){
			mFetchTimelineTask = new FetchTimelineTask(this, showSpinner);
			mFetchTimelineTask.execute();
		}
		else
			mListView.resetHeader();
	}
	@Override
	public void onResult(Object object) {
		// TODO Auto-generated method stub
		if (object != null){
			mTimelineData = (ArrayList<TimelineModel>) object;
			setAdapter();
			
			Intent intent = new Intent(getApplicationContext(), MediaFileDownlaodManager.class);
			intent.setAction(MediaFileDownlaodManager.ACTION_DOWNLOAD);
			intent.putParcelableArrayListExtra("data", mTimelineData);
			startService(intent);
		}
		
		mFetchTimelineTask = null;
		mListView.resetHeader();
		mListView.setEmptyView(memptyView);
	}
	
}
