package com.example.sudoku.grid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.sudoku.R
import kotlin.math.min

class SudokuGridView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val thinLinePaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.cell_color)
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }

    private val thickLinePaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.cell_color)
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cellSizeX = width / 9f
        val cellSizeY = height / 9f

        for (i in 0..8) {
            val paint = if (i % 3 == 0) thickLinePaint else thinLinePaint
            val posX = i * cellSizeX
            val posY = i * cellSizeY

            canvas.drawLine(posX, 0f, posX, height.toFloat(), paint)
            canvas.drawLine(0f, posY, width.toFloat(), posY, paint)
        }

        val lastPosX = 9 * cellSizeX
        val lastPosY = 9 * cellSizeY
        canvas.drawLine(lastPosX, 0f, lastPosX, height.toFloat(), thinLinePaint)
        canvas.drawLine(0f, lastPosY, width.toFloat(), lastPosY, thinLinePaint)
    }

}

