package br.net.mockup.view;

import android.support.v7.app.AppCompatActivity;

import br.net.mockup.control.util.MockupBroadcast;

/**
 * Base para as activitys do aplicativo
 * Created by Thiago on 30/10/2015.
 */
class AppActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        MockupBroadcast.register(this, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        MockupBroadcast.unregister(this, null);
    }
}
