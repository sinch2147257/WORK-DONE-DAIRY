package com.example.dairy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class todoList extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private todoAdapter mAdapter;
    private EditText mEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_todo_list);
        todoDBHelper dbHelper = new todoDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new todoAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
        mEditTextName = findViewById(R.id.edittext_name);

        Button buttonAdd = findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }


    private void addItem() {

        String name = mEditTextName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(todoContract.todoEntry.COLUMN_NAME, name);

        mDatabase.insert(todoContract.todoEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
        mEditTextName.getText().clear();
    }
    private Cursor getAllItems() {
        return mDatabase.query(
                todoContract.todoEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                todoContract.todoEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }
}