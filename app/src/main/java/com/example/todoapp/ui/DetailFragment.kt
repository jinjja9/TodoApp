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
import com.example.todoapp.R
import com.example.todoapp.model.Note
import com.example.todoapp.data.viewmodel.NoteViewModel
import com.example.todoapp.databinding.FragmentDetailBinding
import com.example.todoapp.dialog.EditDialog
import com.example.todoapp.model.Category
import java.text.SimpleDateFormat
import java.util.Locale

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var noteViewModel: NoteViewModel
    private var noteId: Int = 0
    private var categoryId: Int? = null

    private val categoryList = listOf(
        Category(id = 1, name = "Work", iconResId = R.drawable.work),
        Category(id = 2, name = "Personal", iconResId = R.drawable.personal),
        Category(id = 3, name = "Shopping", iconResId = R.drawable.shopping),
        Category(id = 4, name = "Health", iconResId = R.drawable.heart),
        Category(id = 5, name = "Other", iconResId = R.drawable.others)
    )

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
                categoryId = it.categoryId

                // Định dạng Date trước khi hiển thị
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val deadlineText = it.deadline?.let { date -> dateFormat.format(date) } ?: "No deadline"
                binding.deadlinedate.text = deadlineText

                val categoryName = categoryList.find { category -> category.id == categoryId }?.name ?: "Unknown"
                binding.category.text = categoryName
            }
        })

        // Back
        binding.left.setOnClickListener {
            findNavController().popBackStack()
        }

        // Edit
        binding.edit.setOnClickListener {
            noteViewModel.getNoteById(noteId).observe(viewLifecycleOwner, Observer { note ->
                note?.let {
                    val editDialog = EditDialog().apply {
                        arguments = Bundle().apply {
                            putInt("noteId", noteId)
                            putString("title", it.title)
                            putString("description", it.description)
                            // Chuyển đổi deadline thành Long
                            putLong("deadline", it.deadline?.time ?: -1L)
                            it.categoryId?.let { it1 -> putInt("categoryId", it1) }
                        }
                    }
                    editDialog.show(parentFragmentManager, "EditDialog")
                }
            })
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
                val noteToDelete = Note(
                    noteId,
                    binding.title.text.toString(),
                    binding.description.text.toString(),
                    noteViewModel.getNoteById(noteId).value?.deadline,  // Lấy deadline kiểu Date
                    categoryId ?: 0
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
