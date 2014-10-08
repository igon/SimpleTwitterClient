package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.basictwitter.models.Tweet;

public class UserTimelineFragment extends TweetsListFragment {

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		populateTimeline();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void populateTimeline() {
		getClient().getUserTimeline(new TweetTimelineRequestHandler());		
	}

	
	public void paginateTimeline(int currentTotal) {
		Tweet lastTweet = getTweet(currentTotal - 1);
		long lastUid = lastTweet.getUid();
		getClient().getUserTimeline(lastUid, new TweetTimelineRequestHandler());
	}
	
	
}
