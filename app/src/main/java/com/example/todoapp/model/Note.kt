package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "note"
)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
<<<<<<< HEAD:app/src/main/java/com/example/todoapp/model/Note.kt
    @ColumnInfo(name = "deadline") val deadline: String? = null, // Ngày
    @ColumnInfo(name = "categoryId") val categoryId: Int? // ID của category
)
=======
    @ColumnInfo(name = "deadline") val deadline: String?
)
>>>>>>> 5083fc4cb0e587903443b5cba517b316f862647c:app/src/main/java/com/example/todoapp/data/Note.kt
