package com.example.todoapp.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
<<<<<<< HEAD
import com.example.todoapp.data.dao.CategoryDao
import com.example.todoapp.data.dao.NoteDao
import com.example.todoapp.model.Note
import com.example.todoapp.model.Category

@Database(entities = [Note::class, Category::class], version = 2, exportSchema = false) // Đã tăng phiên bản lên 3
=======

@Database(entities = [Note::class], version = 2) // Tăng phiên bản lên 2
>>>>>>> 5083fc4cb0e587903443b5cba517b316f862647c
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao

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
<<<<<<< HEAD
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Thêm migration từ version 1 đến 2 và từ 2 đến 3
=======
                    .addMigrations(MIGRATION_1_2) // Thêm migration
>>>>>>> 5083fc4cb0e587903443b5cba517b316f862647c
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

<<<<<<< HEAD
// Migration từ phiên bản 1 đến 2
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Tạo bảng 'categories' nếu chưa có
        database.execSQL(""" 
            CREATE TABLE IF NOT EXISTS `category` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL
            )
        """.trimIndent())

        // Thêm cột 'deadline' vào bảng 'note'
        database.execSQL("ALTER TABLE note ADD COLUMN `deadline` TEXT")
    }
}

// Migration từ phiên bản 2 đến 3
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Ví dụ: thêm cột 'priority' vào bảng 'note'
        database.execSQL("ALTER TABLE note ADD COLUMN `priority` INTEGER DEFAULT 0 NOT NULL")
=======
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Thêm cột 'deadline'
        database.execSQL("ALTER TABLE note ADD COLUMN deadline TEXT")
>>>>>>> 5083fc4cb0e587903443b5cba517b316f862647c
    }
}
