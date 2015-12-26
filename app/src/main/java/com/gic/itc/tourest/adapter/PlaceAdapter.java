package com.gic.itc.tourest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gic.itc.tourest.R;
import com.gic.itc.tourest.api.DetailCallerBack;
import com.gic.itc.tourest.model.PlaceItem;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LAY Leangsros on 21/12/2015.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> implements DetailCallerBack{
    private Context mContext;
    private LayoutInflater inflater;
    private List<PlaceItem> mPlaceItemList;
    private DetailCallerBack mDetailCallerBack;

    public PlaceAdapter(Context mContext, List<PlaceItem> mPlaceItemList, DetailCallerBack mDetailCallerBack) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.mPlaceItemList = mPlaceItemList;
        this.mDetailCallerBack = mDetailCallerBack;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.place_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PlaceItem place = mPlaceItemList.get(position);
        holder.mTitle.setText(place.getName());
        holder.mType.setText(place.getType());
        if(place.isFavorite()) {
            holder.mFavorite.setImageResource(R.drawable.success);
        }else{
            holder.mFavorite.setImageResource(R.drawable.fail);
        }
        holder.mImagePlace.setImageResource(R.drawable.place);
        holder.mDescription.setText(place.getDescription());
        holder.mImagePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDetailCallerBack.onDetailCllick(place);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlaceItemList.size();
    }

    @Override
    public void onDetailCllick(PlaceItem place) {
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.place_title)
        TextView mTitle;
        @Bind(R.id.place_type) TextView mType;
        @Bind(R.id.imgFavorite)
        ImageButton mFavorite;
        @Bind(R.id.imgPlace)
        ImageView mImagePlace;
        @Bind(R.id.place_desc) TextView mDescription;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
