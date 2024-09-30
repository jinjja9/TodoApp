package com.example.todoapp.dialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.model.Note
import com.example.todoapp.data.NoteViewModel
import com.example.todoapp.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        noteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        binding.Adddate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.buttonadd.setOnClickListener {
            val title = binding.titleAddText.text.toString()
            val description = binding.descriptionAddText.text.toString()
            val deadline = binding.Addtime.text.toString()

            if (title.isNotBlank() && description.isNotBlank()) {
                // Tạo đối tượng Note với thời gian
                val note = Note(0, title, description, deadline)
                noteViewModel.insert(note)
                dismiss()
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
}
