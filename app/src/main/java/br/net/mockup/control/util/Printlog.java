package br.net.mockup.control.util;

import android.util.Log;


/**
 * Created by Thiago on 21/05/2015.
 */
public class Printlog {
    public static final String LOG = "Log";
    private static final String INFO = "Info";
    private static final String WARM = "warm";
    private static final String ERROR = "Error";

    public static void debug(String msg){
        /*if(BuildConfig.DEBUG_MODE) {
            if (Fabric.isInitialized()){
                Crashlytics.log(Log.DEBUG, LOG, msg);
            }else{
                Log.d(LOG, msg);
            }
        }*/

        Log.d(LOG, msg);
    }

    public static void info(String msg){
        /*if(BuildConfig.DEBUG_MODE){
            if(Fabric.isInitialized())
                Crashlytics.log(Log.INFO, Assets.INFO, msg);
            else
                Log.i(Assets.INFO, msg);
        }
*/
        Log.i(INFO, msg);
    }

    public static void warm(String msg){
        /*if(BuildConfig.DEBUG_MODE){
            if(Fabric.isInitialized())
                Crashlytics.log(Log.WARN, Assets.WARM, msg);
            else
                Log.w(Assets.WARM, msg);
        }*/

        Log.w(WARM, msg);
    }

    public static void erro(String msg){

       /* if(Fabric.isInitialized()){
            if( user != null && !user.isEmpty()){
                Crashlytics.setUserName(user);
                //Bugsnag.setUserName(user);
            }

            Crashlytics.log(Log.ERROR, Assets.ERROR, msg);

            //Bugsnag.notify(new RuntimeException(msg), Severity.ERROR);
        }else{
            Log.e(Assets.ERROR, "[USER] " + user);
            Log.e(Assets.ERROR, msg);
        }*/

        Log.e(ERROR, msg);
    }

    public static void erro(Throwable exception, String msg){

        /*if(Fabric.isInitialized()){
            if( user != null && !user.isEmpty()){
                Crashlytics.setUserName(user);
                //Bugsnag.setUserName(user);
            }

            Crashlytics.logException(exception);

            Crashlytics.log(Log.ERROR, Assets.ERROR, msg);
            //Bugsnag.notify(exception, Severity.ERROR);
        }else{
            Log.e(Assets.ERROR, "[USER] " + user);
            Log.e(Assets.ERROR, msg);
        }*/

        Log.e(ERROR, msg);
        exception.printStackTrace();
    }

    public static void erro(Throwable exception){

        /*if(Fabric.isInitialized()){
            if( user != null && !user.isEmpty()){
                Crashlytics.setUserName(user);
                //Bugsnag.setUserName(user);
            }

            Crashlytics.logException(exception);
            //Bugsnag.notify(exception, Severity.ERROR);
        }*/

        exception.printStackTrace();
    }
}
