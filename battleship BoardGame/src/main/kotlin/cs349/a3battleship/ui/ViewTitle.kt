package cs349.a3battleship.ui

import cs349.a3battleship.model.Game
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment

class ViewTitle(private val name:String, private val model: Game) : Label(), IView {
    private enum class GameState {
        Init,
        SetupHuman,
        SetupAI,
        FireHuman,
        FireAI,
        WonHuman,
        WonAI,
    }
    override fun updateView() {
//        if ((updatable) && (GameState.WonHuman == model.gameState)) {
//            this.text = "You won!"
//        } else if ((updatable) && (GameState.WonAi == model.gameState))
//            this.text = "Opponent won!"
    }
    init {
        this.text = name
        this.font = Font.font(null, FontWeight.findByWeight(100), FontPosture.REGULAR, 16.0)
        this.alignment = Pos.CENTER
        this.prefHeight = 25.0
        this.prefWidth = 350.0
        this.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
    }
}