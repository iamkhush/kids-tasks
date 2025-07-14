package com.example.myfirstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.view.View
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    
    private lateinit var taskImageView: ImageView
    private lateinit var taskTitleTextView: TextView
    private lateinit var dateTimeTextView: TextView
    private lateinit var analogClockView: AnalogClockView
    private lateinit var completeTaskButton: Button
    private lateinit var resetButton: Button
    private val handler = Handler(Looper.getMainLooper())
    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            updateDateTime()
            analogClockView.updateTime()
            handler.postDelayed(this, 1000) // Update every second for smooth clock movement
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set fullscreen mode - hide all system UI
        setFullscreenMode()
        
        setContentView(R.layout.activity_main)
        
        initializeViews()
        setupClickListeners()
        updateTaskDisplay()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Re-hide system UI when app gains focus
            setFullscreenMode()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop time updates
        handler.removeCallbacks(updateTimeRunnable)
    }

    private fun setFullscreenMode() {
        // Hide the status bar and navigation bar with most restrictive flags
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LOW_PROFILE
        )
        
        // Keep screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        // Additional flags for maximum fullscreen
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun updateDateTime() {
        val calendar = Calendar.getInstance()
        
        // Format day of week
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val dayOfWeek = dayFormat.format(calendar.time)
        
        // Format time
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val time = timeFormat.format(calendar.time)
        
        // Format date (day number, month name, year)
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        val fullDate = dateFormat.format(calendar.time)
        
        // Update the TextView
        dateTimeTextView.text = "$dayOfWeek \n$time\n$fullDate"
    }

    private fun setupFullscreenTouchListener() {
        // Set up a touch listener on the root view to maintain fullscreen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnTouchListener { _, _ ->
            // Re-hide system UI immediately on any touch
            setFullscreenMode()
            false // Don't consume the touch event
        }
    }

    private fun initializeViews() {
        taskImageView = findViewById(R.id.taskImageView)
        taskTitleTextView = findViewById(R.id.taskTitleTextView)
        dateTimeTextView = findViewById(R.id.dateTimeTextView)
        analogClockView = findViewById(R.id.analogClockView)
        completeTaskButton = findViewById(R.id.completeTaskButton)
        resetButton = findViewById(R.id.resetButton)
        
        // Initialize date and time
        updateDateTime()
        analogClockView.updateTime()
        
        // Start time updates
        handler.post(updateTimeRunnable)
        
        // Set up touch listener to maintain fullscreen
        setupFullscreenTouchListener()
    }

    private fun setupClickListeners() {
        completeTaskButton.setOnClickListener {
            markTaskAsCompleted()
        }
        
        resetButton.setOnClickListener {
            resetAllTasks()
        }
    }

    private fun updateTaskDisplay() {
        val currentTask = TaskManager.getCurrentTask()
        val isLastTask = TaskManager.getNextTask() == null

        if (currentTask != null) {
            taskImageView.setImageResource(currentTask.imageResourceId)
            taskTitleTextView.text = currentTask.title

            if (isLastTask) {
                completeTaskButton.visibility = android.view.View.GONE
            } else {
                completeTaskButton.visibility = android.view.View.VISIBLE
            }
        }
    }

    private fun markTaskAsCompleted() {
        val currentTask = TaskManager.getCurrentTask()
        if (currentTask != null) {
            // Mark task as completed
            TaskManager.markCurrentTaskAsCompleted()
            
            // Update display
            updateTaskDisplay()
        }
    }

    private fun resetAllTasks() {
        TaskManager.resetTasks()
        Toast.makeText(this, "Tasks reset! Start over!", Toast.LENGTH_SHORT).show()
        updateTaskDisplay()
    }
}
