package cs349.a3battleship

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Orientation
import cs349.a3battleship.model.Player
import cs349.a3battleship.model.ships.ShipType
import cs349.a3battleship.ui.*
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage


class Battleship : Application() {
    override fun start(stage: Stage) {
        var game = Game(10, true)
        var computer = AI(game)
        game.startGame()
//        game.placeShip(Player.Human, ShipType.Battleship, Orientation.HORIZONTAL, Cell(3,3))
        // var player = ...
        // View setup
        val viewleft = ViewBoard("Player", game)
        val viewRight = ViewBoard("AI", game)
        val playerTitle = ViewTitle("My Formation", game)
        val opponentTitle = ViewTitle("Opponent's Formation", game)
        val infoTitle = ViewMessage("My Fleet", game)
        val menu = ViewMenu(game)
        game.addView(viewleft)
        game.addView(viewRight)
        game.addView(playerTitle)
        game.addView(opponentTitle)
        game.addView(infoTitle)
        game.addView(menu)
        val frame = BorderPane()

        frame.left = viewleft
        frame.right = viewRight
        frame.center = menu
        frame.top = HBox(playerTitle, infoTitle, opponentTitle)
        val fleets = ViewFleet(game, frame)
        game.addView(fleets)
        val scene = Scene(fleets, 875.0, 375.0)
        stage.scene = scene
        stage.title = "A3 Battleship (s854li)"
        // stage.scene = ...
        stage.show()
    }
}