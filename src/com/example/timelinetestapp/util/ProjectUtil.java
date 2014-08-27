package com.example.timelinetestapp.util;

import java.io.File;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;

public class ProjectUtil {

	public static final String INTERNAL_DIR = "TimelineRes";
	
	public static boolean isDataConnectionAvailable(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null)
			return info.isConnected();

		return false;
	}
	
	public static long getAvailableInternalMemory() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();

		return (availableBlocks * blockSize)/1048576;
	}

	public static String createInternalAppStorage( Context context ) {
		String path = null;
		if ( getAvailableInternalMemory() > 100 ) {
			File folder = new File(context.getExternalCacheDir() + File.separator + ProjectUtil.INTERNAL_DIR);
			if ( !folder.isDirectory() )
				folder.mkdir();
			path = folder.getAbsolutePath();
		}

		return path;
	}
}
