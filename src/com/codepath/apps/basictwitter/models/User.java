package com.codepath.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{
	private static final long serialVersionUID = 5177222050535319033L;
	private String name; 
	private long uid; 
	private String screenName;
	private String profileImageUrl; 
	private String followersCount; 
	private String friendsCount;
	private String tagline; 
	
	public static User fromJson(JSONObject jsonObject) {
		User user = new User();
		
		try {
			user.name = jsonObject.getString("name");
			user.uid = jsonObject.getLong("id");
			user.screenName = jsonObject.getString("screen_name");
			user.profileImageUrl = jsonObject.getString("profile_image_url");		
			user.followersCount = jsonObject.getString("followers_count");
			user.friendsCount = jsonObject.getString("friends_count");
			user.tagline = jsonObject.getString("description");
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null; 
		}		
		return user;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getFollowersCount() {
		return followersCount;
	}

	public String getFriendsCount() {
		return friendsCount;
	}

	public String getTagline() {
		return tagline;
	}
	
	
}
