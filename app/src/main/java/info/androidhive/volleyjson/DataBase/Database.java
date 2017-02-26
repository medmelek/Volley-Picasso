package info.androidhive.volleyjson.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.volleyjson.app.modeltmp;

/**
 * Created by Med Melek on 27/11/2016.
 */

public class Database extends SQLiteOpenHelper {
    private static Database mInstance;

    private static final  String database_name="tmp.db";
    private static final  String table_name="tmp";
    private static final  String valeurtmp="valeurtmp";
    private static final  String datetmp="datetmp";
    private static final  String imagetmp="imagetmp";

    public Database(Context context) {
        super(context,database_name,null,1);
    }
    public static synchronized Database getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new Database(context.getApplicationContext());
        }
        return mInstance;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + table_name +
                "(" +
                valeurtmp + " TEXT ," +
                datetmp + " TEXT, " +
                imagetmp+ " TEXT" +

                ")";

        sqLiteDatabase.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+table_name);
            onCreate(sqLiteDatabase);
        }
    }


    public void addtmp (modeltmp model ) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();

            values.put(valeurtmp, model.getValeurtmp());
            values.put(datetmp, model.getDatetmp());
            values.put(imagetmp,model.getImagetmp());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(table_name, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("ERROR ADD", "Error while trying to add student to database");
        } finally {
            db.endTransaction();
        }
    }



    public List<modeltmp> getAlltmp() {
        List<modeltmp> modeltmpsArrayList = new ArrayList<modeltmp>();

        // SELECT * FROM STUDENT
        String STUDENT_SELECT_QUERY = "SELECT * FROM " + table_name;

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(STUDENT_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                   modeltmp modeltmp = new modeltmp(cursor.getString(cursor.getColumnIndex(valeurtmp)),
                                                    cursor.getString(cursor.getColumnIndex(imagetmp)),
                                                    cursor.getString(cursor.getColumnIndex(datetmp)));
                    modeltmpsArrayList.add(modeltmp);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERROR", "Error while trying to get studentArrayList from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return modeltmpsArrayList;
    }

}
