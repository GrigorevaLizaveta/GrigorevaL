package ru.protei.grigorevaed.compose.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.protei.grigorevaed.compose.domain.Note


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    vm: NotesVeiwModel
) {
    val notes by vm.notes.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { vm.OnAddetNoteClicked() }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(notes) { note ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                        .clickable { vm.OnNoteSelected(note) },
                ) {
                    Column {
                        Text(text = note.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp))
                        Text(text = note.text + "\n", fontSize = 18.sp, modifier = Modifier.padding(5.dp))
                    }
                }
            }
        }
    }
        if (vm.selected != null) {
            EditNoteDialog(
                note = vm.selected!!,
                onEditComplete = { vm.OnEditComplete() },
                onNoteChange = { title, text -> vm.OnNoteChange(title, text) }
            )
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteDialog(
    note: Note,
    onEditComplete: () -> Unit,
    onNoteChange: (title: String, text: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        Column {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it; onNoteChange(it, note.text) },
                label = { Text(text = "Title", fontSize = 20.sp, fontWeight = FontWeight.Bold ) }
            )
            BasicTextField(
                value = text,
                onValueChange = { text = it; onNoteChange(note.title, it) },
                modifier = Modifier
                    .padding(all = 8.dp),
                textStyle = MaterialTheme.typography.bodyLarge
                    .copy(color = MaterialTheme.colorScheme.onPrimary),
                minLines = 20
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onEditComplete) {
                    Text("Done", color = White)
                }
            }
        }
    }
}