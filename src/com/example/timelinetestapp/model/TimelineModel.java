package com.example.timelinetestapp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class TimelineModel implements Parcelable, Comparable<TimelineModel>{

	private String id = "";
	private String postText = "";
	private String createdAt = "";
	private String dateTime = "";
	private User user = new User();
	
	public TimelineModel() {
		// TODO Auto-generated constructor stub
	}
	
	public TimelineModel(Parcel in) {
		// TODO Auto-generated constructor stub
		id = in.readString();
		postText = in.readString();
		createdAt = in.readString();
		dateTime = in.readString();
		user = new User(in);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
		dateTime = createdAt.substring(0, createdAt.indexOf("T")) + " " + createdAt.substring(createdAt.indexOf("T") + 1, createdAt.indexOf("Z"));
	}
	
	public String getDateTime() {
		return dateTime;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(postText);
		dest.writeString(createdAt);
		dest.writeString(dateTime);
		dest.writeString(user.getId());
		dest.writeString(user.getUsername());
		dest.writeString(user.getAvatarUrl());
		dest.writeString(user.getAvatarImage());
		dest.writeString(user.getDescription());
		dest.writeString(user.getCreatedAt());
	}
	
	public static final Parcelable.Creator<TimelineModel> CREATOR = new Creator<TimelineModel>() {
		
		@Override
		public TimelineModel[] newArray(int size) {
			// TODO Auto-generated method stub
			return new TimelineModel[size];
		}
		
		@Override
		public TimelineModel createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new TimelineModel(source);
		}
	};

	@Override
	public int compareTo(TimelineModel another) {
		// TODO Auto-generated method stub
		return getDateTime().compareTo(another.getDateTime());
	}
	
}
