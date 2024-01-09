package com.task.noteapp.domain.interactor

import com.task.noteapp.domain.repository.NoteRepository


class GetNote(private val repository: NoteRepository) {

    suspend operator fun invoke(id: Int) = repository.getNote(id)
}
