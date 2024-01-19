package ru.protei.grigorevaed.compose.domain

import kotlinx.coroutines.flow.Flow
import ru.protei.grigorevaed.compose.data.NotesDao

interface NotesRepository {

    suspend fun loadAllNotes(): List<Note>

    fun loadALlNotesFlow(): Flow<List<Note>>

    fun clearDatabase()

    suspend fun fillDatabase(notes: List<Note>)

    suspend fun add(note: Note)

    suspend fun update(note: Note)
}