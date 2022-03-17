import javafx.application.Application
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage

// MVC with coupled View and Controller (a more typical method than MVC1)
// A simple MVC example inspired by Joseph Mack, http://www.austintek.com/mvc/
// This version uses MVC: two views coordinated with the observer pattern, but no separate controller.
class Grapher : Application() {

    override fun start(stage: Stage) {
        // window name
        stage.title = "A2 Grapher (s854li)"
        stage.isResizable = true
        stage.minWidth = 600.00
        stage.minHeight = 400.00

        // create and initialize the Model to hold our counter
        val model = Model()

        // create each view, and tell them about the model
        // the views will register themselves with the model
        val viewTopTop = ViewToolBar(model)
        val viewTop = ViewDataAdjust(model)
        val viewCenter = ViewGraph(model)
        val viewLeft = ViewNumAdjust(model)
        val viewRight = ViewStats(model)
        val viewBottom = ViewStatus(model)
        val ViewTopTogether = VBox(viewTopTop, viewTop)
        val frame = BorderPane()

        // add View to model to activate observer
        model.addView(viewTopTop)
        model.addView(viewTop)
        model.addView(viewCenter)
        model.addView(viewLeft)
        model.addView(viewRight)
        model.addView(viewBottom)

        // layout formating
        frame.top = ViewTopTogether
        frame.center = viewCenter
        frame.right = viewRight
        frame.left = viewLeft
        frame.bottom = viewBottom

        // Add grid to a scene (and the scene to the stage)
        val scene = Scene(frame, 800.0, 600.0)

        stage.scene = scene
        // width and height listener for grqph
        stage.widthProperty().addListener { obs, oldVal, newVal ->
            model.width = newVal.toDouble()
            model.graphRefresh()
        }

        stage.heightProperty().addListener { obs, oldVal, newVal ->
            model.height = newVal.toDouble()
            model.graphRefresh()
        }
        stage.show()
    }
}