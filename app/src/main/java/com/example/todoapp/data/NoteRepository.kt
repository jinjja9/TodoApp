package com.example.todoapp.data

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDAO: NoteDao) {
    val listNote: LiveData<List<Note>> = noteDAO.getAllNotes()

    suspend fun insertNote(note: Note){
        noteDAO.insert(note)
    }

    suspend fun updateNote(note: Note){
        noteDAO.update(note)
    }

    suspend fun deleteNote(note: Note){
        noteDAO.delete(note)
    }
}