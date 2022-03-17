package cs349.a3battleship.model

import cs349.a3battleship.model.ships.Ship
import cs349.a3battleship.model.ships.ShipType

class Board (var dimension: Int) {

    var placedShips = mutableListOf<Ship>()
    var attackedCells = mutableListOf<Cell>()

    /**
     * Adds newShip to the board if it fits on the board.
     *
     * @return a ship if newShip was added, and null otherwise
     */
    fun placeShip(shipType: ShipType, orientation : Orientation, cell: Cell) : Ship? {
        val newShip = Ship.MakeShip(shipType, orientation, cell)
        return if (newShip.getCells().any {
                (it.x in (0 until dimension) && it.y in (0 until dimension)).not() }) {
            null
        } else if (placedShips.any {
                it.isOverlap(newShip)}) {
            null
        } else {
            placedShips.add(newShip)
            newShip
        }
    }

    /**
     * Removes existingShip from the board
     */
    fun removeShip(existingShip : Ship) {
        placedShips.remove(existingShip)
    }

    /**
     * Attacks cell.
     *
     * @return the ship in cell if one exists, and null otherwise
     */
    fun attackCell(cell : Cell) {
        attackedCells.add(cell)
        placedShips.forEach { ship -> ship.attack(cell) }
    }

    /**
     *
     * @return true if the player still has at least one ship afloat, and false otherwise
     */
    fun hasShips() : Boolean {
        return (placedShips.all { ship -> ship.isSunk() }).not()
    }
}