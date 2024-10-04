package com.example.todoapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM note WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchNotes(query: String): LiveData<List<Note>>

}
