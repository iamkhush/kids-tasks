package com.example.myfirstapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class AnalogClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = RectF()
    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f
    
    private var currentHour = 0
    private var currentMinute = 0
    private var currentSecond = 0

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        paint.color = Color.BLACK
        
        // Initialize text paint for hour numbers
        textPaint.color = Color.BLACK
        textPaint.textSize = 16f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isFakeBoldText = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
        radius = (minOf(w, h) / 2f) - 10f
        rect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // Draw clock face
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        canvas.drawCircle(centerX, centerY, radius, paint)
        
        // Draw clock border
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = 4f
        canvas.drawCircle(centerX, centerY, radius, paint)
        
        // Draw hour numbers
        drawHourNumbers(canvas)
        
        // Draw clock hands
        drawClockHands(canvas)
    }

    private fun drawHourMarkers(canvas: Canvas) {
        paint.strokeWidth = 3f
        paint.color = Color.BLACK
        
        for (i in 0..11) {
            val angle = Math.toRadians(i * 30.0 - 90.0)
            val startX = centerX + (radius - 15) * cos(angle).toFloat()
            val startY = centerY + (radius - 15) * sin(angle).toFloat()
            val endX = centerX + radius * cos(angle).toFloat()
            val endY = centerY + radius * sin(angle).toFloat()
            
            canvas.drawLine(startX, startY, endX, endY, paint)
        }
    }

    private fun drawHourNumbers(canvas: Canvas) {
        for (i in 1..12) {
            val angle = Math.toRadians(i * 30.0 - 90.0)
            val numberRadius = radius - 20f // Position numbers more outward
            val x = centerX + numberRadius * cos(angle).toFloat()
            val y = centerY + numberRadius * sin(angle).toFloat()

            // Adjust Y position for text alignment
            val textBounds = Rect()
            textPaint.getTextBounds(i.toString(), 0, i.toString().length, textBounds)
            val adjustedY = y + (textBounds.height() / 2f)
            
            canvas.drawText(i.toString(), x, adjustedY, textPaint)
        }
    }

    private fun drawClockHands(canvas: Canvas) {
        val calendar = Calendar.getInstance()
        currentHour = calendar.get(Calendar.HOUR)
        currentMinute = calendar.get(Calendar.MINUTE)
        currentSecond = calendar.get(Calendar.SECOND)
        
        // Draw hour hand
        val hourAngle = Math.toRadians((currentHour * 30.0) + (currentMinute * 0.5) - 90.0)
        val hourHandLength = radius * 0.5f
        val hourEndX = centerX + hourHandLength * cos(hourAngle).toFloat()
        val hourEndY = centerY + hourHandLength * sin(hourAngle).toFloat()
        
        paint.strokeWidth = 6f
        paint.color = Color.BLACK
        canvas.drawLine(centerX, centerY, hourEndX, hourEndY, paint)
        
        // Draw minute hand
        val minuteAngle = Math.toRadians(currentMinute * 6.0 - 90.0)
        val minuteHandLength = radius * 0.7f
        val minuteEndX = centerX + minuteHandLength * cos(minuteAngle).toFloat()
        val minuteEndY = centerY + minuteHandLength * sin(minuteAngle).toFloat()
        
        paint.strokeWidth = 4f
        paint.color = Color.BLACK
        canvas.drawLine(centerX, centerY, minuteEndX, minuteEndY, paint)
        
        // Draw second hand
        val secondAngle = Math.toRadians(currentSecond * 6.0 - 90.0)
        val secondHandLength = radius * 0.8f
        val secondEndX = centerX + secondHandLength * cos(secondAngle).toFloat()
        val secondEndY = centerY + secondHandLength * sin(secondAngle).toFloat()
        
        paint.strokeWidth = 2f
        paint.color = Color.RED
        canvas.drawLine(centerX, centerY, secondEndX, secondEndY, paint)
        
        // Draw center dot
        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        canvas.drawCircle(centerX, centerY, 6f, paint)
    }

    fun updateTime() {
        invalidate()
    }
} 