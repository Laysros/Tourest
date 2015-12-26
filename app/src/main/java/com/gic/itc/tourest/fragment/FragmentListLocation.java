package com.gic.itc.tourest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gic.itc.tourest.R;
import com.gic.itc.tourest.adapter.PlaceAdapter;
import com.gic.itc.tourest.api.DetailCallerBack;
import com.gic.itc.tourest.app.AppController;
import com.gic.itc.tourest.model.PlaceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by LAY Leangsros on 18/12/2015.
 */
public class FragmentListLocation extends Fragment implements DetailCallerBack{

    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;
    private List<PlaceItem> mPlaceItemList;
    private PlaceAdapter mPlaceAdapter;

    ////
    @Bind(R.id.detail_panel)
    LinearLayout mDetailPanel;
    @Bind(R.id.detail_title)
    TextView mDetailTitle;
    @Bind(R.id.close)
    ImageButton mClose;
    @Bind(R.id.detail_desc) TextView mDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_location, container, false);
        ButterKnife.bind(this,view);
        mPlaceItemList = new ArrayList<>();

        initDataPlace();


        mPlaceAdapter = new PlaceAdapter(getActivity(), mPlaceItemList,this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mPlaceAdapter);

        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Click hehe", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initDataPlace(){
        double mLatitude=51.503186;
        double mLongitude=-0.12644;

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude+ "," + mLongitude);
        sb.append("&radius=5000");
        sb.append("&types=atm");
        sb.append("&sensor=true");
        sb.append("&key=");
        sb.append(getString(R.string.google_maps_key));
        Log.d("URL", "link:" + sb.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (sb.toString(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            JSONArray results = response.getJSONArray("results");
                            //iterate through the results
                            for(int i = 0; i<results.length();i++)
                            {
                                JSONObject single = (JSONObject) results.get(i);
                                //for example, to get the location which is nested 2 levels in
                                JSONObject geometry = single.getJSONObject("geometry");
                                //within geometry object is location
                                JSONObject location = geometry.getJSONObject("location");
                                double lat = location.getDouble("lat");
                                double lonG = location.getDouble("lng");
                                Log.d("Lat", lat+"");
                                Log.d("Long", lonG+"");

                                //Set to object
                                PlaceItem p = new PlaceItem();
                                p.setPlaceId(single.getString("place_id"));
                                p.setName(single.getString("name"));
                                mPlaceItemList.add(p);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Error", error.toString());
                    }});

        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    @Override
    public void onDetailCllick(PlaceItem place) {
        Log.d("GGGGGGGGGGGGGGGGGGG", "Efef" + place.getPlaceId());
        mDetailPanel.setVisibility(View.VISIBLE);
        mDetailTitle.setText(place.getName());
        mDescription.setText(place.getName());
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDetailPanel.setVisibility(View.GONE);
            }
        });


    }
}
