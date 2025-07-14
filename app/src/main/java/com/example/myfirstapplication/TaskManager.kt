package com.example.myfirstapplication

object TaskManager {
    private val tasks = mutableListOf<Task>()
    private var currentTaskIndex = 0

    init {
        // Initialize with sample tasks
        addSampleTasks()
    }

    private fun addSampleTasks() {
        // Morning and evening routines from the Swedish PDF
        tasks.add(Task(1, "Good morning", R.drawable.vakna))
        tasks.add(Task(2, "Toilet", R.drawable.toilet))
        tasks.add(Task(3, "Get Dressed", R.drawable.get_ready))
        tasks.add(Task(4, "Eat Breakfast", R.drawable.breakfast))
        tasks.add(Task(5, "Dishwasher", R.drawable.dishwasher))
        tasks.add(Task(6, "Brush", R.drawable.brush))
        tasks.add(Task(7, "Put on outer clothes", R.drawable.outerclothes))
        tasks.add(Task(8, "Go to School", R.drawable.goschool))
        tasks.add(Task(9, "Take Off clothes", R.drawable.takeoffclothe))
        tasks.add(Task(10, "Hang your jacket", R.drawable.hang_jacket))
        tasks.add(Task(11, "Wash Hands & face", R.drawable.wash_hands_face))
        tasks.add(Task(12, "Snacks", R.drawable.snacks))
        tasks.add(Task(13, "Evening stuff", R.drawable.evening))
        tasks.add(Task(14, "Dinner", R.drawable.dinner))
        tasks.add(Task(15, "Dishwasher", R.drawable.dishwasher))
        tasks.add(Task(16, "Wash Hands", R.drawable.hands_wash))
        tasks.add(Task(17, "Family time", R.drawable.family_play))
        tasks.add(Task(18, "Put stuff back", R.drawable.toys_back))
        tasks.add(Task(19, "Brush", R.drawable.brush))
        tasks.add(Task(20, "Toilet", R.drawable.toilet))
        tasks.add(Task(21, "Sleep", R.drawable.sleep))
    }

    fun getCurrentTask(): Task? {
        return if (currentTaskIndex < tasks.size) tasks[currentTaskIndex] else null
    }

    fun getNextTask(): Task? {
        return if (currentTaskIndex + 1 < tasks.size) tasks[currentTaskIndex + 1] else null
    }

    fun markCurrentTaskAsCompleted() {
        if (currentTaskIndex < tasks.size) {
            tasks[currentTaskIndex].isCompleted = true
            currentTaskIndex++
        }
    }

    fun resetTasks() {
        tasks.forEach { it.isCompleted = false }
        currentTaskIndex = 0
    }

    fun getAllTasks(): List<Task> = tasks.toList()

    fun getCompletedTasksCount(): Int = tasks.count { it.isCompleted }

    fun getTotalTasksCount(): Int = tasks.size
} 