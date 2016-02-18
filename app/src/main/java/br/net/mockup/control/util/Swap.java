package br.net.mockup.control.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Thiago on 03/11/2015.
 */
public class Swap {

    private static final Intent intent = new Intent();

    public static Intent activity(Context packageContext, Class<?> cls){
        if(intent.getExtras() != null)
            intent.getExtras().clear();

        intent.setClass(packageContext, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }

    public static void now(Context context, Intent intent){
        context.startActivity(intent);
    }
}
