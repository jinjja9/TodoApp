package com.example.todoapp.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.model.Note
import com.example.todoapp.data.viewmodel.NoteViewModel
import com.example.todoapp.databinding.FragmentEditBinding
import com.example.todoapp.model.Category
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var noteViewModel: NoteViewModel

    private var noteId: Int = 0
    private var title: String? = null
    private var description: String? = null
    private var deadline: Date? = null
    private var categoryId: Int? = null

    private val categoryList = listOf(
        Category(id = 1, name = "Work", iconResId = R.drawable.work),
        Category(id = 2, name = "Personal", iconResId = R.drawable.personal),
        Category(id = 3, name = "Shopping", iconResId = R.drawable.shopping),
        Category(id = 4, name = "Health", iconResId = R.drawable.heart),
        Category(id = 5, name = "Other", iconResId = R.drawable.others)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            noteId = it.getInt("noteId")
            title = it.getString("title")
            description = it.getString("description")

            val deadlineLong = it.getLong("deadline", -1L)
            if (deadlineLong != -1L) {
                deadline = Date(deadlineLong)
            }

            categoryId = it.getInt("categoryId")
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

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        if (deadline != null) {
            binding.Addtime.setText(dateFormat.format(deadline))
        } else {
            binding.Addtime.setText("No deadline")
        }

        binding.Adddate.setOnClickListener {
            showDatePickerDialog()
        }

        setupCategorySpinner()
        categoryId?.let { setCategorySpinnerSelection(it) }

        binding.buttonsave.setOnClickListener {
            val newTitle = binding.titleAddText.text.toString()
            val newDescription = binding.descriptionAddText.text.toString()

            if (newTitle.isNotBlank() && newDescription.isNotBlank()) {
                val selectedCategoryPosition = binding.categorySpinner.selectedItemPosition
                val selectedCategory = categoryList[selectedCategoryPosition]
                val newCategoryId = selectedCategory.id

                showSaveConfirmationDialog(newTitle, newDescription, deadline, newCategoryId)
            } else {
                Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun setupCategorySpinner() {
        val categoryNames = categoryList.map { it.name }.toTypedArray()
        binding.categorySpinner.adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categoryNames
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(Color.WHITE)
                return view
            }
        }.also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun setCategorySpinnerSelection(selectedCategoryId: Int) {
        val position = categoryList.indexOfFirst { it.id == selectedCategoryId }
        if (position >= 0) {
            binding.categorySpinner.setSelection(position)
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(Calendar.YEAR, selectedYear)
                calendar.set(Calendar.MONTH, selectedMonth)
                calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
                deadline = calendar.time  // Cập nhật deadline
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.Addtime.setText(dateFormat.format(deadline))  // Hiển thị ngày
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showSaveConfirmationDialog(newTitle: String, newDescription: String, deadline: Date?, categoryId: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Save TODO")
            .setMessage("Are you sure you want to save this TODO?")
            .setPositiveButton("Save") { dialog, _ ->
                val note = Note(noteId, newTitle, newDescription, deadline, categoryId)
                noteViewModel.update(note)
                dismiss()
                findNavController().popBackStack()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }
}
