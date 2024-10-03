package com.example.todoapp.dialog

import android.app.DatePickerDialog
<<<<<<< HEAD
import android.graphics.Color
=======
>>>>>>> 5083fc4cb0e587903443b5cba517b316f862647c
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
import java.util.Calendar

class AddDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var noteViewModel: NoteViewModel

    // Danh sách category động
    private val categoryList = listOf("Work", "Personal", "Shopping", "Health", "Other")
    private var selectedCategoryId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        noteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

<<<<<<< HEAD
        // Thiết lập Spinner với danh sách động
        binding.categorySpinner.apply {
            adapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, categoryList) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent) as TextView
                    view.setTextColor(Color.WHITE) // Đặt màu chữ là trắng
                    return view
                }

            }.also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            // Xử lý sự kiện chọn category
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    selectedCategoryId = position // Lưu ID của category được chọn
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedCategoryId = 0 // Mặc định là "Work" nếu không chọn
                }
            }
        }

=======
>>>>>>> 5083fc4cb0e587903443b5cba517b316f862647c
        binding.Adddate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.buttonadd.setOnClickListener {
            val title = binding.titleAddText.text.toString()
            val description = binding.descriptionAddText.text.toString()
            val deadline = binding.Addtime.text.toString()

            if (title.isNotBlank() && description.isNotBlank()) {
<<<<<<< HEAD
                // Tạo đối tượng Note với category động
                val note = Note(0, title, description, deadline, selectedCategoryId)
=======
                // Tạo đối tượng Note với thời gian
                val note = Note(0, title, description, deadline)
>>>>>>> 5083fc4cb0e587903443b5cba517b316f862647c
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
