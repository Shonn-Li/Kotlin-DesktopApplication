package cs349.a3battleship.model.ships

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Orientation

class Destroyer(override var orientation: Orientation,
                override var location : Cell
) : Ship() {
    override val length = 2
    override val shipType = ShipType.Destroyer
    override var shipCells = MutableList(length) { idx ->
        var delta = when (orientation) {
            Orientation.VERTICAL -> Cell(0, 1)
            Orientation.HORIZONTAL -> Cell(1, 0)
        }
        location + delta * idx;
    }
}