package com.codepath.apps.restclienttemplate;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FlickrClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = FlickrApi.class;
    public static final String REST_URL = "http://www.flickr.com/services";
    //public static final String REST_CONSUMER_KEY = "57ac210e2e82195e071f9a761d763ca0";
    public static final String REST_CONSUMER_KEY = "bec6cda168977e43b258a40d7d112273";
    //public static final String REST_CONSUMER_SECRET = "7d359e4f4149545b";
    public static final String REST_CONSUMER_SECRET = "123806b79de9099a";
    public static final String REST_CALLBACK_URL = "oauth://cprest";
    
    public FlickrClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
        setBaseUrl("https://api.flickr.com/services/rest");
    }

    /*public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?&format=json&nojsoncallback=1&api_key=bec6cda168977e43b258a40d7d112273&method=flickr.interestingness.getList");
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }*/
    public  void  getUserPhotos(String userID, AsyncHttpResponseHandler handler)
    {
        String apiUrl = getApiUrl("?&method=flickr.people.getPhotos&api_key="+YOUR_API_KEY"+&user_id="+userID+"&format=json&nojsoncallback=1&auth_token=72157669042352170-fb9d0c0175479c3d&api_sig=a8a0e3eaa5ee573d522e28f5a3de6767");
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }
    public  void  getUserID(String emailID, AsyncHttpResponseHandler handler)
    {
        String email=emailID.replace("@","%40");
        String apiUrl = getApiUrl("?method=flickr.people.findByEmail&api_key="+YOUR_API_KEY"+&find_email="+emailID+"&format=json&nojsoncallback=1&auth_token=72157669450145722-2fcfb1d99c004424&api_sig=b972ed9b2025e6a2eb06b4ad89d2799d");
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }

}
