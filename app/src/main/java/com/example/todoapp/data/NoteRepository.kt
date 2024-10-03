package com.example.todoapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteRepository(private val noteDAO: NoteDao) {
    val listNote: LiveData<List<Note>> = noteDAO.getAllNotes()

    suspend fun insertNote(note: Note) {
        noteDAO.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDAO.update(note)
    }

    suspend fun deleteNoteById(id: Int) {
        noteDAO.deleteById(id)
    }


    fun getNoteById(noteId: Int): LiveData<Note?> {
        val result = MutableLiveData<Note?>()
        CoroutineScope(Dispatchers.IO).launch {
            val note = noteDAO.getNoteById(noteId)
            result.postValue(note)
        }
        return result
    }
}
