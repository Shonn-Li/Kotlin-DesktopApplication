import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javax.xml.crypto.Data

class ViewToolBar(

    private val model: Model
) : ToolBar(), IView {
    private val spacer = HBox()
    private val datasetTitle = Label("Dataset:")
    private val DataList = ComboBox<String>()
    private val seperator = Separator()
    private val newButton = Button("New")
    private val spinner = Spinner<Int>(0, 20, model.newDataSetNum)

    init {
        // event handling
        newButton.setOnMouseClicked {
            model.addDataSet()
        }
        spinner.valueProperty().addListener { obj, old, new ->
            model.newDataSetNum = new
//            println("newDataSetNum adjusted")
        }
        DataList.setOnAction {
            if (model.addComboBox) {
                model.addComboBox = false
            } else {
                var item: String = DataList.selectionModel.selectedItem.toString()
//                println(item)
                model.changeDataSet(item)
            }
        }
    }

    // When notified by the model that things have changed,
    // update to display the new value
    override fun updateView() {
        println("update View Called")
        if (model.addComboBox) {
            add(model.currentDataSet)
        }

    }
    init {
        // Widget formatting Settings
        DataList.items.addAll("Increasing", "Large", "Middle",  "Single", "Range", "Percentage")
        DataList.prefWidth = 110.0
        DataList.selectionModel.selectFirst()
        seperator.orientation = Orientation.VERTICAL
        seperator.valignment = VPos.CENTER
        seperator.padding = (Insets(0.0, 10.0, 0.0, 10.0))
        newButton.prefWidth = 80.0
        newButton.maxWidth = 80.0
        spinner.prefWidth = 60.0
        spacer.alignment = Pos.CENTER_LEFT
        spacer.spacing = 5.0
        spacer.setPadding(Insets(5.0, 5.0, 5.0, 5.0))
        spacer.children.add(datasetTitle)
        spacer.children.add(DataList)
        spacer.children.add(seperator)
        spacer.children.add(newButton)
        spacer.children.add(spinner)
        this.items.addAll(spacer)
    }
    fun add(dataSet:DataSet) {
        DataList.items.add(dataSet.dataSetTitle)
        DataList.selectionModel.select(dataSet.dataSetTitle)
    }
}