package com.example.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.NoteAdapter
import com.example.todoapp.model.Note
import com.example.todoapp.model.Category // Import Category model
import com.example.todoapp.data.viewmodel.NoteViewModel
import com.example.todoapp.databinding.FragmentHomeScreenBinding
import com.example.todoapp.dialog.AddDialog

class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var noteAdapter: NoteAdapter
    private val noteList = mutableListOf<Note>()
    private lateinit var noteViewModel: NoteViewModel

    // Danh sách các Category (bỏ thuộc tính color)
    private val categoryList = listOf(
        Category(id = 1, name = "Work", iconResId= R.drawable.work),
        Category(id = 2, name = "Personal", iconResId = R.drawable.personal),
        Category(id = 3, name = "Shopping", iconResId = R.drawable.shopping),
        Category(id = 4, name = "Health", iconResId = R.drawable.heart),
        Category(id = 5, name = "Other", iconResId = R.drawable.others)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        // Truyền danh sách category vào adapter
        noteAdapter = NoteAdapter(noteList, categoryList) { note ->
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailFragment(
                noteId = note.id
            )
            findNavController().navigate(action)
        }

        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            noteList.clear()
            noteList.addAll(notes)
            noteAdapter.notifyDataSetChanged()
        }

        binding.addbutton.setOnClickListener {
            AddDialog().show(parentFragmentManager, "AddFragment")
        }

        return binding.root
    }
}
