package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.fragments.HomeTimelineFragment;
import com.codepath.apps.fragments.MentionsTimelineFragment;
import com.codepath.apps.fragments.TweetsListFragment;

public class TimelineActivity extends FragmentActivity {
	public final int TWEET_CODE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
				.newTab()
				.setText("Home")
				.setIcon(R.drawable.ic_action_home)
				.setTag("HomeTimelineFragment")
				.setTabListener(
						new FragmentTabListener<HomeTimelineFragment>(
								R.id.flContainer, this, "home",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
				.newTab()
				.setText("Mentions")
				.setIcon(R.drawable.ic_action_mentions)
				.setTag("MentionsTimelineFragment")
				.setTabListener(
						new FragmentTabListener<MentionsTimelineFragment>(
								R.id.flContainer, this, "mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
		if (resultCode == RESULT_OK && requestCode == TWEET_CODE) {
			// Extract name value from result extras
			Tweet newTweet = (Tweet) data.getExtras().getSerializable("tweet");

			if (newTweet != null) {
				TweetsListFragment fragmentTweetList = (TweetsListFragment) getSupportFragmentManager()
						.findFragmentById(R.id.flContainer);

				fragmentTweetList.add(0, newTweet);
				// aTweets.notifyDataSetChanged();
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
	
	public void composeTweet(MenuItem item) {
		
		Intent i = new Intent(this,ComposeTweetActivity.class);
		startActivityForResult(i,TWEET_CODE);
	}

	
}
