package com.codepath.apps.basictwitter.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Serializable {
	private static final long serialVersionUID = 5177222050535318633L;
	private String body; 
	private long uid;
	private String createdAt; 
	private User user; 
	
	
	public static Tweet fromJSON(JSONObject jsonObject) {
		Tweet tweet = new Tweet(); 

		try {
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null; 
		}
		return tweet;
	}
	
	public static ArrayList<Tweet> fromJSONArray(JSONArray json) {
		ArrayList<Tweet> result = new ArrayList<Tweet>(json.length());
		JSONObject current = null;

		for (int i = 0; i < json.length(); i++) {
			try {
				current = json.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}

			Tweet curTweet = fromJSON(current);
			if (curTweet != null) {
				result.add(curTweet);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return getBody() + "-" + getUser().getScreenName();
	}

	public String getBody() {
		return body;
	}


	public long getUid() {
		return uid;
	}


	public String getCreatedAt() {
		return createdAt;
	}


	public User getUser() {
		return user;
	}
	
}
