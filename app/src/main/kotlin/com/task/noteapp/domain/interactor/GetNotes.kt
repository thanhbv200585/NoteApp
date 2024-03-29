package com.task.noteapp.domain.interactor

import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(private val repository: NoteRepository) {

    operator fun invoke(): Flow<List<Note>> {
        return repository.getNotes().map {
            it.sortedByDescending { n -> n.createdDate }
        }
    }
}
