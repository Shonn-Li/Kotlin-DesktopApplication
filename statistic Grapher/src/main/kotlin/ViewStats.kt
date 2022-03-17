import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.lang.Math.round
import java.util.Collections.max

class ViewStats(
    private val model: Model
) : HBox(), IView {
    private var NumberTitle = Label("Number")
    private var MinimumTitle = Label("Minimum")
    private var MaximumTitle = Label("Maximum")
    private var AverageTitle = Label("Average")
    private var SumTitle = Label("Sum")
    private var NumberValue = Label()
    private var MinimumValue = Label()
    private var MaximumValue = Label()
    private var AverageValue = Label()
    private var SumValue = Label()
    private val StatsTitles = VBox(NumberTitle, MinimumTitle, MaximumTitle, AverageTitle, SumTitle)
    private val StatsValues = VBox(NumberValue, MinimumValue, MaximumValue, AverageValue, SumValue)
    override fun updateView() {
        NumberValue.text = model.currentDataSet.data.size.toString()
        MaximumValue.text = model.currentDataSet.data.maxOrNull().toString()
        MinimumValue.text = model.currentDataSet.data.minOrNull().toString()
        AverageValue.text = "%.1f".format((model.currentDataSet.data.average()))
        SumValue.text = model.currentDataSet.data.sum().toString()
    }
    init {
        updateView()
//        Layout Formatting
        StatsTitles.setPadding(Insets(10.0, 5.0, 10.0, 10.0))
        StatsValues.setPadding(Insets(10.0, 30.0, 10.0, 5.0))
        StatsTitles.spacing = 10.0
        StatsValues.spacing = 10.0
        this.maxWidth(125.0)
        this.minWidth(125.0)

        children.add(StatsTitles)
        children.add(StatsValues)
    }
}