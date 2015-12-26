package com.gic.itc.tourest.api;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by LAY Leangsros on 26/12/2015.
 */
public interface Api {
    @POST("/")
    Call<Places> getData();
}
