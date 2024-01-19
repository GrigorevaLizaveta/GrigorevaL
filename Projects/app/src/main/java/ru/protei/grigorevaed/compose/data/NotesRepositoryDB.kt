package ru.protei.grigorevaed.compose.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.protei.grigorevaed.compose.domain.Note
import ru.protei.grigorevaed.compose.domain.NotesRepository

class NotesRepositoryDB(
    private val dao: NotesDao
):
    NotesRepository {

    override suspend fun loadAllNotes(): List<Note> = withContext(Dispatchers.IO) {
        return@withContext dao.all()
    }
    override fun loadALlNotesFlow(): Flow<List<Note>> = dao.allFlow()
    override fun clearDatabase() = dao.deleteAll()
    override suspend fun fillDatabase(notes: List<Note>) {
       notes.forEach{dao.insert(it)}
    }
    override suspend fun add(note: Note) {
        dao.insert(note)
    }
    override suspend fun update(note: Note) {
        dao.update(note)
    }
}

