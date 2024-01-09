package com.task.noteapp.domain.interactor

import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository

class InsertNote(private val repository: NoteRepository) {

    suspend operator fun invoke(note: Note) = repository.insertNote(note)
}
