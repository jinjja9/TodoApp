package com.example.todoapp.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import com.example.todoapp.data.dao.CategoryDao
import com.example.todoapp.data.dao.NoteDao
import com.example.todoapp.data.dao.UserDao
import com.example.todoapp.model.Note
import com.example.todoapp.model.Category
import com.example.todoapp.model.User

@Database(entities = [Note::class, Category::class, User::class], version = 1, exportSchema = false) // Đã tăng phiên bản lên 3
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}