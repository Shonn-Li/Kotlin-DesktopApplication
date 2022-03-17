package cs349.a3battleship.ui
import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.CellState
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Player
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.ListCell
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import kotlin.math.floor

class ViewBoard(private val name: String, private val model: Game) : Canvas(350.0, 350.0), IView {

    val gc = this.graphicsContext2D
    override fun updateView() {
        gc.fill = Color.LIGHTBLUE
        gc.fillRect(25.0,25.0,300.0,300.0)
        if ((!model.settingUp) && !((model.gameEnd) && (name == "Player")))  {
            var cellsState:Array<Array<CellState>>
            if (name == "AI") {
                cellsState = model.getBoard(Player.AI)
            } else {
                cellsState = model.getBoard(Player.Human)
            }
            cellsState.forEachIndexed { j, it ->
                it.forEachIndexed { i, cellState ->
                    when (cellState) {
                        CellState.Ocean -> gc.fill = Color.LIGHTBLUE
                        CellState.Attacked -> gc.fill = Color.LIGHTGRAY
                        CellState.ShipHit -> gc.fill = Color.ORANGE
                        CellState.ShipSunk -> gc.fill = Color.GRAY
                        else -> gc.fill = Color.LIGHTBLUE
                    }
                    gc.fillRect(25.0 + i * 30, 25.0 + j * 30.0, 30.0, 30.0)
                }
            }
        } else if ((model.gameEnd) && (name == "Player")) {
            var cellsState:Array<Array<CellState>> = model.getBoard(Player.Human)
            cellsState.forEachIndexed { j, it ->
                it.forEachIndexed { i, cellState ->
                    when (cellState) {
                        CellState.ShipSunk -> gc.fill = Color.GRAY
                        else -> gc.fill = Color.LIGHTBLUE
                    }
                    gc.fillRect(25.0 + i * 30, 25.0 + j * 30.0, 30.0, 30.0)
                }
            }
        }
        grid(25.0, 25.0, 300.0, 30.0, gc)
    }
    init {
        val gc = this.graphicsContext2D
        gc.fill = Color.WHITE
        gc.fillRect(0.0,0.0,350.0,350.0)
        gc.fill = Color.LIGHTBLUE
        gc.fillRect( 25.0, 25.0, 300.0, 300.0)
        grid(25.0, 25.0, 300.0, 30.0, gc)
        gc.fill = Color.BLACK
        val nums : List<String> = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val letters : List<String> = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")
        gc.textAlign = TextAlignment.CENTER;
        nums.forEachIndexed { i, it ->
            gc.fillText(it, 40+i*30.0, 15.0)
        }
        nums.forEachIndexed { i, it ->
            gc.fillText(it, 40+i*30.0, 345.0)
        }
        letters.forEachIndexed { i, it ->
            gc.fillText(it, 10.0, 43.0+i*30.0)
        }
        letters.forEachIndexed { i, it ->
            gc.fillText(it, 340.0, 43.0+i*30.0)
        }
        this.onMouseClicked = EventHandler {
            if ((name == "AI") && (!model.settingUp) && (!((it.sceneX - 550 <= 0) || (it.sceneX - 550 >= 300) ||
                    (it.sceneY - 50 <= 0) || (it.sceneY - 50 >= 300)))) {

//                println(it.sceneX - 550)
//                println(it.sceneY - 50)
                print("attacking cell: (")
                print(floor((it.sceneX - 550)/30).toInt())
                print(", ")
                print(floor((it.sceneY - 50)/30).toInt())
                println(")")
                model.attackCell(Cell(floor((it.sceneX - 550)/30).toInt(),floor((it.sceneY - 50)/30).toInt()))
            }
        }
    }
    fun grid(x: Double, y: Double, wh: Double, s: Double, gc: GraphicsContext) {
        gc.lineWidth = 1.0
        gc.stroke = Color.BLACK

        var i = 0.0
        while (i <= wh) {
            gc.strokeLine(x + i, y, x + i, y + wh)
            gc.strokeLine(x, y + i, x + wh, y + i )
            i += s
        }
    }
}
