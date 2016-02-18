package br.net.mockup.control.util;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Thiago on 09/11/2015.
 */
public class DatabaseUtil {
    public static void execSQL(SQLiteDatabase db, String sql){
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
