package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.adapter.NoteAdapter
import com.example.todoapp.data.viewmodel.NoteViewModel
import com.example.todoapp.model.Note

class CalendarFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteList: List<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        // Khởi tạo ViewModel
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Nhận danh sách ID từ arguments
        val noteIds = arguments?.getIntArray("noteList")

        // Kiểm tra nếu có noteIds thì lấy danh sách Note tương ứng
        noteIds?.let {
            noteViewModel.getNotesByIds(it).observe(viewLifecycleOwner) { notes ->
                noteList = notes
                setupRecyclerView(view)  // Thiết lập RecyclerView sau khi có danh sách Notes
            }
        }

        return view
    }

    private fun setupRecyclerView(view: View) {
        // Tìm RecyclerView theo ID
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        // Thiết lập LayoutManager và Adapter cho RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Adapter với danh sách note đã nhận
        recyclerView.adapter = NoteAdapter(noteList, emptyList()) { note ->
            // Xử lý sự kiện click cho từng Note nếu cần
        }
    }
}
