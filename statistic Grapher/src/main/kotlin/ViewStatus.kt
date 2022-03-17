import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ToolBar
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.HBox
import javafx.scene.paint.Color

class ViewStatus(
    private val model: Model
) : HBox(), IView {
    private val info = Label(model.DataSets.size.toString()+ " Datasets")
    override fun updateView() {
        info.text = model.DataSets.size.toString() + " Datasets"
    }
    init {
        // layout
        this.children.add(info)
        this.padding = (Insets(10.0, 10.0, 10.0, 10.0))
        this.alignment = Pos.CENTER_LEFT
        this.background = Background(BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY))
    }
}