import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment

class ViewGraph(
private val model: Model
) : Canvas(model.width - 275.0, model.height - 130.0), IView {
    override fun updateView() {
        // values setup
        var width = model.width - 275.0
        var height = model.height - 155.0
        this.width = width
        this.height = height
        val gc = this.graphicsContext2D
        // background
        gc.fill = Color.WHITE
        gc.fillRect( 0.0, 0.0, width, height)
        // black text and xy line
        gc.fill = Color.BLACK
        gc.fillText(model.GraphTitle, (width / 2), 50 / 2.0)
        gc.fillText(model.GraphXTitle, (width / 2), height - 50 + 50/2)
        gc.fillText("0", 40.0, height - 50 + 5)
        var MaximumValue = model.currentDataSet.data.maxOrNull()
        gc.fillText(MaximumValue.toString(), 35.0, 55.0)
        gc.lineWidth = 0.5
        gc.strokeLine(50.0,50.0, 50.0, height - 50)
        gc.strokeLine(50.0,height - 50, width - 50, height - 50)
//      vertical text
        gc.save()
        gc.translate(0.0, height);
        gc.rotate(-90.0);
        gc.fillText(model.GraphYTitle, (height / 2),50 / 2.0)
        gc.fillText("random", 0.0, 0.0)
        gc.restore()
        // bar display
        var n = model.currentDataSet.data.size
        var TotalWidth = width - 100.0
        var TotalHeight = height - 100.0
        var RectWidth = (TotalWidth - (n + 1) * 5.0)/n
        val max = if (MaximumValue != null) MaximumValue else -1
        print(max)
        print(MaximumValue)
        var HeightRatio = TotalHeight/(max)
        var colors = Color.hsb(1.0, 1.0, 1.0)
        var ratio:Double = 360.0/n
        model.currentDataSet.data.forEachIndexed{ i, it ->
            gc.fill = Color.hsb(ratio*(i+1), 1.0, 1.0)
            gc.fillRect(50 + (i + 1) * 5 + i*RectWidth, height - 50.0 - HeightRatio * it, RectWidth, HeightRatio * it)
            gc.fill = Color.BLACK
            gc.strokeRect(50 + (i + 1) * 5 + i*RectWidth, height - 50.0 - HeightRatio * it, RectWidth, HeightRatio * it)
        }

        // extra two lines to accompany xy line
        gc.fill = Color.LIGHTGREY
        gc.lineWidth = 0.1
        gc.strokeLine(50.0,50.0, width - 50, 50.0)
        gc.strokeLine(width - 50,50.0, width - 50, height - 50)
        gc.textAlign = TextAlignment.CENTER;
    }
    init {
        updateView()
    }
}