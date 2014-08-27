package com.example.timelinetestapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{

	private String id = "";
	private String username = "";
	private String avatarUrl = "";
	private String avatarImage = "";
	private String description = "";
	private String createdAt = "";
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(Parcel in){
		id = in.readString();
		username = in.readString();
		avatarUrl = in.readString();
		avatarImage = in.readString();
		description = in.readString();
		createdAt = in.readString();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getAvatarImage() {
		return avatarImage;
	}
	public void setAvatarImage(String avatarImage) {
		this.avatarImage = avatarImage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
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
		dest.writeString(username);
		dest.writeString(avatarUrl);
		dest.writeString(avatarImage);
		dest.writeString(description);
		dest.writeString(createdAt);
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
		
		@Override
		public User[] newArray(int size) {
			// TODO Auto-generated method stub
			return new User[size];
		}
		
		@Override
		public User createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new User(source);
		}
	};
	
}
