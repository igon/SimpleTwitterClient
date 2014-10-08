package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.basictwitter.models.Tweet;

public class UserTimelineFragment extends TweetsListFragment {
	
	private String screen_name; 


	
	 public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       // Get back arguments
	       String screen_name = getArguments().getString("screen_name");	
	       this.screen_name = screen_name;
	   }
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		populateTimeline();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void populateTimeline() {
		getClient().getUserTimeline(screen_name, new TweetTimelineRequestHandler());		
	}

	
	public void paginateTimeline(int currentTotal) {
		Tweet lastTweet = getTweet(currentTotal - 1);
		long lastUid = lastTweet.getUid();
		getClient().getUserTimeline(lastUid, new TweetTimelineRequestHandler());
	}
	
	public static UserTimelineFragment newInstance(String screen_name) {
		UserTimelineFragment current = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putString("screen_name", screen_name);
		current.setArguments(args);
		return current;
	}
	
	
}
