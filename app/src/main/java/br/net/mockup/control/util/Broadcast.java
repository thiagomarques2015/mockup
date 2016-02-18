package br.net.mockup.control.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.ImageReader;
import android.support.v4.content.LocalBroadcastManager;

import br.net.mockup.model.ImageParcelable;
import br.net.mockup.model.config.Actions;

/**
 * Enviar mensagens broadcast para o aplicativo
 * Created by Thiago on 30/10/2015.
 */
public class Broadcast {
    private static Intent intent = new Intent();
    private static IntentFilter filter = new IntentFilter(Actions.EXECUTE_COMMAND);

    public static void send(Context context, byte[] data, String command){
        clearExtras();
        intent.setAction(Actions.EXECUTE_COMMAND);
        intent.putExtra("image", new ImageParcelable(data));
        intent.putExtra("command", command);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void send(Context context, ImageReader reader, String command) {
        clearExtras();
        intent.setAction(Actions.EXECUTE_COMMAND);
        intent.putExtra("image", new ImageParcelable(reader));
        intent.putExtra("command", command);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void send(Context context, String command) {
        clearExtras();
        intent.setAction(Actions.EXECUTE_COMMAND);
        intent.putExtra("command", command);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private static void clearExtras() {
        if( intent.getExtras() != null)
            intent.getExtras().clear();
    }

    public static void register(Context context, BroadcastReceiver receiver){
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }

    public static void unregister(Context context, BroadcastReceiver receiver){
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }
}
