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
    private val categories: List<Category>,
    private val onItemClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, categories: List<Category>, onItemClick: (Note) -> Unit) {
            binding.noteTitle.text = note.title
            binding.noteDescription.text = note.description
            binding.deadlinedate.text = note.deadline ?: "Chưa có hạn chót"

            val category = categories.find { it.id == note.categoryId }
            if (category != null) {
                binding.iconcategory.setImageResource(category.iconResId)
            } else {
                binding.iconcategory.setImageResource(R.drawable.others)
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
        holder.bind(noteList[position], categories, onItemClick)
    }

    override fun getItemCount(): Int = noteList.size
}
