package br.net.mockup.model.activity;

import android.support.v7.app.AppCompatActivity;

import br.net.mockup.control.util.Broadcast;

/**
 * Base para as activitys do aplicativo
 * Created by Thiago on 30/10/2015.
 */
public class AppActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        Broadcast.register(this, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Broadcast.unregister(this, null);
    }
}
