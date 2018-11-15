package com.example.psusweng.saveanddisplaydatasqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        mListView = (ListView)findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Display data in the ListView.");

        // Get the data from the database and append to the ListView.
        Cursor data = mDatabaseHelper.getData();

        ArrayList<String> listData = new ArrayList<>();

        while(data.moveToNext())
        {
            // Get the data from the database in column 1
            // then add it to the ListView.
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = adapterView.getItemAtPosition(position).toString();

                Log.d(TAG, "onItemClick: You clicked on: " + name);

                // Get the ID for the name associated with the item that was clicked in the ListView.
                Cursor data = mDatabaseHelper.getItemID(name);
                int itemID = -1;
                while(data.moveToNext())
                {
                    itemID = data.getInt(0);

                    if( itemID > -1 )
                    {
                        Log.d(TAG,"onItemClick: The ID is " + itemID);
                        Intent editDataActivity =  new Intent(ListDataActivity.this, EditDataActivity.class);
                        editDataActivity.putExtra("id", itemID);
                        editDataActivity.putExtra("name", name);
                        startActivity(editDataActivity);
                    }
                    else
                    {
                        toastMessage("No ID associated with that name.");
                    }
                }
            }
        });
    }

    private void toastMessage(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
