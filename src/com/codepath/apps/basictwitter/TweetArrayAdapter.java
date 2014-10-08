package com.codepath.apps.basictwitter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
//public class TweetArrayAdapter extends GenericAdapter<Tweet> {
		
	public TweetArrayAdapter(Context context,int resource,List<Tweet> objects) {
		super(context, resource, objects);
	}



	public TweetArrayAdapter(FragmentActivity activity, ArrayList<Tweet> tweets) {
		// TODO Auto-generated constructor stub
		super(activity,android.R.layout.simple_list_item_1,tweets);
	}



	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		return getDataRow(position, convertView, parent);
	}
	
	public View getDataRow(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);

		View v;

		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}

		ImageView ivProfileImage = (ImageView) v
				.findViewById(R.id.ivProfileImage);
		TextView tvName = (TextView) v.findViewById(R.id.tvName);
		TextView tvHandle = (TextView) v.findViewById(R.id.tvHandle);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvTime = (TextView) v.findViewById(R.id.tvTime);
		ivProfileImage.setImageResource(android.R.color.transparent);

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(),
				ivProfileImage);
		ivProfileImage.setTag(tweet.getUser().getScreenName());
		
		
		ivProfileImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(),ProfileActivity.class);
				i.putExtra("screen_name", v.getTag().toString());
				getContext().startActivity(i);
			}
			
		});
		
		tvHandle.setText("@"+tweet.getUser().getScreenName());
		tvName.setText(tweet.getUser().getName());
		tvTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		tvBody.setText(tweet.getBody());

		return v;
	}
	
}
