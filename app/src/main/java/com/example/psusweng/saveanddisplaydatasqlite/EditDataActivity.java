package com.example.psusweng.saveanddisplaydatasqlite;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button mBtnSave,mBtnDelete;
    private EditText mEditTextItem;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        mBtnSave = (Button) findViewById(R.id.btnSave);
        mBtnDelete = (Button) findViewById(R.id.btnDelete);
        mEditTextItem = (EditText) findViewById(R.id.editable_item);
        mDatabaseHelper = new DatabaseHelper(this);

        // Get the intent extra information from the ListDataActivity.
        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id",-1);
        selectedName = receivedIntent.getStringExtra("name");

        mEditTextItem.setText(selectedName);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = mEditTextItem.getText().toString();
                if(!item.equals(""))
                {
                    mDatabaseHelper.updateName(item, selectedID, selectedName);
                    Intent goBack = new Intent(EditDataActivity.this,ListDataActivity.class);
                    startActivity(goBack);
                }
                else
                {
                    toastMessage("You must enter a name.");
                }
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mDatabaseHelper.deleteName(selectedID, selectedName);
                mEditTextItem.setText("");
                toastMessage("Deleted from database.");
            }
        });
    }


    private void toastMessage(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
