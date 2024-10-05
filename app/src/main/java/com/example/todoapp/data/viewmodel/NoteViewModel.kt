package com.example.todoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.NoteDatabase
import com.example.todoapp.data.respository.NoteRepository
import com.example.todoapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        allNotes = repository.listNote
    }

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNoteById(note.id)
        }
    }


    fun getNoteById(noteId: Int): LiveData<Note?> {
        return repository.getNoteById(noteId)
    }

    fun searchNotes(query: String): LiveData<List<Note>> {
        return repository.searchNotes(query)
    }

    fun filterNotesByCategory(categoryId: Int): LiveData<List<Note>> {
        return repository.getNotesByCategory(categoryId)
    }

    fun getNotesByIds(noteIds: IntArray): LiveData<List<Note>> {
        return repository.getNotesByIds(noteIds)
    }

}
