package cs349.a3battleship.model.ships

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Orientation

class Carrier(override var orientation: Orientation,
              override var location : Cell
) : Ship() {
    override val length = 5
    override val shipType = ShipType.Carrier
    override var shipCells = mutableListOf(Cell(0,0))

    init {
        shipCells = MutableList(length) { idx ->
            var delta = when (orientation) {
                Orientation.VERTICAL -> Cell(0, 1)
                Orientation.HORIZONTAL -> Cell(1, 0)
            }
            location + delta * idx;
        }
    }
}