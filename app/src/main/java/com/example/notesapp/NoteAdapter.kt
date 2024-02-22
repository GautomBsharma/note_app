package com.example.notesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter ( var context: Context,var notes:List<Note>):RecyclerView.Adapter<NoteAdapter.NoteHolder>(){

    private val db:DatabaseHelper = DatabaseHelper(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item,parent,false)
        return NoteHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
       val data = notes[position]
        holder.tvTitle.text = data.title
        holder.tvnote.text = data.notedes
        holder.editb.setOnClickListener {
            val intent =Intent(context,UpdateActivity::class.java).apply {
                putExtra("ID",data.id)


            }
            context.startActivity(intent)
        }
        holder.delet.setOnClickListener {
            db.deleteNote(data.id)
            refreshData(db.getallNote())
            Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
        }
    }
    inner class NoteHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val tvTitle = itemView.findViewById<TextView>(R.id.tvtitle)
        val tvnote = itemView.findViewById<TextView>(R.id.tvnote)
        val editb = itemView.findViewById<ImageView>(R.id.editbtn)
        val delet = itemView.findViewById<ImageView>(R.id.btndela)
    }
    fun refreshData(newNote :List<Note>){
        notes = newNote
        notifyDataSetChanged()

    }
}