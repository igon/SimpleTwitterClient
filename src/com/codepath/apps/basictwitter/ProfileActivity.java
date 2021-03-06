package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		String screen_name = getIntent().getStringExtra("screen_name");
		boolean self = getIntent().getBooleanExtra("self", false);
		
		
		if (self) {
			loadProfileInfo();	
		}else {
			loadProfileInfo(screen_name);	
		}
	}
	
	public void loadProfileInfo(String screen_name) {
		TwitterApplication.getRestClient().getUserInfo(screen_name, new UserInformationResponseHandler());
	}

	public void loadProfileInfo() {
		TwitterApplication.getRestClient().getMyInfo(new UserInformationResponseHandler());
	}
	
	private void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount()+ " Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
		
	}
	
	private class UserInformationResponseHandler extends JsonHttpResponseHandler {
		@Override
		public void onSuccess(JSONObject json) {
				User u = User.fromJson(json);
				getActionBar().setTitle("@"+ u.getScreenName());
				populateProfileHeader(u);
				
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.timeline_holder, UserTimelineFragment.newInstance(u.getScreenName()));
				ft.commit();
		}
	}

}
