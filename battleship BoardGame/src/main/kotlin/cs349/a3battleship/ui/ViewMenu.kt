package cs349.a3battleship.ui
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Player
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage

class ViewMenu(private val model: Game)  : Pane(), IView {
    var startGame : Button  = Button()
    var exitGame : Button  = Button()
    override fun updateView() {
        if (model.settingUp) {
            startGame.isDisable = model.getShipsPlacedCount(Player.Human) != 5
        }
    }
    init {
        startGame.prefWidth = 125.0
        startGame.text = "Start Game"
        startGame.isDisable = true
        startGame.relocate(25.0, 275.0)
        exitGame.prefWidth = 125.0
        exitGame.text = "Exit Game"
        exitGame.relocate(25.0, 300.0)
        this.children.addAll(startGame, exitGame)
        this.setPrefSize(175.0, 350.0)
        this.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
        startGame.setOnAction {
            model.settingUp = false
            startGame.isDisable = true
            println("startGame button updated")
            model.startGame()
        }
        exitGame.setOnAction {
            Platform.exit()
        }
    }
}
