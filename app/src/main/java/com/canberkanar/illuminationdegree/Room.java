package com.canberkanar.illuminationdegree;

/**
 * Created by Canberk on 1/4/2018.
 */

public class Room {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;
    public int minIntensity;
    public int maxIntensity;

    Room(String ad, int minIntensity, int maxIntensity){
        this.name = ad;
        this.minIntensity = minIntensity;
        this.maxIntensity = maxIntensity;
    }
}
