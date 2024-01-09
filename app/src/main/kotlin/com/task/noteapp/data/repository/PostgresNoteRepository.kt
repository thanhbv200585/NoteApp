package com.task.noteapp.data.repository

import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.Driver
import java.sql.Connection

class PostgresNoteRepository(private val dataSource: HikariDataSource) : NoteRepository {

    init {
        // Connect to the database and create the table if it doesn't exist
        Database.connect(dataSource)
        transaction {
            SchemaUtils.create(Notes)
        }
    }

    // Table for storing notes
    object Notes : Table("Note") {
        val id = integer("id").autoIncrement().primaryKey()
        val title = varchar("title", 255)
        val description = text("description")
        val imageUrl = varchar("imageUrl", 512).nullable() // Adjust max length if needed
        val createdDate = long("createdDate")
        val modifiedDate = long("modifiedDate").nullable()
    }

    override fun getNotes(): Flow<List<Note>> {
        return flow {
            while (true) {
                emit(transaction {
                    Notes.selectAll().map {
                        Note(
                            it[Notes.id],
                            it[Notes.title],
                            it[Notes.description],
                            it[Notes.imageUrl],
                            it[Notes.createdDate],
                            it[Notes.modifiedDate]
                        )
                    }
                })
                delay(1000) // Refresh every second (adjust as needed)
            }
        }
    }

    override suspend fun getNote(id: Int): Note? {
        return transaction {
            Notes.select { Notes.id eq id }.mapNotNull {
                Note(
                    it[Notes.id],
                    it[Notes.title],
                    it[Notes.description],
                    it[Notes.imageUrl],
                    it[Notes.createdDate],
                    it[Notes.modifiedDate]
                )
            }.singleOrNull()
        }
    }

    override suspend fun insertNote(note: Note) {
        transaction {
            Notes.insert {
                it[title] = note.title
                it[description] = note.description
                it[imageUrl] = note.imageUrl
                it[createdDate] = note.createdDate
                it[modifiedDate] = note.modifiedDate
            }
        }
    }

    override suspend fun deleteNote(note: Note) {
        transaction {
            Notes.deleteWhere { Notes.id eq note.id }
        }
    }
}
