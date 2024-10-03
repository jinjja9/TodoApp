package com.example.todoapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todoapp.model.Note
import com.example.todoapp.data.viewmodel.NoteViewModel
import com.example.todoapp.databinding.FragmentDetailBinding
import com.example.todoapp.dialog.EditDialog

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var noteViewModel: NoteViewModel
    private var noteId: Int = 0
    private var categoryId: Int? = null

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

        noteViewModel.getNoteById(noteId).observe(viewLifecycleOwner, Observer { note ->
            note?.let {
                binding.title.text = it.title
                binding.description.text = it.description
<<<<<<< HEAD:app/src/main/java/com/example/todoapp/ui/DetailFragment.kt
                binding.deadlinedate.text = it.deadline
                categoryId = it.categoryId // Lấy categoryId từ note
=======
                binding.deadlinedate.text=it.deadline
>>>>>>> 5083fc4cb0e587903443b5cba517b316f862647c:app/src/main/java/com/example/todoapp/DetailFragment.kt
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
                    putString("deadline", binding.deadlinedate.text.toString())
<<<<<<< HEAD:app/src/main/java/com/example/todoapp/ui/DetailFragment.kt
                    putInt("categoryId", categoryId ?: 0) // Truyền categoryId
=======
>>>>>>> 5083fc4cb0e587903443b5cba517b316f862647c:app/src/main/java/com/example/todoapp/DetailFragment.kt
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
<<<<<<< HEAD:app/src/main/java/com/example/todoapp/ui/DetailFragment.kt
                val noteToDelete = Note(
                    noteId,
                    binding.title.text.toString(),
                    binding.description.text.toString(),
                    binding.deadlinedate.text.toString(),
                    categoryId ?: 0 // Kiểm tra nếu categoryId là null thì truyền 0
=======
                val noteToDelete = Note(noteId, binding.title.text.toString(), binding.description.text.toString(),
                    null.toString()
>>>>>>> 5083fc4cb0e587903443b5cba517b316f862647c:app/src/main/java/com/example/todoapp/DetailFragment.kt
                )
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
