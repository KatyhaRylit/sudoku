package com.example.sudoku

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import com.example.sudoku.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding
    private lateinit var gridLayout: GridLayout
    private lateinit var sudokuGame: SudokuGame
    private var selectedCell: TextView? = null
    private var isSelectedCell = false
    private var selectedRow: Int = -1
    private var selectedCol: Int = -1

    private val sudokuBoard = arrayOf(
        intArrayOf(0, 0, 1, 0, 0, 0, 4, 3, 0),
        intArrayOf(0, 8, 0, 0, 6, 0, 0, 0, 1),
        intArrayOf(7, 0, 0, 0, 9, 4, 0, 6, 0),
        intArrayOf(2, 0, 0, 0, 0, 0, 1, 9, 6),
        intArrayOf(8, 1, 0, 4, 0, 2, 0, 0, 0),
        intArrayOf(0, 3, 0, 7, 0, 1, 0, 0, 8),
        intArrayOf(7, 9, 0, 0, 0, 0, 0, 2, 0),
        intArrayOf(0, 0, 0, 2, 7, 8, 0, 0, 9),
        intArrayOf(8, 0, 0, 0, 4, 0, 5, 0, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gridLayout = findViewById(R.id.sudokuNumbers)

        sudokuGame = SudokuGame()
        setupSudokuBoard()
        setupButtons()

    }

    private fun setupButtons() {
        val numBtns = listOf(
            binding.btn1,
            binding.btn2,
            binding.btn3,
            binding.btn4,
            binding.btn5,
            binding.btn6,
            binding.btn7,
            binding.btn8,
            binding.btn9,
        )
        numBtns.forEach { btn ->
            btn.setOnClickListener {
                onNumberClick(btn.text.toString())
            }
        }
    }

    private fun onNumberClick(num: String) {
        Log.d("MyLog", "numberClick")
        if ((selectedCell != null) && isSelectedCell) {
            selectedCell?.text = num
        }
    }

    private fun setupSudokuBoard() {
        val padding = 10f

        gridLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val widthGL = gridLayout.width
            val heightGL = gridLayout.height

            val availableWidth = widthGL - padding
            val availableHeight = heightGL - padding

            val widthSizeCell = availableWidth / 9f
            val heightSizeCell = availableHeight / 9f

            var currX = 0f
            var currY = 0f

            for (i in 0 until 9) {
                for (j in 0 until 9) {

                    if (i % 3 == 0 && i != 0) {
                        currY += padding
                    }

                    if (j % 3 == 0 && j != 0) {
                        currX += padding
                    }

                    val cell = TextView(this).apply {
                        textSize = 20f
                        setTextColor(Color.BLACK)
                        gravity = Gravity.CENTER
                        layoutParams = GridLayout.LayoutParams().apply {
                            width = widthSizeCell.toInt()
                            height = heightSizeCell.toInt()
                        }
                        text = if (sudokuGame.puzzle[i][j] != 0) sudokuGame.puzzle[i][j].toString() else ""


                        setOnClickListener {
                            selectCell(this, i, j)
                        }
                    }

                    gridLayout.addView(cell)

                    currX += widthSizeCell
                }

                currX = 0f
                currY += heightSizeCell
            }

        }
    }


    private fun selectCell(cell: TextView, row: Int, col: Int) {
        if (selectedCell === cell) {
            cell.setBackgroundColor(Color.TRANSPARENT)
            isSelectedCell = false
            selectedRow = -1
            selectedCol = -1
            return
        }
        selectedCell?.setBackgroundColor(Color.TRANSPARENT)
        cell.setBackgroundColor(Color.LTGRAY)
        selectedCell = cell
        isSelectedCell = true
        selectedRow = row
        selectedCol = col
    }


}