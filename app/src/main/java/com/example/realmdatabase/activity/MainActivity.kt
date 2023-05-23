package com.example.realmdatabase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdatabase.R
import com.example.realmdatabase.adapter.NoteAdapter
import com.example.realmdatabase.databinding.ActivityMainBinding
import com.example.realmdatabase.databinding.AlertDialogBinding
import com.example.realmdatabase.manager.RealmManager
import com.example.realmdatabase.model.Note
import com.example.realmdatabase.util.Time

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val noteAdapter by lazy { NoteAdapter() }
    private val realmManager by lazy { RealmManager() }
    private var noteList: MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
            val divider = DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL)
            addItemDecoration(divider)
        }
        noteList = realmManager.getNotes().toMutableList()
        noteAdapter.noteList = noteList

        binding.floatingFab.setOnClickListener {
            showCustomDialog()
        }
        noteAdapter.onLongClicked = { note, pos ->
            realmManager.deleteNote(note.id)
            noteList.removeAt(pos)
            noteAdapter.notifyItemRemoved(pos)
        }

    }

    private fun showCustomDialog() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        val alertDialog = AlertDialog.Builder(this).create()
        val bn: AlertDialogBinding = AlertDialogBinding.bind(view)
        alertDialog.setView(view)

        bn.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        bn.btnSave.setOnClickListener {
            val text = bn.editAlert.text.toString().trim()
            val note = Note(realmManager.getNotes().size.toLong(), Time.timeStamp(), text)
            realmManager.saveNote(note)
            noteList.add(note)
            noteAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()
            binding.linearLayout.isVisible = noteList.isEmpty()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}