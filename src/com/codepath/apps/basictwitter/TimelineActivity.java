package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utils.EndlessScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		populateTimeline();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet> ();
		aTweets = new TweetArrayAdapter(this,android.R.layout.simple_list_item_1, tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				paginateTimeline(totalItemsCount);
			}
		});
	}
	
	public void populateTimeline() {
		client.getHomeTimeline(new TweetRequestHandler());		
	}
	
	public void paginateTimeline(int currentTotal) {
		Tweet lastTweet = aTweets.getItem(currentTotal-1);
		long lastUid = lastTweet.getUid();
		client.getHomeTimeline(lastUid, new TweetRequestHandler());
		
	}
	
	private class TweetRequestHandler extends JsonHttpResponseHandler {
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
