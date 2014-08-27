package com.example.timelinetestapp.network;

import java.io.FileInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.timelinetestapp.Callback.ICallback;
import com.example.timelinetestapp.model.TimelineModel;
import com.example.timelinetestapp.parser.TimeLineParser;

public class FetchTimelineTask extends AsyncTask<Void, Void, Void>{

	private String url = "https://alpha-api.app.net/stream/0/posts/stream/global";
	protected ProgressDialog mProgressDialog;
	private ICallback mCallback;
	private Activity mActivity;
	private ArrayList<TimelineModel> mTimelineList = null;
	private boolean showSpinner = false;
	
	public FetchTimelineTask(Activity activity, boolean showSpinner) {
		// TODO Auto-generated constructor stub
		this.showSpinner = showSpinner;
		mCallback = (ICallback)activity;
		mActivity = activity;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if (showSpinner){
			mProgressDialog = ProgressDialog.show(mActivity, "", "Please wait...", true, true);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL | ProgressDialog.STYLE_SPINNER);
		}
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try{
			MyHttpClient client = new MyHttpClient();
			String file = client.getHttpResponse(mActivity.getApplicationContext(), url);
			if (file != null){
				TimeLineParser parser = new TimeLineParser();
				mTimelineList = parser.readTimelineStream(new FileInputStream(file));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if ( mProgressDialog != null )
			mProgressDialog.dismiss();
		
		if (mCallback != null)
			mCallback.onResult(mTimelineList);
	}

}
