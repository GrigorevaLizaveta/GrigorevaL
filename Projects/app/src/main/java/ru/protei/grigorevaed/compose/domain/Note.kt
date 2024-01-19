package ru.protei.grigorevaed.compose.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note(

    var title: String,
    var text: String
)
{
@PrimaryKey(autoGenerate = true)
var id: Long? = null

}