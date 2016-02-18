package br.net.mockup.control.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Base para comandos SQL
 * Created by Thiago on 02/07/2015.
 */
public abstract class DaoSQL<T> {
    private SQLiteDatabase db;

    public DaoSQL(SQLiteDatabase db) {
        if(db == null){
            throw new IllegalArgumentException("Nao foi possivel iniciar " + toString());
        }
        this.db = db;
    }

    protected final SQLiteDatabase getDb() {
        return db;
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName();
    }

    public abstract long insert(T... object);
    public abstract long update(Object... object);
    public abstract T find(Object... object);
    public abstract long delete(T object);
    public abstract Object list(Object... object);
    protected abstract Object create(Cursor cursor, int colunas);
}
