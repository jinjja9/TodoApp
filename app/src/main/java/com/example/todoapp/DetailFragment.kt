package com.example.todoapp

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        arguments?.let { bundle ->
            val args = DetailFragmentArgs.fromBundle(bundle)
            noteId = args.noteId // Lưu noteId
            binding.title.text = args.title
            binding.description.text = args.description
        }

        // back
        binding.left.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_homeScreenFragment)
        }

        // edit
        binding.edit.setOnClickListener {
            val editDialog = EditDialog()
            val editArgs = Bundle().apply {
                putInt("noteId", noteId)
                putString("title", binding.title.text.toString())
                putString("description", binding.description.text.toString())
            }
            editDialog.arguments = editArgs
            editDialog.show(parentFragmentManager, "EditDialog")
        }

        // delete
        binding.delete.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        return binding.root
    }
    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete TODO")
        builder.setMessage("Are you sure you want to delete this TODO?")

        // Dialog xoa
        builder.setPositiveButton("Delete") { dialog, _ ->
            val noteToDelete = Note(noteId, binding.title.text.toString(), binding.description.text.toString())
            noteViewModel.delete(noteToDelete)
            findNavController().navigate(R.id.action_detailFragment_to_homeScreenFragment)
            dialog.dismiss() // dong dialog
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}
