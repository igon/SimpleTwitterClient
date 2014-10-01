package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utils.EndlessScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;
	
	public final int TWEET_CODE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		populateTimeline();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet> ();
		aTweets = new TweetArrayAdapter(this, android.R.layout.simple_list_item_1, tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				paginateTimeline(totalItemsCount);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == TWEET_CODE) {
	     // Extract name value from result extras
		 Tweet newTweet = (Tweet) data.getExtras().getSerializable("tweet");
		 
		 if (newTweet != null) {
			 tweets.add(0, newTweet);
			 aTweets.notifyDataSetChanged();
		 }
	  }
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void populateTimeline() {
		client.getHomeTimeline(new TweetTimelineRequestHandler());		
	}
	
	public void paginateTimeline(int currentTotal) {

		Tweet lastTweet = aTweets.getItem(aTweets.getCount() - 1);
		long lastUid = lastTweet.getUid();
		client.getHomeTimeline(lastUid, new TweetTimelineRequestHandler());

	}
	
	public void composeTweet(MenuItem item) {
		
		Intent i = new Intent(this,ComposeTweetActivity.class);
		startActivityForResult(i,TWEET_CODE);
	}
	
	public void refreshTweets(MenuItem item) {
		populateTimeline();
	}
	
	private class TweetTimelineRequestHandler extends JsonHttpResponseHandler {
		@Override
		public void onSuccess(JSONArray json) {
			aTweets.addAll(Tweet.fromJSONArray(json));
			aTweets.notifyDataSetChanged();
		}

		@Override
		public void onFailure(Throwable e, String s) {
			Log.d("debug", e.toString());
			Log.d("debug", s);
		}
	}
	
}
