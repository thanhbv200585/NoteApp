package com.task.noteapp.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.noteapp.domain.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract val dao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}
