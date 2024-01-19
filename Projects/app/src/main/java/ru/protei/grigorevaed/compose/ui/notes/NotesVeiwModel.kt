package ru.protei.grigorevaed.compose.ui.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.protei.grigorevaed.compose.domain.Note
import ru.protei.grigorevaed.compose.domain.NotesUseCase

class NotesVeiwModel(private val notesUseCase: NotesUseCase):ViewModel()
{
    val notesli = mutableStateListOf<Note>(
        Note("Заметка 1","Текст 1"),
        Note("Заметка 2","Текст 2"),
        Note("Заметка 3","Текст 3"),
    )

    val notes = MutableStateFlow<List<Note>>(notesli)

    init {
                viewModelScope.launch {
                    notesUseCase.fillWithInitialNotes(notes.value)
                    notesUseCase.notesFlow()
                        .collect()
                        { note ->
                         notes.value = note
                        }
                }
    }

    var selected by mutableStateOf<Note?>(null)

    fun OnAddetNoteClicked(){
        selected = Note("", "")
        notes.value = notes.value.toMutableList().apply {
            add(selected!!)}
    }

    fun OnNoteChange(title: String, text: String){
        selected?.let{
            it.title = title
            it.text = text
        }
    }

    fun OnEditComplete(){
        selected = null
    }

    fun OnNoteSelected(note: Note){
        selected = note
    }
}