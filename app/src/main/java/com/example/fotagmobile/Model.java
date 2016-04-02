package com.example.fotagmobile;

import java.util.Observable;

public class Model extends Observable {

    private MainActivity mainActivity;
    private int stars;


    public Model(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
        setChanged();
        notifyObservers("stars");
    }
}
