package com.example.todoapp.data.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.dao.NoteDao
import com.example.todoapp.model.Note
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
        fun searchNotes(query: String): LiveData<List<Note>> {
            return noteDAO.searchNotes(query)
        }
        fun getNotesByCategory(categoryId: Int): LiveData<List<Note>> {
            return noteDAO.getNotesByCategory(categoryId)
        }

    fun getNotesByIds(noteIds: IntArray): LiveData<List<Note>> {
        return noteDAO.getNotesByIds(noteIds)
    }

}
