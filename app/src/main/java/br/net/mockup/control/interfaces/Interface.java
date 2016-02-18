package br.net.mockup.control.interfaces;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Thiago on 15/10/2015.
 */
public abstract class Interface<T> {
    private AppCompatActivity activity;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public abstract T create();
}
