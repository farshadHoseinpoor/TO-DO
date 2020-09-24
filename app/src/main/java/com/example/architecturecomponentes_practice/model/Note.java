package com.example.architecturecomponentes_practice.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String title;

    private String description;

    private int priority;

    public int getId() {
        return id;
    }

    public Note setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

}
