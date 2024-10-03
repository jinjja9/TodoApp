package com.example.todoapp.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todoapp.model.Note
import com.example.todoapp.data.viewmodel.NoteViewModel
import com.example.todoapp.databinding.FragmentEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class EditDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var noteViewModel: NoteViewModel

    private var noteId: Int = 0
    private var title: String? = null
    private var description: String? = null
    private var deadline: String? = null
    private var categoryId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            noteId = it.getInt("noteId")
            title = it.getString("title")
            description = it.getString("description")
            deadline = it.getString("deadline")
            categoryId = it.getInt("categoryId")         }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        noteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        // Điền thông tin vào EditText
        binding.titleAddText.setText(title)
        binding.descriptionAddText.setText(description)
        binding.Addtime.setText(deadline)

        binding.Adddate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.buttonsave.setOnClickListener {
            val newTitle = binding.titleAddText.text.toString()
            val newDescription = binding.descriptionAddText.text.toString()
            val newDeadline = binding.Addtime.text.toString()

            if (newTitle.isNotBlank() && newDescription.isNotBlank()) {
                showSaveConfirmationDialog(newTitle, newDescription, newDeadline)
            } else {
                Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                binding.Addtime.setText(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showSaveConfirmationDialog(newTitle: String, newDescription: String, newDeadline: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Save Note")
            .setMessage("Are you sure you want to save this note?")
            .setPositiveButton("Save") { dialog, _ ->
                val updatedNote = Note(noteId, newTitle, newDescription, newDeadline, categoryId) // Sử dụng ID của Category
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
