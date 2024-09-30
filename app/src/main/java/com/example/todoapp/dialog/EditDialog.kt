package com.example.todoapp.dialog

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todoapp.data.Note
import com.example.todoapp.data.NoteViewModel
import com.example.todoapp.databinding.FragmentEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var noteViewModel: NoteViewModel

    private var noteId: Int = 0
    private var title: String? = null
    private var description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            noteId = it.getInt("noteId")
            title = it.getString("title")
            description = it.getString("description")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        noteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        binding.titleAddText.setText(title)
        binding.descriptionAddText.setText(description)

        binding.buttonsave.setOnClickListener {
            val newTitle = binding.titleAddText.text.toString()
            val newDescription = binding.descriptionAddText.text.toString()

            if (newTitle.isNotBlank() && newDescription.isNotBlank()) {
                showSaveConfirmationDialog(newTitle, newDescription)
            } else {
                Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun showSaveConfirmationDialog(newTitle: String, newDescription: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Save Note")
            .setMessage("Are you sure you want to save this note?")
            .setPositiveButton("Save") { dialog, _ ->
                val updatedNote = Note(noteId, newTitle, newDescription)
                noteViewModel.update(updatedNote)
                dismiss()
                findNavController().popBackStack()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }
}
