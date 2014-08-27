package com.example.timelinetestapp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.timelinetestapp.model.TimelineModel;
import com.example.timelinetestapp.util.ProjectUtil;

public class MediaFileDownlaodManager extends IntentService {

	public static final String ACTION_DOWNLOAD = "com.example.timelinetestapp.ACTION_DOWNLOAD";
	public static final String ACTION_DWN_UPDATE = "com.example.timelinetestapp.ACTION_DWN_UPDATE";
	public static final String ACTION_DWN_COMPLETE = "com.example.timelinetestapp.ACTION_DWN_COMPLETE";

	public MediaFileDownlaodManager() {
		// TODO Auto-generated constructor stub
		super("MediaFileDownlaodManager");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("MediaFileDownlaodManager", "started");

		String action = intent.getAction();
		
		if ( !( ProjectUtil.getAvailableInternalMemory() > 100 ) 
				|| !ProjectUtil.isDataConnectionAvailable(this.getApplicationContext())) {
			return;
		}
		
		if (action.equals(ACTION_DOWNLOAD)){
			ArrayList<TimelineModel> data = intent.getParcelableArrayListExtra("data");
			if (data != null && data.size() > 0){
				String path = null;
				for (int i = 0; i < data.size(); i++){
					path = downloadFileFromWebServer(data.get(i).getUser().getAvatarUrl());
					if (path != null){
						data.get(i).getUser().setAvatarImage(path);
						Intent updateIntent = new Intent(ACTION_DWN_UPDATE);
						updateIntent.putParcelableArrayListExtra("data", data);
						sendBroadcast(updateIntent);
					}
				}
				sendBroadcast(new Intent(ACTION_DWN_COMPLETE));
			}
		}

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	/**
	 *
	 * @param sourceurl
	 * @return
	 */
	private String downloadFileFromWebServer(String sourceurl)  {
		String dest = null;
		if(sourceurl == null || sourceurl.equals("") )
			return dest;

		HttpURLConnection urlConnection = null;
		FileOutputStream fileOutput = null;
		try
		{
			URL url = new URL(sourceurl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			//			urlConnection.setDoOutput(true);
			urlConnection.connect();

			File file = new File( ProjectUtil.createInternalAppStorage(getApplicationContext())  + "/" + getFileName(sourceurl) );

			if( !file.exists())
				file.createNewFile();
			else
				return file.getAbsolutePath();

			dest = file.getAbsolutePath();

			fileOutput = new FileOutputStream(file);
			InputStream inputStream = urlConnection.getInputStream();
			int downloadedSize = 0;

			byte[] buffer = new byte[1024];
			int bufferLength = 0;

			while ( (bufferLength = inputStream.read(buffer)) > 0 )
			{
				fileOutput.write( buffer, 0, bufferLength );
				downloadedSize += bufferLength;
			}
		}catch (Exception e){
			urlConnection.disconnect();
			dest = null;
		} finally{
			if(fileOutput != null){
				try {
					fileOutput.close();
				} catch (IOException e) {
				}
			}

			if( urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return dest;
	}

	private String getFileName( String path ){
		if(path == null || path.equals("")) return path;
		String fileName = null;
		if( path.indexOf("\\") > 0){
			fileName = ( path.substring(path.lastIndexOf("\\")) );
		} else if( path.indexOf("/") > 0){
			fileName = ( path.substring(path.lastIndexOf("/") + 1 ) );
		}
		return fileName + ".jpg";
	}

}
