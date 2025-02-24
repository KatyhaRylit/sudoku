package com.example.sudoku

class SudokuGame {
    val solution: Array<IntArray> = generateSolvedSudoku()
    var puzzle: Array<IntArray> = removeNumbers(solution, 3)

    private fun generateSolvedSudoku(): Array<IntArray> {
        val board = Array(9) { IntArray(9) { 0 } }
        fillBoard(board)
        return board
    }


    fun isFinishedGame() : Boolean {
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (solution[i][j] != puzzle[i][j]) {
                    return false
                }
            }
        }
        return true
    }

    private fun fillBoard(board: Array<IntArray>): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (board[row][col] == 0) {
                    val numbers = (1..9).shuffled()
                    for (num in numbers) {
                        if (isValidMove(board, row, col, num)) {
                            board[row][col] = num
                            if (fillBoard(board)) return true
                            board[row][col] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun isValidMove(board: Array<IntArray>, row: Int, col: Int, num: Int): Boolean {
        for (i in 0 until 9) {
            if (board[row][i] == num || board[i][col] == num) return false
        }
        val boxRowStart = row / 3 * 3
        val boxColStart = col / 3 * 3
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[boxRowStart + i][boxColStart + j] == num) return false
            }
        }
        return true
    }

    private fun removeNumbers(board: Array<IntArray>, difficulty: Int): Array<IntArray> {
        val puzzle = board.map { it.clone() }.toTypedArray()
        var count = difficulty
        while (count > 0) {
            val row = (0 until 9).random()
            val col = (0 until 9).random()
            if (puzzle[row][col] != 0) {
                puzzle[row][col] = 0
                count--
            }
        }
        return puzzle
    }

    fun isMoveCorrect(row: Int, col: Int, num: Int): Boolean {
        return solution[row][col] == num
    }


}
