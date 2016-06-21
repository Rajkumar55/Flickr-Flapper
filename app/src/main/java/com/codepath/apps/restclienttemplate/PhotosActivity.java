package com.codepath.apps.restclienttemplate;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.FlickrPhoto;
import com.loopj.android.http.JsonHttpResponseHandler;

public class PhotosActivity extends Activity {
	
	FlickrClient client;
	ArrayList<FlickrPhoto> photoItems;
	GridView gvPhotos;
	PhotoArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		ImageButton imageView= (ImageButton) findViewById(R.id.imageButton);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText emailID= (EditText) findViewById(R.id.editText);
				String email=emailID.getText().toString();
				client = FlickrClientApp.getRestClient();
				photoItems = new ArrayList<FlickrPhoto>();
				gvPhotos = (GridView) findViewById(R.id.gvPhotos);
				adapter = new PhotoArrayAdapter(PhotosActivity.this, photoItems);
				gvPhotos.setAdapter(adapter);
				loadPhotos(email);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photos, menu);
		return true;
	}

	public void loadPhotos(String emailID) {
		/*client.getInterestingnessList(new JsonHttpResponseHandler() {
    		public void onSuccess(JSONObject json) {
                Log.d("DEBUG", "result: " + json.toString());
                // Add new photos to SQLite
                try {
					JSONArray photos = json.getJSONObject("photos").getJSONArray("photo");
					for (int x = 0; x < photos.length(); x++) {
						String uid  = photos.getJSONObject(x).getString("id");
						FlickrPhoto p = FlickrPhoto.byPhotoUid(uid);
						if (p == null) { p = new FlickrPhoto(photos.getJSONObject(x)); };
						p.save();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Log.e("debug", e.toString());
				}
                
				// Load into GridView from DB
				for (FlickrPhoto p : FlickrPhoto.recentItems()) {
					adapter.add(p);
				}
				Log.d("DEBUG", "Total: " + photoItems.size());
            }
    	});*/
		//final String[] email = new String[1];
		final String[] nsid = new String[1];
		client.getUserID(emailID,new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONObject jsonObject) {
				try {
					nsid[0] =jsonObject.getJSONObject("user").getString("nsid");
					client.getUserPhotos(nsid[0],new JsonHttpResponseHandler(){
						@Override
						public void onSuccess(JSONObject json) {
							adapter.clear();
							Log.d("DEBUG", "result: " + json.toString());
							// Add new photos to SQLite
							try {
								JSONArray photos = json.getJSONObject("photos").getJSONArray("photo");
								for (int x = 0; x < photos.length(); x++) {
									String uid  = photos.getJSONObject(x).getString("id");
									FlickrPhoto p = FlickrPhoto.byPhotoUid(uid);
									if (p == null) { p = new FlickrPhoto(photos.getJSONObject(x)); };
									p.save();
								}
							} catch (JSONException e) {
								e.printStackTrace();
								Log.e("debug", e.toString());
							}

							// Load into GridView from DB
							for (FlickrPhoto p : FlickrPhoto.recentItems()) {
								adapter.add(p);
							}
							Log.d("DEBUG", "Total: " + photoItems.size());
						}

					});

				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
				}
			}
		});

	}

}
