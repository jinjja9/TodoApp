package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.data.NoteViewModel
import com.example.todoapp.databinding.FragmentHomeScreenBinding
import com.example.todoapp.dialog.AddDialog

class HomeScreenFragment : Fragment() {

    data class NoteItem(val title: String, val description: String)

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var noteAdapter: NoteAdapter
    private val noteList = mutableListOf<NoteItem>()
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        // Khởi tạo adapter cho danh sách ghi chú
        noteAdapter = NoteAdapter(noteList)
        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Khởi tạo ViewModel
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Quan sát dữ liệu từ ViewModel
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            noteList.clear()
            noteList.addAll(notes.map { NoteItem(it.title, it.description) })
            noteAdapter.notifyDataSetChanged() // Cập nhật RecyclerView
        }

        // Mở dialog để thêm ghi chú mới
        binding.addbutton.setOnClickListener {
            val addDialog = AddDialog()
            addDialog.show(parentFragmentManager, "AddDialog")
        }

        return binding.root
    }
}
