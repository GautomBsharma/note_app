package com.example.notesapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import com.example.notesapp.databinding.ActivityTimeBinding
import java.util.*

class TimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeBinding // Initialize binding

    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var isRunning = false

    private lateinit var  handler : Handler
    private val updateTimeTask = object : Runnable {
        override fun run() {
            val updatedTime = SystemClock.elapsedRealtime() - startTime + elapsedTime
            val seconds = (updatedTime / 1000).toInt()
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            binding.tvTime.text = String.format("%02d:%02d:%02d", hours % 24, minutes % 60, seconds % 60)
            binding.dayTextView.text = days.toString()

            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler()
        binding.startButton.setOnClickListener {
            startTimer()
        }

        /*binding.btnRestart.setOnClickListener {
            resetTimer()
        }*/
    }

    private fun startTimer() {
        if (!isRunning) {
            startTime = SystemClock.elapsedRealtime()
            handler.post(updateTimeTask)
            isRunning = true
        }
    }

    private fun resetTimer() {
        handler.removeCallbacks(updateTimeTask)
        elapsedTime = 0
        isRunning = false
        binding.tvTime.text = "00:00:00" // Update with binding
        binding.dayTextView.text = "0" // Update with binding
    }

    override fun onPause() {
        super.onPause()
        if (isRunning) {
            elapsedTime = SystemClock.elapsedRealtime() - startTime
            handler.removeCallbacks(updateTimeTask)
            isRunning = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (isRunning) {
            startTime = SystemClock.elapsedRealtime() - elapsedTime
            handler.post(updateTimeTask)
        }
    }
}