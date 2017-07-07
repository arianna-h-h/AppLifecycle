package edu.austincc.AppLifecycle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* from http://www.devx.com/wireless/Article/40842 
 */
public class DBAdapter 
{
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "books.db";   
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table "+Food.Restaurants.TABLE_NAME+" (_id integer primary key autoincrement, "
        +Food.Restaurants.COLUMN_NAME_NAME+" text not null, "
        +Food.Restaurants.COLUMN_NAME_TYPE+" text not null, "
        +Food.Restaurants.COLUMN_NAME_LOCATION+ " text not null);";
        
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS names");
            onCreate(db);
        }
    }    
    
    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a title into the database---
    public long insertName(String name, String type, String location)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Food.Restaurants.COLUMN_NAME_NAME, name);
        initialValues.put(Food.Restaurants.COLUMN_NAME_TYPE, type);
        initialValues.put(Food.Restaurants.COLUMN_NAME_LOCATION, location);
        return db.insert(Food.Restaurants.TABLE_NAME, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteName(long rowId)
    {
        return db.delete(Food.Restaurants.TABLE_NAME, Food.Restaurants._ID +
        		"=" + rowId, null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllNames()
    {
        return db.query(Food.Restaurants.TABLE_NAME, new String[] {
                        Food.Restaurants._ID,
                        Food.Restaurants.COLUMN_NAME_NAME,
                        Food.Restaurants.COLUMN_NAME_TYPE,
                        Food.Restaurants.COLUMN_NAME_LOCATION},
                null, 
                null, 
                null, 
                null, 
                null);
    }

    //---finds a book by a particular title---
    public Cursor findName(String name) throws SQLException
    {
    	String sql = "select "+
                Food.Restaurants._ID+", "+
                Food.Restaurants.COLUMN_NAME_NAME+", "+
                Food.Restaurants.COLUMN_NAME_TYPE+", "+
                Food.Restaurants.COLUMN_NAME_LOCATION+
        	" from "+Food.Restaurants.TABLE_NAME+
        	" where "+Food.Restaurants.COLUMN_NAME_NAME+" = ?";
    	//sql = "select _id,isbn,title,publisher from titles where title=?";
    	String[] search = {
                name
    	};
        return db.rawQuery(sql, search);
    }
    
    //---retrieves a particular title---
    public Cursor getName(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, Food.Restaurants.TABLE_NAME, new String[] {
                                Food.Restaurants._ID,
                                Food.Restaurants.COLUMN_NAME_NAME,
                                Food.Restaurants.COLUMN_NAME_TYPE,
                                Food.Restaurants.COLUMN_NAME_LOCATION
                		},
                        Food.Restaurants._ID + "=" + rowId,
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a title---
    public boolean updateName(long rowId, String name,
    String type, String location)
    {
        ContentValues args = new ContentValues();
        args.put(Food.Restaurants.COLUMN_NAME_NAME, name);
        args.put(Food.Restaurants.COLUMN_NAME_TYPE, type);
        args.put(Food.Restaurants.COLUMN_NAME_LOCATION, location);
        return db.update(Food.Restaurants.TABLE_NAME, args,
        		Food.Restaurants._ID + "=" + rowId, null) > 0;
    }
}
 
