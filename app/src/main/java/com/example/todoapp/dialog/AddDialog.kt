package com.example.todoapp.dialog

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.model.Note
import com.example.todoapp.data.viewmodel.NoteViewModel
import com.example.todoapp.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class AddDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var noteViewModel: NoteViewModel

    private val categoryList = listOf("Work", "Personal", "Shopping", "Health", "Other")
    private var selectedCategoryId: Int = 1
    private var selectedDate: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        noteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        setupCategorySpinner()

        binding.Adddate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.buttonadd.setOnClickListener {
            addNote()
        }

        return binding.root
    }

    private fun setupCategorySpinner() {
        binding.categorySpinner.apply {
            adapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, categoryList) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent) as TextView
                    view.setTextColor(Color.WHITE)
                    return view
                }
            }.also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    selectedCategoryId = position + 1
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedCategoryId = 1
                }
            }
        }
    }

    private fun addNote() {
        val title = binding.titleAddText.text.toString()
        val description = binding.descriptionAddText.text.toString()

        if (title.isBlank() || description.isBlank()) {
            Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(
            id = 0,
            title = title,
            description = description,
            deadline = selectedDate,
            categoryId = selectedCategoryId
        )
        noteViewModel.insert(note)
        dismiss() // Đóng dialog
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDateTime = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }
                selectedDate = selectedDateTime.time // Lưu Date đã chọn

                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.Addtime.setText(formatter.format(selectedDate!!))
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}