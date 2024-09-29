package com.example.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.NoteItemBinding

class NoteAdapter(
    private val noteList: List<HomeScreenFragment.NoteItem>,
    private val onItemClick: (HomeScreenFragment.NoteItem) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: HomeScreenFragment.NoteItem, onItemClick: (HomeScreenFragment.NoteItem) -> Unit) {
            binding.noteTitle.text = note.title
            binding.noteDescription.text = note.description
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
        holder.bind(noteList[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}
