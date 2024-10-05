package com.example.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.example.todoapp.R
import com.example.todoapp.adapter.NoteAdapter
import com.example.todoapp.model.Category
import com.example.todoapp.model.Note
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var noteList: List<Note>
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var selectDayTextView: TextView
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        val notes = arguments?.getSerializable("noteList") as? ArrayList<Note>
        val categories = arguments?.getSerializable("categoryList") as? ArrayList<Category>

        selectDayTextView = view.findViewById(R.id.selectday)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)

        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val selectedDate = eventDay.calendar.time
                selectDayTextView.text = "Selected date: ${dateFormat.format(selectedDate)}"
                filterNotesByDate(selectedDate)
            }
        })

        notes?.let {
            noteList = it
            setupRecyclerView(view, categories ?: emptyList())

            val eventDays = ArrayList<EventDay>()
            notes.forEach { note ->
                note.deadline?.let { deadline ->
                    val eventDay = Calendar.getInstance().apply { time = deadline }
                    val taskDrawable = R.drawable.task
                    eventDays.add(EventDay(eventDay, taskDrawable))
                }
            }

            calendarView.setEvents(eventDays)

            val today = Calendar.getInstance().time
            selectDayTextView.text = "Selected date: ${dateFormat.format(today)}"
            filterNotesByDate(today)
        }

        return view
    }

    private fun setupRecyclerView(view: View, categories: List<Category>) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        noteAdapter = NoteAdapter(noteList, categories) { note ->
        }
        recyclerView.adapter = noteAdapter
    }

    private fun filterNotesByDate(selectedDate: Date) {
        val selectedDateString = dateFormat.format(selectedDate)

        val filteredNotes = noteList.filter { note ->
            note.deadline?.let { dateFormat.format(it) == selectedDateString } ?: false
        }

        noteAdapter = NoteAdapter(filteredNotes, categories = emptyList()) { note ->
        }
        recyclerView.adapter = noteAdapter
    }
}
