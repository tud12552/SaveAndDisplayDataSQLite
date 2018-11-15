package com.example.psusweng.saveanddisplaydatasqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;

    private Button mBtnAdd, mBtnViewUsers;

    private EditText mEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextName = (EditText)findViewById(R.id.editTextName);
        mBtnAdd = (Button)findViewById(R.id.btnAdd);
        mBtnViewUsers = (Button)findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = mEditTextName.getText().toString();
                if (newEntry.length() != 0)
                {
                    AddData(newEntry);
                    mEditTextName.setText("");
                }
                else
                {
                    toastMessage("You must inset a name.");
                }
            }
        });

        mBtnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newEntry)
    {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if(insertData)
        {
            toastMessage("Data Successfully Inserted");
        }
        else
        {
            toastMessage("Error Inserting Data");
        }
    }

    private void toastMessage(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
