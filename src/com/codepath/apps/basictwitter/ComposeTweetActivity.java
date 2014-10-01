package com.codepath.apps.basictwitter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {

	private EditText tweetBody;
	private TwitterClient twitterClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		tweetBody = (EditText) findViewById(R.id.etTweet);
		twitterClient = TwitterApplication.getRestClient();
	}

	public void onCancel(View v) {
		finish();
	}

	
	public void onTweet(View v) {
		 twitterClient.postTweet(tweetBody.getText().toString(), new TweetRequestHandler());
	}

	private class TweetRequestHandler extends JsonHttpResponseHandler {
		@Override
		public void onSuccess(JSONObject json) {
			
			Tweet addedTweet = Tweet.fromJSON(json);	
			Intent data = new Intent();
			
			if (addedTweet != null) {
				data.putExtra("tweet", addedTweet);
				setResult(RESULT_OK, data);
			} else {
				setResult(RESULT_CANCELED);
				Toast.makeText(getBaseContext(), "Ooops! looks like twitter is not working :-( #failwhale", Toast.LENGTH_LONG).show();
			}
			
			finish();
		};

		@Override
		public void onFailure(Throwable e, String s) {
			Log.d("debug", e.toString());
			Log.d("debug", s);
		}
	}
}
