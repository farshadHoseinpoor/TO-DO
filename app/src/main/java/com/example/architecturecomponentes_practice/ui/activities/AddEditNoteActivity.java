package com.example.architecturecomponentes_practice.ui.activities;

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

import com.example.architecturecomponentes_practice.R;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID_KEY="com.example.architecturecomponentes_practice.id_key";
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

        titleEditText = findViewById(R.id.editText_title);
        descriptionEditText = findViewById(R.id.editText_description);
        priorityNumberPicker = findViewById(R.id.numberPicker_priority);

        priorityNumberPicker.setMinValue(1);
        priorityNumberPicker.setMaxValue(10);

        Intent intent=getIntent();

        if (intent.hasExtra(EXTRA_ID_KEY)){
            setTitle("Edit Note");
            titleEditText.setText(intent.getStringExtra(EXTRA_TITLE_KEY));
            descriptionEditText.setText(intent.getStringExtra(EXTRA_DESCRIPTION_KEY));
            priorityNumberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY_KEY,1));
        }else setTitle("Add Note");

    }

    public void saveNotes() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        int priority = priorityNumberPicker.getValue();
        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please Enter a Title And Description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data=new Intent();

        data.putExtra(EXTRA_TITLE_KEY,title);
        data.putExtra(EXTRA_DESCRIPTION_KEY,description);
        data.putExtra(EXTRA_PRIORITY_KEY,priority);

        int id=getIntent().getIntExtra(EXTRA_ID_KEY,-1);
        if (id!=-1){
            data.putExtra(EXTRA_ID_KEY,id);
        }


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