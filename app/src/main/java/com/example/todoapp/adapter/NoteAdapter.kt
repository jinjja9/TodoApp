package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.Note
import com.example.todoapp.databinding.NoteItemBinding
import com.example.todoapp.model.Category

class NoteAdapter(
    private val noteList: List<Note>,
    private val categories: List<Category>, // Danh sách các category
    private val onItemClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, categories: List<Category>, onItemClick: (Note) -> Unit) {
            binding.noteTitle.text = note.title
            binding.noteDescription.text = note.description
            binding.deadlinedate.text = note.deadline ?: "Chưa có hạn chót"

            // Tìm danh mục tương ứng và cập nhật icon
            val category = categories.find { it.id == note.categoryId } // Giả sử note có thuộc tính categoryId
            if (category != null) {
                binding.iconcategory.setImageResource(category.iconResId) // Cập nhật ảnh theo danh mục
            } else {
                binding.iconcategory.setImageResource(R.drawable.others) // Ảnh mặc định nếu không tìm thấy
            }

            binding.root.setOnClickListener {
                onItemClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position], categories, onItemClick) // Truyền danh sách category vào bind
    }

    override fun getItemCount(): Int = noteList.size
}
