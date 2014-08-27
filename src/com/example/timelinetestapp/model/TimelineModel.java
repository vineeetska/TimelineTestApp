package com.example.timelinetestapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TimelineModel implements Parcelable{

	private String id = "";
	private String postText = "";
	private String createdAt = "";
	private User user = new User();
	
	public TimelineModel() {
		// TODO Auto-generated constructor stub
	}
	
	public TimelineModel(Parcel in) {
		// TODO Auto-generated constructor stub
		id = in.readString();
		postText = in.readString();
		createdAt = in.readString();
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
	
}
