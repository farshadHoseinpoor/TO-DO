package com.example.architecturecomponentes_practice.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.architecturecomponentes_practice.model.Note;
import com.example.architecturecomponentes_practice.viewmodels.NoteViewModel;
import com.example.architecturecomponentes_practice.R;
import com.example.architecturecomponentes_practice.adapters.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getNoteAt(viewHolder.getAbsoluteAdapterPosition()));
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID_KEY, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE_KEY, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION_KEY, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY_KEY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE_KEY);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION_KEY);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY_KEY, 10);
            Note note = new Note(title, description, priority);
            viewModel.insert(note);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        }else if (requestCode==EDIT_NOTE_REQUEST && resultCode==RESULT_OK){

            int id=data.getIntExtra(AddEditNoteActivity.EXTRA_ID_KEY,-1);

            if (id == -1){
                Toast.makeText(this, "Can`t Update Note", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE_KEY);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION_KEY);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY_KEY, 10);
            Note note = new Note(title, description, priority);
            note.setId(id);


            viewModel.update(note);
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();


        } else Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_icon:
                viewModel.deleteAllNotes();
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}