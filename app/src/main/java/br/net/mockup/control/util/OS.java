package br.net.mockup.control.util;

import android.os.Build;

/**
 * Created by Thiago on 04/11/2015.
 */
public class OS {
    public static boolean isLollipop(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
