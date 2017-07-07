package edu.austincc.AppLifecycle;

import android.provider.BaseColumns;

/* 
 * database definition with a single table
 */
public class Food {
	
	private Food() {}

	public class Restaurants implements BaseColumns
	{
		public static final String TABLE_NAME = "Restaurants";
		
	    public static final String COLUMN_NAME_NAME = "name";
	    public static final String COLUMN_NAME_TYPE = "type";
	    public static final String COLUMN_NAME_LOCATION = "location";
	}
	
}
