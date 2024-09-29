package com.example.todoapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.Note
import com.example.todoapp.data.NoteViewModel
import com.example.todoapp.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class AddDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        noteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        binding.buttonadd.setOnClickListener {
            val title = binding.titleAddText.text.toString()
            val description = binding.descriptionAddText.text.toString()
            val createDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            if (title.isNotBlank() && description.isNotBlank()) {
                val note = Note(0, title, description)
                noteViewModel.insert(note)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}
