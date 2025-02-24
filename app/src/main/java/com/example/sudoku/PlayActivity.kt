package com.example.sudoku

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import com.example.sudoku.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding
    private lateinit var gridLayout: GridLayout
    private lateinit var sudokuGame: SudokuGame
    private lateinit var timerHandler: Handler
    private lateinit var timerRunnable: Runnable
    private var secondsElapsed = 0
    private val fixedCells = mutableSetOf<Pair<Int, Int>>()
    private var selectedCell: TextView? = null
    private var isSelectedCell = false
    private var selectedRow: Int = -1
    private var selectedCol: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gridLayout = findViewById(R.id.sudokuNumbers)
        sudokuGame = SudokuGame()
        setupSudokuBoard()
        setupButtons()
        startTimer()

        binding.btnDelete.setOnClickListener {
            if (selectedCell != null && isSelectedCell && !((selectedRow to selectedCol) in fixedCells)) {
                selectedCell?.text = ""
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timerHandler.removeCallbacks(timerRunnable)
    }

    private fun startTimer() {
        timerHandler = Handler(Looper.getMainLooper())
        timerRunnable = object : Runnable {
            override fun run() {
                secondsElapsed++
                val minutes = secondsElapsed / 60
                val seconds = secondsElapsed % 60
                binding.tvTime.text = String.format("%02d:%02d", minutes, seconds)
                timerHandler.postDelayed(this, 1000)
            }
        }
        timerHandler.post(timerRunnable)
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
        if ((selectedCell != null) && isSelectedCell) {
            if ((selectedRow to selectedCol) in fixedCells) {
                return
            }
            selectedCell?.text = num
            if (!sudokuGame.isMoveCorrect(selectedRow, selectedCol, num.toInt())) {
                selectedCell?.setTextColor(Color.RED)
            } else {
                selectedCell?.setTextColor(Color.parseColor("#FF368819"))
            }
        }
    }

    private fun setupSudokuBoard() {
        val bigPadding = 10f
        val smallPadding = 4f

        gridLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val widthGL = gridLayout.width
            val heightGL = gridLayout.height

            val availableWidth = widthGL
            val availableHeight = heightGL

            val widthSizeCell = availableWidth / 9f
            val heightSizeCell = availableHeight / 9f

            var currX = 10f
            var currY = 10f

            for (i in 0 until 9) {
                for (j in 0 until 9) {

                    if (i % 3 == 0 && i != 0) {
                        currY += bigPadding
                    } else {
                        currY += smallPadding
                    }

                    if (j % 3 == 0 && j != 0) {
                        currX += bigPadding
                    } else {
                        currX += smallPadding
                    }

                    val cell = TextView(this).apply {
                        textSize = 20f
                        setTextColor(Color.BLACK)
                        gravity = Gravity.CENTER
                        layoutParams = GridLayout.LayoutParams().apply {
                            width = widthSizeCell.toInt()
                            height = heightSizeCell.toInt()
                        }
                        text =
                            if (sudokuGame.puzzle[i][j] != 0) sudokuGame.puzzle[i][j].toString() else ""

                        setOnClickListener {
                            selectCell(this, i, j)
                        }
                    }
                    if (sudokuGame.puzzle[i][j] != 0) {
                        fixedCells.add(i to j)
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
        selectedCell?.setBackgroundColor(Color.TRANSPARENT)
        if (selectedCell === cell) {
            isSelectedCell = false
            selectedRow = -1
            selectedCol = -1
            return
        }
        selectedCell = cell
        isSelectedCell = true
        selectedRow = row
        selectedCol = col

        clearPreviousSelection()

        // Выделяем строку и столбец
        for (i in 0 until 9) {
            val rowCell = gridLayout.getChildAt(row * 9 + i) as TextView
            val colCell = gridLayout.getChildAt(i * 9 + col) as TextView
            rowCell.setBackgroundColor(Color.parseColor("#E0E0E0")) // Серый цвет выделения
            colCell.setBackgroundColor(Color.parseColor("#E0E0E0"))
        }


        // Определяем границы квадрата 3×3
        val startRow = (row / 3) * 3
        val startCol = (col / 3) * 3

        // Выделяем 3×3 квадрат
        for (r in startRow until startRow + 3) {
            for (c in startCol until startCol + 3) {
                val squareCell = gridLayout.getChildAt(r * 9 + c) as TextView
                squareCell.setBackgroundColor(Color.parseColor("#E0E0E0")) // Голубой цвет выделения
            }
        }

        cell.setBackgroundColor(Color.LTGRAY)


    }

    private fun clearPreviousSelection() {
        for (i in 0 until gridLayout.childCount) {
            val cell = gridLayout.getChildAt(i) as TextView
            cell.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}