package com.gic.itc.tourest.fragment;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gic.itc.tourest.R;
import com.gic.itc.tourest.adapter.FeedAdapter;
import com.gic.itc.tourest.app.AppConfig;
import com.gic.itc.tourest.app.AppController;
import com.gic.itc.tourest.helper.SessionManager;
import com.gic.itc.tourest.model.FeedItem;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by LAYLeangsros on 13/07/2015.
 */
public class FragmentProfile extends Fragment {

    private Double latitude, longitude;
    private TextView tvCity, tvCoutry;
    private RecyclerView recyclerView;
    private FeedAdapter adapter;
    private List<FeedItem> feedItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_about, container, false);
        tvCity = (TextView) view.findViewById(R.id.city);
        tvCoutry = (TextView) view.findViewById(R.id.country);


        initValueAbout();
        recyclerView = (RecyclerView) view.findViewById(R.id.drawerList);

        feedItems = new ArrayList<>();


        /**/
        adapter = new FeedAdapter(getActivity(), feedItems);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /**/


        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)

        // We first check for cached request
        try {
            requestFeed();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;

    }

    private void requestFeed() throws JSONException {


        String tag_string_req = "req_login";
        final SessionManager pref = new SessionManager(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("This", "Login Response: " + response.toString());
                try {
                    JSONObject object = new JSONObject(response);
                    parseJsonFeed(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                    createRequestErrorListener();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "feed");
                params.put("id", "" + pref.getId());

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);

    }


    private void createRequestErrorListener() {
        Log.e("Error", "getting date problem");
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(i);
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("saying"));
                //item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("time"));

                // url might be null sometimes
                //String feedUrl = feedObj.isNull("url") ? null : feedObj.getString("url");
                //item.setUrl(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initPost() {


    }

    private void initValueAbout() {
        String tag_string_req = "req_login";
        final SessionManager pref = new SessionManager(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("This", "Login Response: " + response.toString());
                try {
                    JSONObject object = new JSONObject(response);
                    if(!object.getBoolean("error")) {
                        Geocoder geocoder;

                        latitude = object.getDouble("latitude");
                        longitude = object.getDouble("longitude");

                        List<Address> addresses = null;
                        geocoder = new Geocoder(getActivity(), Locale.getDefault());


                        Log.d("getttt", "ladf" + latitude);
                        try {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        //String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        //String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                        tvCity.setText(city);
                        tvCoutry.setText(country);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "get_about");
                params.put("id", "" + pref.getId());

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
}
