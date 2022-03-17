package cs349.a3battleship.model

/**
 * A cell represent a single cell on the board.
 * @param x represents the x-coordinate od the cell (between 0 and board.dimension - 1)
 * @param y represents the y-coordinate od the cell (between 0 and board.dimension - 1)
 * @hparam hit expresses if the cell was attacked previously
 */
data class Cell(var x : Int, var y : Int, var attacked : Boolean = false) {

    operator fun plus(pt: Cell): Cell {
        return Cell(x + pt.x, y + pt.y)
    }

    operator fun times(i: Int): Cell {
        return Cell(i * x, i * y)
    }

    override fun equals(other: Any?): Boolean {
        return (other is Cell) && (x == other.x) && (y == other.y)
    }
}
