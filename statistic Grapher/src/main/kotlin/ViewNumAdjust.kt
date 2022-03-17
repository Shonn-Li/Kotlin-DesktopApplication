import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.text.TextAlignment

class ViewNumAdjust(
    private val model: Model
) : javafx.scene.control.ScrollPane(), IView {
    private val list = GridPane()
    override fun updateView() {
        // Numdajust list set up
        list.children.clear()
        model.dataTable.forEachIndexed { i, it ->
            var label = Label((i + 1).toString() + ":")
            label.alignment = Pos.BASELINE_LEFT

            list.add(label, 0, i)
            list.add(it.spinner, 1, i)
        }
    }
    init {
        updateView()
        // layout formatting
        list.alignment = Pos.BASELINE_LEFT
        list.maxWidth(125.0)
        list.minWidth(125.0)
        list.hgap = 5.0
        list.setPadding(Insets(10.0, 30.0, 10.0, 20.0))
        content = list
        this.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        this.width = 150.0
        this.minWidth(150.0)
        this.maxWidth(150.0)
    }
}