package com.example.bj.sqlitepractice;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
    }

    public void addDataClicked(View v)
    {
        EditText editName = (EditText) findViewById(R.id.editText_name);
        EditText editSurname = (EditText) findViewById(R.id.editText_surname);
        EditText editMarks = (EditText) findViewById(R.id.editText_marks);

        boolean isInserted = myDb.insertData( editName.getText().toString() , editSurname.getText().toString() , editMarks.getText().toString() );

        if(isInserted==true)
        {
            Toast.makeText(MainActivity.this , "Data Inserted !" , Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this , "Failed to Insert !" , Toast.LENGTH_LONG).show();
        }
    }

    public void viewDataClicked(View v)
    {
        Cursor res = myDb.getAllData();
        if( res.getCount() == 0 )
        {
            //No Data , Show Some Message
            showMessage("Error" , "Nothing Found !");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while( res.moveToNext() )
        {
            buffer.append( "ID :"+res.getString(0)+"\n" );
            buffer.append( "NAME :"+res.getString(1)+"\n" );
            buffer.append( "SURNAME :"+res.getString(2)+"\n" );
            buffer.append( "MARKS :"+res.getString(3)+"\n\n" );
        }

        //Show The Data
        showMessage("Data" , buffer.toString());
    }

    public void showMessage(String title , String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateDataClicked(View v)
    {
        EditText editID = (EditText) findViewById(R.id.editText_id);
        EditText editName = (EditText) findViewById(R.id.editText_name);
        EditText editSurname = (EditText) findViewById(R.id.editText_surname);
        EditText editMarks = (EditText) findViewById(R.id.editText_marks);

        boolean isUpdated = myDb.updateData(editID.getText().toString() , editName.getText().toString() , editSurname.getText().toString() , editMarks.getText().toString());

        if( isUpdated == true )
        {
            Toast.makeText(MainActivity.this , "Data Updated !" , Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this , "Update Failed !" , Toast.LENGTH_LONG).show();
        }
    }

    public void deleteClicked(View v)
    {
        EditText editID = (EditText) findViewById(R.id.editText_id);

        int deletedRows =  myDb.deleteData(editID.getText().toString());

        if( deletedRows > 0 )
        {
            Toast.makeText(MainActivity.this , "Deleted "+deletedRows+" Rows !" , Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this , "No Data Deleted !" , Toast.LENGTH_LONG).show();
        }
    }

}
