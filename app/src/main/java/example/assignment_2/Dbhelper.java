package example.assignment_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prernaa on 3/5/2017.
 */

public class Dbhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    public Dbhelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE= "CREATE TABLE "+
                DbContract.DbEntry.TABLE_NAME + "("+
                DbContract.DbEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntry.COLUMN_CAPTION + " TEXT NOT NULL , "+
                DbContract.DbEntry.COLUMN_PATH + " TEXT NOT NULL , "+
                DbContract.DbEntry.COLUMN_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP "+ ");";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntry.TABLE_NAME);
        onCreate(db);
    }
}
