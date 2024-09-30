package com.example.todoapp

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todoapp.data.Note
import com.example.todoapp.data.NoteViewModel
import com.example.todoapp.databinding.FragmentDetailBinding
import com.example.todoapp.dialog.EditDialog

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var noteViewModel: NoteViewModel
    private var noteId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        arguments?.let {
            val args = DetailFragmentArgs.fromBundle(it)
            noteId = args.noteId
        }

        // Sử dụng noteId để lấy thông tin từ ViewModel
        noteViewModel.getNoteById(noteId).observe(viewLifecycleOwner, Observer { note ->
            note?.let {
                binding.title.text = it.title
                binding.description.text = it.description
            }
        })

        // Back
        binding.left.setOnClickListener {
            findNavController().popBackStack()
        }

        // Edit
        binding.edit.setOnClickListener {
            val editDialog = EditDialog().apply {
                arguments = Bundle().apply {
                    putInt("noteId", noteId)
                    putString("title", binding.title.text.toString())
                    putString("description", binding.description.text.toString())
                }
            }
            editDialog.show(parentFragmentManager, "EditDialog")
        }

        // Delete
        binding.delete.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        return binding.root
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete TODO")
            .setMessage("Are you sure you want to delete this TODO?")
            .setPositiveButton("Delete") { dialog, _ ->
                val noteToDelete = Note(noteId, binding.title.text.toString(), binding.description.text.toString())
                noteViewModel.delete(noteToDelete)
                findNavController().popBackStack()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }
}
