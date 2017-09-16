package com.technozion.tz_17.eventCategories;

/**
 * Created by sharda on 31/08/17.
 */

public class EventCategory {
    int id;
    String title;
    int imageResourceId;

    public EventCategory(){

    }

    public EventCategory(int id, String title, int imageResourceId){

        this.id = id;
        this.title = title;
        this.imageResourceId = imageResourceId;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
