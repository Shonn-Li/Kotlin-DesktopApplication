import javafx.scene.control.Spinner

// a spinner box class
class DataAdjuster(initialVal:Int, model: Model, index:Int) {
    val index = index
    val value = initialVal
    val spinner = Spinner<Int>(0, 100, value)
    init {
        spinner.prefWidth = 75.0
        spinner.valueProperty().addListener { obj, old, new ->
            model.currentDataSet.data[index] = new
            model.graphRefresh()
        }
    }
}