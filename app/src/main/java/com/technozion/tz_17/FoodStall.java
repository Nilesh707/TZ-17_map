package com.technozion.tz_17;

/**
 * Created by Rishu Kumar on 26/08/2017.
 */
public class FoodStall {
    private int id;
    private String name;
    private String time;
    private String location;
    private int image;
    private String menu;

    public FoodStall(int id, String name, String time, String location, int image, String menu) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.location = location;
        this.image = image;
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }
}
