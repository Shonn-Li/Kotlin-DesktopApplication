package cs349.a3battleship.ui

import javafx.scene.control.Label

import cs349.a3battleship.model.Game
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.TextArea
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment

class ViewMessage(private val name:String, private val model: Game) : Label(), IView {
    override fun updateView() {
        if ((model.gameEnd) && (model.playerWon)) {
            text = "You won!"
        } else if (model.gameEnd) {
            text = "You were defeated"
        }
    }
    init {
        this.text = name
        this.font = Font.font(null, FontWeight.NORMAL, 16.0)
        this.alignment = Pos.CENTER
        this.prefHeight = 25.0
        this.prefWidth = 175.0
        this.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
    }
}