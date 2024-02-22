package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db:DatabaseHelper
    private var notid :Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseHelper(this)
        notid = intent.getIntExtra("ID",-1)
        if (notid==-1){
            finish()
            return
        }
        val note = db.getNotebyId(notid)
        binding.utitle.setText(note.title)
        binding.unotes.setText(note.notedes)
        binding.AddUpdateNote.setOnClickListener {
            val newtit = binding.utitle.text.toString()
            val newdes = binding.unotes.text.toString()
            val updaten = Note(notid,newtit,newdes)
            db.updateNote(updaten)
            finish()
            Toast.makeText(this, "Note Update", Toast.LENGTH_SHORT).show()
        }

    }
}