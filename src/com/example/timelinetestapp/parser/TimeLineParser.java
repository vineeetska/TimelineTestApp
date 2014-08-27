package com.example.timelinetestapp.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.util.JsonReader;

import com.example.timelinetestapp.model.TimelineModel;

public class TimeLineParser {

	public ArrayList<TimelineModel> readTimelineStream(InputStream in){
		ArrayList<TimelineModel> list = new ArrayList<TimelineModel>();
		try {
			JsonReader reader = new JsonReader(new InputStreamReader(in));
			reader.beginObject();
			while (reader.hasNext()){
				String name = reader.nextName();
				if (name.equals(ParserNode.DATA)){
					reader.beginArray();
					while (reader.hasNext())
						list.add(readTimeline(reader));
					reader.endArray();
				}
				else
					reader.skipValue();
			}
			reader.endObject();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	private TimelineModel readTimeline(JsonReader reader) throws IOException{
		TimelineModel model = new TimelineModel();
		reader.beginObject();
		while(reader.hasNext()){
			String name = reader.nextName();
			
			if (name.equals(ParserNode.CREATED_AT))
				model.setCreatedAt(reader.nextString());
			else if (name.equals(ParserNode.TEXT))
				model.setPostText(reader.nextString());
			else if (name.equals(ParserNode.USER)){
				reader.beginObject();
				while (reader.hasNext()){
					name = reader.nextName();
					if (name.equals(ParserNode.USERNAME))
						model.getUser().setUsername(reader.nextString());
					else if (name.equals(ParserNode.AVATAR_IMAGE)){
						reader.beginObject();
						while (reader.hasNext()){
							name = reader.nextName();
							if (name.equals(ParserNode.URL))
								model.getUser().setAvatarUrl(reader.nextString());
							else
								reader.skipValue();
						}
						reader.endObject();
					}	
					else
						reader.skipValue();
				}
				reader.endObject();
			}
			else
				reader.skipValue();
		}
		reader.endObject();
		return model;
	}
	
}
