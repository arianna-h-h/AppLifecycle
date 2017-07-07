package edu.austincc.AppLifecycle;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DBActivity extends Activity {
	DBAdapter db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db);
		
		db = new DBAdapter(this); 
	}
	
	public void addName(View v) {
		//---add title---
        db.open(); 
        EditText etName = (EditText) findViewById(R.id.editTextName);
        EditText etType = (EditText) findViewById(R.id.editTextType);
        EditText etLoc =  (EditText) findViewById(R.id.editTextLoc);

        long id = db.insertName(
				etName.getText().toString(),
				etType.getText().toString(),
				etLoc.getText().toString());
        Toast.makeText(this, String.format("inserted at id: %d", id), 
        		Toast.LENGTH_LONG).show();
        
        db.close();
	}
	
	public void updateName(View v) {
		//---update title---
        db.open(); 
        EditText etName = (EditText) findViewById(R.id.editTextName);
        EditText etType = (EditText) findViewById(R.id.editTextType);
        EditText etLoc =  (EditText) findViewById(R.id.editTextLoc);
        EditText et = (EditText) findViewById(R.id.editTextUpdateID);
		int id = Integer.parseInt(et.getText().toString());

        boolean updated = db.updateName(id,
        		etType.getText().toString(),
        		etName.getText().toString(),
        		etLoc.getText().toString());
        if (updated)
	        Toast.makeText(this, String.format("updated at id: %d", id), 
	        		Toast.LENGTH_LONG).show();
        else
	        Toast.makeText(this, String.format("update failed at id: %d", id), 
	        		Toast.LENGTH_LONG).show();
        
        db.close();
	}
	
	public void getAllNames(View v)
	{
		db.open();
		Cursor c = db.getAllNames();
		toastAllNames(c);
		db.close();
	}
		
	public void toastAllNames(Cursor c)
		{
		StringBuilder builder = new StringBuilder();
		if (c.moveToFirst()) {
			builder.append("id: " + c.getString(0) + "\n" +
	                "NAME: " + c.getString(1) + "\n" +
	                "TYPE: " + c.getString(2) + "\n" +
	                "LOCATION:  " + c.getString(3)+"\n");
		} else {
			builder.append("no records found");
		}
		while (c.moveToNext())
		{
			builder.append("id: " + c.getString(0) + "\n" +
	                "NAME: " + c.getString(1) + "\n" +
	                "TYPE: " + c.getString(2) + "\n" +
	                "LOCATION:  " + c.getString(3)+"\n");
		}
		Toast.makeText(this, 
                builder.toString(),
                Toast.LENGTH_LONG).show(); 
	}
	
	
	public void findName(View v)
	{
		EditText et = (EditText) findViewById(R.id.editTextFind);
        //---get a title---
        db.open();
        Cursor c = db.findName(et.getText().toString());
        toastAllNames(c);
        db.close();
	}
	
	public void getName(View v)
	{
		EditText et = (EditText) findViewById(R.id.editTextGetID);
		int id = Integer.parseInt(et.getText().toString());
        //---get a title---
        db.open();
        Cursor c = db.getName(id);
        if (c.moveToFirst())        
            DisplayName(c);
        else
            Toast.makeText(this, "No title found", 
            		Toast.LENGTH_LONG).show();
        db.close();
	}
	
	public void deleteName(View v)
	{
		EditText et = (EditText) findViewById(R.id.editTextDelID);
		int id = Integer.parseInt(et.getText().toString());
	    //---delete a title---
	    db.open();
	    if (db.deleteName(id))
	        Toast.makeText(this, "Delete successful.", 
	            Toast.LENGTH_LONG).show();
	    else
	        Toast.makeText(this, "Delete failed.", 
	            Toast.LENGTH_LONG).show();            
	    db.close();
	}

	
    public void DisplayName(Cursor c)
    {
        Toast.makeText(this, 
                "id: " + c.getString(0) + "\n" +
                "NAME: " + c.getString(1) + "\n" +
                "TYPE: " + c.getString(2) + "\n" +
                "LOCATION:  " + c.getString(3),
                Toast.LENGTH_LONG).show();        
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_db, menu);
		return true;
	}

}
