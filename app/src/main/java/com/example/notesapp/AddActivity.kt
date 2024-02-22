package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var db:DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseHelper(this)
        binding.btnAddNote.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        if (binding.title.text.toString().isEmpty()){
            binding.title.error ="Enter title"
        }
        else if (binding.notes.text.toString().isEmpty()){
            binding.notes.error ="Enter note"
        }
        else{
           insertData()
        }
    }

     private fun insertData() {
        val title = binding.title.text.toString()
        val notedes = binding.notes.text.toString()
        val note = Note(0,title,notedes)
        db.insertN(note)
        finish()
        Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()
    }



}