package com.example.realmdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdatabase.databinding.ItemLayoutBinding
import com.example.realmdatabase.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    var noteList: MutableList<Note> = mutableListOf()
    lateinit var onLongClicked: (Note, pos: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int = noteList.size

    inner class NoteViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.textNote.text = note.note
            binding.textTime.text = note.time

            itemView.setOnLongClickListener {
                onLongClicked(note, adapterPosition)
                true
            }
        }
    }
}