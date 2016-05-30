package br.net.mockup.control.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Thiago on 09/11/2015.
 */
public abstract class Dao<T>{
    private MockupDatabaseHelper helper;
    private boolean isClose;
    private SQLiteDatabase db;

    public Dao() {
        isClose = true;
    }

    public final T open(Context context){
        if(helper == null || isClose){
            helper = getDatabase(context);
            isClose = false;
        }
        return (T) this;
    }

    protected final SQLiteDatabase getDb(){

        try {
            if(db == null)
                restart();
            return db;
        } catch (SQLException e) {
            Log.e("Database", "Ocorreu um erro ao abrir o database " + e.getLocalizedMessage());
            e.printStackTrace();
            restart();
            return db;
        }
    }

    protected MockupDatabaseHelper getDatabase(Context context){
        return null;
    }

    private void restart(){
        db = helper.getWritableDatabase();
    }

    public final void close(){
        helper.close();
        isClose = true;
    }

    public final boolean isClose(){
        return isClose;
    }
}
