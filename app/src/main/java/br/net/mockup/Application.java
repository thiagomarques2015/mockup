package br.net.mockup;

import android.content.Context;

/**
 * Created by Thiago on 28/10/2015.
 */
public class Application extends android.app.Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static Context getContext(){
        return instance;
    }
}
