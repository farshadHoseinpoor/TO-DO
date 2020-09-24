package com.example.architecturecomponentes_practice.model.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.architecturecomponentes_practice.database.NoteDao;
import com.example.architecturecomponentes_practice.database.NoteDataBase;
import com.example.architecturecomponentes_practice.model.Note;

import java.util.List;

public class NoteRepository {

    public NoteDao noteDao;
    LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {

        noteDao = NoteDataBase.getInstance(application).noteDao();
        allNotes = noteDao.getAllNotes();

    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);

    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);

    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);

    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(noteDao).execute();

    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;

        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;

        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;

        }
    }
    private static class DeleteAllNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteAllNotes();
            return null;

        }
    }

}
