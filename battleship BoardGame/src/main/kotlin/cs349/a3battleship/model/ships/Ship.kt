package cs349.a3battleship.model.ships

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Orientation
import cs349.a3battleship.model.Player

abstract class Ship() {
    protected abstract val length : Int
    protected abstract var orientation : Orientation
    protected abstract var location : Cell
    protected abstract var shipCells : MutableList<Cell>

    abstract val shipType : ShipType

    companion object {
        /**
         * Creates a new ship.
         * @param shipType the type of the ship
         * @param orientation the orientation of the ship
         * @param bowCell the cell coordinates of the bow of the ship
         * @return returns the newly created ship
         */
        fun MakeShip(shipType: ShipType, orientation: Orientation, bowCell : Cell) : Ship =
            when(shipType) {
                ShipType.Battleship -> Battleship(orientation, bowCell)
                ShipType.Carrier -> Carrier(orientation, bowCell)
                ShipType.Cruiser -> Cruiser(orientation, bowCell)
                ShipType.Destroyer -> Destroyer(orientation, bowCell)
                ShipType.Submarine -> Submarine(orientation, bowCell)
            }
    }

    /**
     * Attacks this ship.
     * @param cell The cell which is being attacked.
     * @return true if ship was hit, and false otherwise
     */
    fun attack(cell : Cell) : Boolean {
        val pt = shipCells.find { pt -> pt == cell }
        pt?.attacked = true
        return pt != null
    }

    /**
     * Assess if this ship has sunk.
     * @return true if ship has sunk, and false otherwise
     */
    fun isSunk() : Boolean {
        return shipCells.all { pt -> pt.attacked }
    }

    /**
     * Assess if this ship overlaps with another ship.
     * @return true if the two ships overlap, and false otherwise
     */
    fun isOverlap(ship : Ship) : Boolean {
        return shipCells.intersect(ship.shipCells).isEmpty().not()
    }

    /**
     * Returns a copy of all cells occupied by this ship.
     */
    fun getCells() : List<Cell> {
        return shipCells.toList()
    }
}