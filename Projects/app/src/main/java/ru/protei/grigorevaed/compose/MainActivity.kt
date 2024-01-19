package ru.protei.grigorevaed.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import ru.protei.grigorevaed.compose.domain.NotesUseCase
import ru.protei.grigorevaed.compose.ui.notes.NotesScreen
import ru.protei.grigorevaed.compose.ui.notes.NotesVeiwModel
import ru.protei.grigorevaed.compose.ui.theme.GrigorevaedTheme
import ru.protei.grigorevaed.compose.data.NotesDatabase
import ru.protei.grigorevaed.compose.data.NotesRepositoryDB


class MainActivity : ComponentActivity() {

    private val database: NotesDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java, "note_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
    private val notesRepo by lazy { NotesRepositoryDB(database.notesDao()) }
    private val notesUseCase by lazy { NotesUseCase(notesRepo) }
    private val notesViewModel: NotesVeiwModel by viewModels {
        viewModelFactory {
            initializer {
                NotesVeiwModel(notesUseCase)
            }
        }
    }

    private lateinit var vm: NotesVeiwModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = NotesVeiwModel(notesUseCase)
        setContent {
            GrigorevaedTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                )
                {
                    NotesScreen(vm)
                }
            }
        }
    }
}




