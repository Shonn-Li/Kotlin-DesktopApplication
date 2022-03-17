package cs349.a3battleship.ui

import cs349.a3battleship.model.*
import cs349.a3battleship.model.ships.ShipType
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.transform.Rotate
import kotlin.math.floor

// Boat class, inheriting from Rectangle with addtional properties for tracking
class Boat(initX: Double, initY: Double, width: Double, height: Double, boatSize: Int, shipType: ShipType) :
    Rectangle(initX, initY, width, height) {
    var initX = initX
    var initY = initY
    var boatSize = boatSize
    var shipType = shipType
    var alignX: Double = (30 - width) / 2
    var alignY: Double = (30 * boatSize - height) / 2
    var horizontal: Boolean = false
    var placedShip: Boolean = false
    var cellx:Int = 0
    var celly:Int = 0
//    init {
//        println(alignX)
//        println(alignY)
//    }
}

class ViewFleet(
    private val model: Game,
    private val background: Node
) : Pane(), IView {
    // mouse movement variable set up
    private lateinit var selectedNode: Node
    private var holding: Boolean = false
    var offsetX: Double = 0.0
    var offsetY: Double = 0.0
    var snapPoint: Double = 0.0
    private lateinit var listOfShip: MutableList<Boat>
    //update View setup
    var clickUnavailable = false

    override fun updateView() {
//        print("passed updateView ViewFleet settingUp:")
        println(!model.settingUp)
        // when clicking should be unavailable
        if (!model.settingUp) {
            clickUnavailable = true
        }
        // when game ends moving all ships except sunken ones
        if (model.gameEnd) {
            val sunkenPoints = model.getBoard(Player.Human)

            listOfShip.forEach {
                if (sunkenPoints[it.celly][it.cellx] == CellState.ShipSunk) {
                    return@forEach
                }
                with (it) {
                    if (horizontal) {
                        horizontal = !horizontal
                        var tempWidth = width
                        width = height
                        height = tempWidth
                    }
                    this.translateX = 0.0
                    this.translateY = 0.0
                }
            }
        }
    }

    init {
        this.children.add(background)
        var x: Double = 350.0 + 15 + 5
        var y: Double = 25.0
        val destroyer = Boat(x, y, 17.0, 45.0, 2, ShipType.Destroyer)
        with(destroyer) {
            stroke = Color.BLACK
            strokeWidth = 1.0
            fill = Color.TRANSPARENT
            itemSelected(this)
        }
        listOfShip = mutableListOf(destroyer)
        this.children.add(destroyer)
        x = 350.0 + 15 + 35
        val submarine = Boat(x, y, 15.0, 75.0, 3, ShipType.Submarine)
        with(submarine) {
            stroke = Color.BLACK
            strokeWidth = 1.0
            fill = Color.TRANSPARENT
            itemSelected(this)
        }
        listOfShip.add(submarine)
        this.children.add(submarine)
        x = 350.0 + 15 + 65
        val cruiser = Boat(x, 25.0, 25.0, 75.0, 3, ShipType.Cruiser)
        with(cruiser) {
            stroke = Color.BLACK
            strokeWidth = 1.0
            fill = Color.TRANSPARENT
            itemSelected(this)
        }
        listOfShip.add(cruiser)
        this.children.add(cruiser)
        x = 350.0 + 15 + 95
        val battleship = Boat(x, y, 25.0, 105.0, 4, ShipType.Battleship)
        with(battleship) {
            stroke = Color.BLACK
            strokeWidth = 1.0
            fill = Color.TRANSPARENT
            itemSelected(this)
        }
        listOfShip.add(battleship)
        this.children.add(battleship)

        x = 350.0 + 15 + 125
        val carrier = Boat(x, y, 25.0, 135.0, 5, ShipType.Carrier)
        with(carrier) {
            stroke = Color.BLACK
            strokeWidth = 1.0
            fill = Color.TRANSPARENT
            itemSelected(this)
        }
        listOfShip.add(carrier)
        this.children.add(carrier)

        //events
        this.onMouseMoved = EventHandler {
            if (holding) {
                selectedNode.translateX = it.sceneX + offsetX
                selectedNode.translateY = it.sceneY + offsetY
                it.consume()
            }
        }
    }

    // verticalCellCheck checks that the vertical ship is snappable and snaps it
    private fun verticalCellCheck(x: Double, y: Double, node: Node): Boolean {
//        println("came to cell check")
        with(node as Boat) {
            var ycell = 0
            var xcell = 0
            var xloc = x - 25
            var yloc = y - 50
//            print("xloc: ")
//            println(xloc)
//            print("yloc: ")
//            println(yloc)
//            print("x remainder: ")
//            println(xloc % 30)

            // check for horizontal snap
            if ((xloc) % 30 <= 15) {
//                println(alignX)
//                println(xloc / 30)
//                println(((xloc / 30) * 30 + 25))
                translateX = (floor(xloc / 30) * 30 + 25 + alignX) - this.x
                xcell = floor((xloc / 30)).toInt()
//                println("translated x in cell check case 1")
            } else if ((xloc) % 30 > 15) {

//                println(((xloc / 30) * 30 + 25 + alignX))

                translateX = floor(xloc / 30 + 1) * 30 + 25 + alignX - this.x
                xcell = floor((xloc / 30)).toInt() + 1
//                println("translated x in cell check case 2")
            } else {
                return false
            }

            // check for vertical snap
//            print("y remainder: ")
//            println(yloc % 30)
            if ((yloc % 30 <= 15) && (yloc / 30 + boatSize - 1 <= 10)) {
                translateY = floor(yloc / 30) * 30 + 50 + alignY - this.y
                ycell = floor((yloc / 30)).toInt()
//                println("translated y in cell check 1")
            } else if ((yloc % 30 > 15) && (yloc / 30 + 1 + boatSize <= 10)) {
                translateY = floor((yloc / 30) + 1) * 30 + 50 + alignY - this.y
                ycell = floor((yloc / 30)).toInt() + 1
//                println("translated y in cell check 2")
            } else {
                return false
            }
            if (checkOverlap(false, node)) {
                return false
            }
            node.placedShip = true
            node.cellx = xcell
            node.celly = ycell
            println(xcell)
            println(ycell)
            model.placeShip(Player.Human, shipType, Orientation.VERTICAL, Cell(xcell, ycell, false))
        }

        return true
    }

    // horizontalCellCheck checks that the horizontal ship is snappable and snaps it
    private fun horizontalCellCheck(x: Double, y: Double, node: Node): Boolean {
//        println("came to cell check horizontal")
        with(node as Boat) {
            var ycell = 0
            var xcell = 0
            var xloc = x - 25
            var yloc = y - 50
//            print("xloc: ")
//            println(xloc)
//            print("yloc: ")
//            println(yloc)
//            print("x remainder: ")
//            println(xloc % 30)
//            println(this.x)
//            println(this.y)
            // check for horizontal snap
            if ((xloc % 30 <= 15) && (xloc / 30 + boatSize - 1 <= 10)) {
//                println(floor((xloc / 30)) * 30 + 25 + alignY)
                translateX = floor((xloc / 30)) * 30 + 25 + alignY - this.x
                xcell = floor((xloc / 30)).toInt()
//                println("translated x in cell check 1")
            } else if ((xloc % 30 > 15) && (xloc / 30 + 1 + boatSize <= 10)) {
                println(floor(xloc / 30 + 1) * 30 + 25 + alignY)
                translateX = floor(xloc / 30 + 1) * 30 + 25 + alignY - this.x
                xcell = floor((xloc / 30)).toInt() + 1
//                println("translated x in cell check 2")
            } else {
                return false
            }
//            print("y remainder: ")

            // check for vertical snap
            println(yloc % 30)
            if ((yloc) % 30 <= 15) {
                println(floor(yloc / 30) * 30 + 50 + alignX)
                translateY = floor(yloc / 30) * 30 + 50 + alignX - this.y
//                println("translated x in cell check case 1")
                ycell = floor((yloc / 30)).toInt()
            } else if ((yloc) % 30 > 15) {
                println(floor(yloc / 30 + 1) * 30 + 50 + alignX)
                translateY = (floor(yloc / 30 + 1) * 30 + 50 + alignX) - this.y
                ycell = floor((yloc / 30)).toInt() + 1
            } else {
                return false
            }
            if (checkOverlap(true, node)) {
//                println("didn't passed Overlap")
                return false
            }
//            println("passed Overlap")
            node.placedShip = true
            node.cellx = xcell
            node.celly = ycell
            println(xcell)
            println(ycell)
            model.placeShip(Player.Human, shipType, Orientation.HORIZONTAL, Cell(xcell, ycell, true))
        }
        return true
    }

    // checkOverlap make sure nothing overlaps
    private fun checkOverlap(horizontal: Boolean, node: Node): Boolean {
        // check overlap for each boat except it self
        listOfShip.forEach {
            if (it == node) {
                return@forEach
            }
            with(node as Boat) {
                var leftBoatX = it.translateX + it.x
                var rightBoatX = it.translateX + it.width + it.x
                var topBoatY = it.translateY + it.y
                var bottomBoatY = it.translateY + it.height + it.y
                var leftNodeX = translateX + x
                var rightNodeX = translateX + x + width
                var topNodeY = translateY + y
                var bottomNodeY = translateY + y + height
                // a lot of debugging info
//                println(it.shipType)
//                print("boat location leftBoatX: ")
//                println(leftBoatX)
//                print("node location leftNodeX: ")
//                println(leftNodeX)
//                print("node location rightNodeX end: ")
//                println(rightNodeX)
//                print("boat location rightBoatX end: ")
//                println(rightBoatX)
//                print("boat location topBoatY: ")
//                println(topBoatY)
//                print("node location topNodeY: ")
//                println(topNodeY)
//                print("node location bottomNodeY end: ")
//                println(bottomNodeY)
//                print("boat location bottomBoatY end: ")
//                println(bottomBoatY)
//                print("passed : ")
//                print(leftBoatX >= rightNodeX )
//                print(" ")
//                print(leftNodeX >= rightBoatX)
//                print(" ")
//                print(topBoatY >= bottomNodeY)
//                print(" ")
//                println(topNodeY >= bottomBoatY)
                if (leftBoatX >= rightNodeX || leftNodeX >= rightBoatX) {
//                    println("passed return for each")
                    return@forEach
                }

                // If one rectangle is above other
                if (topBoatY >= bottomNodeY || topNodeY >= bottomBoatY) {
                    return@forEach
                }
//                println("passed return true")
                return true
            }
        }
//        println("returned ")
        return false
    }

    // changeHorzontal changes the ship to a horizontal ship
    private fun changeHorzontal(node: Node, it: MouseEvent) {
        with(node as Boat) {
            horizontal = !horizontal
            // get mouse distance
            var previousDistFromMouseToXEdge = it.sceneX - (translateX + x)
            var previousDistFromMouseToYEdge = it.sceneY - (translateY + y)
            // rotate
            var tempWidth = width
            width = height
            height = tempWidth
            // fixed mouse poiting
            translateX = it.sceneX - previousDistFromMouseToYEdge - x
            translateY = it.sceneY - previousDistFromMouseToXEdge - y
            offsetX = node.translateX - it.sceneX
            offsetY = node.translateY - it.sceneY
        }
    }

    // itemSelected handles ship selection
    private fun itemSelected(node: Node) {
        node.onMouseClicked = EventHandler {
            // when click is turned off
            if ((clickUnavailable) || ((holding) && (selectedNode != node))){
                println("Came to clickUnavailable")
            // when right click
            } else if (it.button == MouseButton.SECONDARY) {
                changeHorzontal(node, it)
            // when left click
            } else {
                // when left click while dragging object
                if (holding) {
                    with(node as Boat) {
                        // move ship to designated mouse pointed place
                        var x: Double = node.x + this.translateX
                        var y: Double = node.y + this.translateY
                        if ((x <= 325.0 + snapPoint) && (x >= 25 - snapPoint) &&
                            (y <= 350 + snapPoint) && (y >= 50 - snapPoint)
                            && (((!horizontal) && (verticalCellCheck(x, y, node)))
                                    || ((horizontal) && (horizontalCellCheck(x, y, node))))
                        ) {
                        } else {
                            // put ship back to original spot
                            if (horizontal) {
                                changeHorzontal(node, it)
                            }
                            this.translateX = 0.0
                            this.translateY = 0.0
                            it.consume()
                        }
                    }
                // when ship is clicked for the first time
                } else {

                    with (node as Boat) {
                        if (placedShip) {
                            if (placedShip) {
                                model.removeShip(Player.Human, Cell(cellx, celly))
                            }
                            placedShip = !placedShip
                        }
                    }
                    selectedNode = node
                    offsetX = node.translateX - it.sceneX
                    offsetY = node.translateY - it.sceneY
                    it.consume()
                }
                holding = !holding
            }
        }
    }

}