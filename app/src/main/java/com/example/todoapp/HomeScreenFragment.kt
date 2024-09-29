package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.data.NoteViewModel
import com.example.todoapp.databinding.FragmentHomeScreenBinding
import com.example.todoapp.dialog.AddDialog

class HomeScreenFragment : Fragment() {

    data class NoteItem(val id: Int, val title: String, val description: String)

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var noteAdapter: NoteAdapter
    private val noteList = mutableListOf<NoteItem>()
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        noteAdapter = NoteAdapter(noteList) { noteItem ->
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailFragment(
                noteId = noteItem.id,
                title = noteItem.title,
                description = noteItem.description,
            )
            findNavController().navigate(action)
        }


        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            noteList.clear()
            noteList.addAll(notes.map { NoteItem(it.id, it.title, it.description) })
            noteAdapter.notifyDataSetChanged()
        }

        binding.addbutton.setOnClickListener {
            val addFragment = AddDialog()
            addFragment.show(parentFragmentManager, "AddFragment")
        }

        return binding.root
    }
}
