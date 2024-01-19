package ru.protei.grigorevaed.compose.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.protei.grigorevaed.compose.domain.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM note")
    fun all():List<Note>
    @Query ("SELECT * FROM note ORDER BY id ASC")
    fun allFlow(): Flow<List<Note>>
    @Insert(onConflict = OnConflictStrategy. IGNORE)
    suspend fun insert(note: Note): Long
    @Update
    suspend fun update(note: Note)
    @Query ("DELETE FROM note WHERE id = :id")
    suspend fun deleteById(id: Long)
    @Query ("DELETE FROM note")
    fun deleteAll()
}