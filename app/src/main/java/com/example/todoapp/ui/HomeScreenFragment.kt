package com.example.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.InvalidationTracker
import com.example.todoapp.R
import com.example.todoapp.adapter.NoteAdapter
import com.example.todoapp.model.Note
import com.example.todoapp.model.Category
import com.example.todoapp.data.viewmodel.NoteViewModel
import com.example.todoapp.databinding.FragmentHomeScreenBinding
import com.example.todoapp.dialog.AddDialog

class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var noteAdapter: NoteAdapter
    private val noteList = mutableListOf<Note>()
    private lateinit var noteViewModel: NoteViewModel

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
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        noteAdapter = NoteAdapter(noteList, categoryList) { note ->
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailFragment(
                noteId = note.id
            )
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Ban đầu hiển thị tất cả các ghi chú
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            noteList.clear()
            noteList.addAll(notes)
            noteAdapter.notifyDataSetChanged()
        }

        // Nút thêm ghi chú
        binding.addbutton.setOnClickListener {
            AddDialog().show(parentFragmentManager, "AddFragment")
        }

        // Nhấn vào biểu tượng filter để chọn category
        binding.filter.setOnClickListener {
            showCategoryPopup(it)
        }

        // Thiết lập màu cho search view
        val searchText = binding.seaching.findViewById<android.widget.EditText>(androidx.appcompat.R.id.search_src_text)
        searchText.setTextColor(resources.getColor(R.color.white))
        searchText.setHintTextColor(resources.getColor(R.color.white))

        // Tìm kiếm ghi chú
        binding.seaching.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText ?: ""
                noteViewModel.searchNotes(query).observe(viewLifecycleOwner) { notes ->
                    noteList.clear()
                    noteList.addAll(notes)
                    noteAdapter.notifyDataSetChanged()
                }
                return true
            }
        })

        return binding.root
    }

    private fun showCategoryPopup(anchorView: View) {
        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.popup_category_filter, null)

        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        // TextView trong popup
        popupView.apply {
            findViewById<TextView>(R.id.menu_item_all).setOnClickListener {
                updateCategory("All", popupWindow)
            }
            findViewById<TextView>(R.id.menu_item_work).setOnClickListener {
                updateCategory("Work", popupWindow)
            }
            findViewById<TextView>(R.id.menu_item_personal).setOnClickListener {
                updateCategory("Personal", popupWindow)
            }
            findViewById<TextView>(R.id.menu_item_shopping).setOnClickListener {
                updateCategory("Shopping", popupWindow)
            }
            findViewById<TextView>(R.id.menu_item_health).setOnClickListener {
                updateCategory("Health", popupWindow)
            }
            findViewById<TextView>(R.id.menu_item_other).setOnClickListener {
                updateCategory("Other", popupWindow)
            }
            findViewById<TextView>(R.id.menu_item_deadline).setOnClickListener { // Thêm sự kiện cho deadline
                updateCategory("Deadline", popupWindow)
            }
        }

        popupWindow.showAsDropDown(anchorView)
    }


    private fun updateCategory(category: String, popupWindow: PopupWindow) {
        popupWindow.dismiss()

        // Cập nhật TextView `namefillter` theo danh mục được chọn
        binding.namefillter.text = when (category) {
            "All" -> "All"
            "Work" -> "Work"
            "Personal" -> "Personal"
            "Shopping" -> "Shopping"
            "Health" -> "Health"
            "Other" -> "Other"
            "Deadline" -> "Deadline"
            else -> "All" // Giá trị mặc định
        }

        when (category) {
            "All" -> {
                noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
                    noteList.clear()
                    noteList.addAll(notes)
                    noteAdapter.notifyDataSetChanged()
                }
            }
            "Work" -> filterNotesByCategory(1)
            "Personal" -> filterNotesByCategory(2)
            "Shopping" -> filterNotesByCategory(3)
            "Health" -> filterNotesByCategory(4)
            "Other" -> filterNotesByCategory(5)
            "Deadline" -> filterNotesByDeadline()
        }
    }


    private fun filterNotesByCategory(categoryId: Int) {
        noteViewModel.filterNotesByCategory(categoryId).observe(viewLifecycleOwner) { filteredNotes ->
            noteList.clear()
            noteList.addAll(filteredNotes)
            noteAdapter.notifyDataSetChanged()
        }
    }

    private fun filterNotesByDeadline() {
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            val filteredNotes = notes.filter { it.deadline != null && it.deadline.isNotEmpty() }
            noteList.clear()
            noteList.addAll(filteredNotes)
            noteAdapter.notifyDataSetChanged()
        }
    }

}
