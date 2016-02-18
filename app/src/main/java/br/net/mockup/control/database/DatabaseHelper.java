package br.net.mockup.control.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Thiago on 09/11/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static class Table{
        public static final String _ID = "_id";
    }

    public DatabaseHelper(Context context, String bancoDados, int versao) {
        super(context, bancoDados, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
