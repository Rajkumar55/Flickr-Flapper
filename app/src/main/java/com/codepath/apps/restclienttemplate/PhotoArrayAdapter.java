package com.codepath.apps.restclienttemplate;

import java.util.List;

import com.codepath.apps.restclienttemplate.models.FlickrPhoto;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PhotoArrayAdapter extends ArrayAdapter<FlickrPhoto> {
	Context ctxt;
	public PhotoArrayAdapter(Context context, List<FlickrPhoto> photoList) {
		super(context, R.layout.photo_item, photoList);
		ctxt=context;
	}
	
	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
		final FlickrPhoto photo = this.getItem(position);
		LinearLayout itemView;
		final ImageView ivImage;
		ImageLoader imageLoader = ImageLoader.getInstance();

        if (convertView == null) {
    		LayoutInflater inflator = LayoutInflater.from(getContext());
    		itemView = (LinearLayout) inflator.inflate(R.layout.photo_item, parent, false);
        } else {
            itemView = (LinearLayout) convertView;
        }
        ivImage = (ImageView) itemView.findViewById(R.id.ivPhoto);
        ivImage.setImageResource(android.R.color.transparent);
		imageLoader.displayImage(photo.getUrl(), ivImage);
		ivImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(ctxt,photo.getUrl(),Toast.LENGTH_LONG).show();
				Intent in=new Intent(ctxt,PhotoActivity.class);
				in.putExtra("PhotoName",photo.getName().toString());
				in.putExtra("PhotoURL",photo.getUrl());
				ctxt.startActivity(in);
			}
		});


        return itemView;
	}

}
