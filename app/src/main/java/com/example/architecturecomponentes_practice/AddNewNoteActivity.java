package com.example.architecturecomponentes_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNewNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE_KEY="com.example.architecturecomponentes_practice.title_key";
    public static final String EXTRA_DESCRIPTION_KEY="com.example.architecturecomponentes_practice.description_key";
    public static final String EXTRA_PRIORITY_KEY="com.example.architecturecomponentes_practice.priority_key";

    private EditText titleEditText;
    private EditText descriptionEditText;
    private NumberPicker priorityNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        setTitle("ACP");

        titleEditText = findViewById(R.id.editText_title);
        descriptionEditText = findViewById(R.id.editText_description);
        priorityNumberPicker = findViewById(R.id.numberPicker_priority);
        priorityNumberPicker.setMinValue(1);
        priorityNumberPicker.setMaxValue(10);




    }

    public void saveNotes() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        int priority = priorityNumberPicker.getValue();
        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "please enter a title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data=new Intent();
        data.putExtra(EXTRA_TITLE_KEY,title);
        data.putExtra(EXTRA_DESCRIPTION_KEY,description);
        data.putExtra(EXTRA_PRIORITY_KEY,priority);
        setResult(RESULT_OK,data);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note_menu:
                saveNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}