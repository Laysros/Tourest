package com.gic.itc.tourest.model;

/**
 * Created by LAY Leangsros on 21/12/2015.
 */
public class PlaceItem {
    private String placeId;
    private boolean isFavorite;
    private String name;
    private String type;
    private String url;
    private String description;


    public PlaceItem(){

    }

    public PlaceItem(String place_id, boolean isFavorite, String name, String type, String url, String description) {
        this.placeId = place_id;
        this.isFavorite = isFavorite;
        this.name = name;
        this.type = type;
        this.url = url;
        this.description = description;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
