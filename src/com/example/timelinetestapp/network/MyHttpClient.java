package com.example.timelinetestapp.network;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;

import com.example.timelinetestapp.util.ProjectUtil;

public class MyHttpClient {

	public String getHttpResponse(Context context, String sourceurl) throws IllegalStateException, Exception {
		String dest = null;
		InputStream in = null;
		HttpURLConnection urlConnection = null;
		FileOutputStream fileOutput = null;
		try{
			URL url = new URL(sourceurl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			in = urlConnection.getInputStream();
			File file = new File( ProjectUtil.createInternalAppStorage(context)  + "/" + "timeline.txt" );

			if( !file.exists())
				file.createNewFile();

			dest = file.getAbsolutePath();
			fileOutput = new FileOutputStream(file);

			byte[] buffer = new byte[1024];
			int bufferLength = 0;

			while ( (bufferLength = in.read(buffer)) > 0 )
			{
				fileOutput.write( buffer, 0, bufferLength );
			}
			System.out.println("Response: " + readString(in));
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if (fileOutput != null)
				fileOutput.close();
			
			if( urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return dest;
	}
	
	private String readString(InputStream in) throws IOException
	{
		byte[] bytes = readBytes(in);
		String texto = new String(bytes);
		return texto;
	}

	private byte[] readBytes(InputStream in) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try
		{
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
			{
				bos.write(buf, 0, len);
			}

			byte[] bytes = bos.toByteArray();
			return bytes;
		}
		finally
		{
			bos.close();
		}
	}

}
