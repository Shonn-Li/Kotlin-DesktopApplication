package cs349.a3battleship

import cs349.a3battleship.model.*
import kotlin.random.Random

class AI(var game : Game) {

    private val myself = Player.AI

    init {
        game.onPlayerSetupBegin.add { placeShips(it) }
        game.onPlayerAttackBegin.add { attackCell(it) }
    }

    private fun placeShips(player : Player) {
        if (player == myself) {
            game.getShipsToPlace().forEach {
                do {
                    var bowCell = Cell(
                        (Random.nextDouble() * game.dimension).toInt(),
                        (Random.nextDouble() * game.dimension).toInt()
                    )
                    var orientation = Orientation.values()[(Random.nextDouble() * Orientation.values().size).toInt()]
                } while (game.placeShip(Player.AI, it, orientation, bowCell) == null)
            }
        game.startGame()
        }
    }

    private fun attackCell(player : Player) {
        if (player == myself) {
            var attackCell: Cell
            do {
                attackCell = Cell(
                    (Random.nextDouble() * game.dimension).toInt(),
                    (Random.nextDouble() * game.dimension).toInt()
                )
            } while (game.getAttackedCells(Player.Human).contains(attackCell))
            game.attackCell(attackCell)
        }
    }
}