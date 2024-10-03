package com.example.todoapp.data.respository

import androidx.lifecycle.LiveData
import com.example.todoapp.data.dao.CategoryDao
import com.example.todoapp.model.Category

class CategoryRepository(private val categoryDAO: CategoryDao) {
    val allCategories: LiveData<List<Category>> = categoryDAO.getAllCategories()

    suspend fun insert(category: Category) {
        categoryDAO.insert(category)
    }

    suspend fun update(category: Category) {
        categoryDAO.update(category)
    }

    suspend fun deleteById(id: Int) {
        categoryDAO.deleteById(id)
    }

    suspend fun getCategoryById(categoryId: Int): Category? {
        return categoryDAO.getCategoryById(categoryId)
    }
}