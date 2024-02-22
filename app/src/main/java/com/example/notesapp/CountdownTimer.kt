package com.example.notesapp

import android.os.CountDownTimer
import android.widget.TextView
import java.util.concurrent.TimeUnit

class CountdownTimer (
    private val textViewDay: TextView,
    private val textViewTime: TextView,
    private val startTime: Long,
    private val duration: Long
) : CountDownTimer(duration, 1000) {

    override fun onTick(millisUntilFinished: Long) {
        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

        textViewDay.text = days.toString()
        textViewTime.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    override fun onFinish() {
        textViewDay.text = "0"
        textViewTime.text = "00:00:00"
        // Handle timer completion here (e.g., show message, play sound)
    }
}