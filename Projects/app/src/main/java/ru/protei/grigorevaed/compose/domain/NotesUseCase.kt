package ru.protei.grigorevaed.compose.domain

import kotlinx.coroutines.flow.Flow
import ru.protei.grigorevaed.compose.data.NotesRepositoryDB

class NotesUseCase (
    private val notesRepo: NotesRepositoryDB
){
    suspend fun fillWithInitialNotes(initialNotes: List<Note>){
        notesRepo.clearDatabase()
        notesRepo.fillDatabase(initialNotes)
    }

    fun notesFlow(): Flow<List<Note>> {
        var flow: Flow<List<Note>>
        return notesRepo.loadALlNotesFlow()
        }

    suspend fun save(note: Note) {
        if (note.id?.toInt() == 0) {
            notesRepo.add(note)
             }
        else {
            notesRepo.update(note)
             }
    }

    suspend fun add(note: Note) {
        return notesRepo.add(note)
    }

    suspend fun update(note: Note) {
        notesRepo.update(note)
    }
}