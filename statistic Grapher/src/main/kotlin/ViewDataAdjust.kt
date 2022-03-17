import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import javafx.scene.control.*

class ViewDataAdjust(
    private val model: Model
) : HBox(), IView {
    // Values for Title view
    private val title = Label("Title:")
    private var titleInfo = TextField(model.GraphTitle)
    private val Xtitle = Label("X-Axis:")
    private var XtitleInfo = TextField(model.GraphXTitle)
    private val Ytitle = Label("Y-Axis:")
    private var YtitleInfo = TextField(model.GraphYTitle)
    private var notRealUpdate = false
    override fun updateView() {
        if (notRealUpdate) {
            notRealUpdate = false
        } else {
            titleInfo.text = model.currentDataSet.title
            XtitleInfo.text = model.currentDataSet.xAxis
            YtitleInfo.text = model.currentDataSet.yAxis
        }
    }

    init {
        // formatting layout
        this.setPadding(Insets(10.0, 10.0, 10.0, 10.0))
        this.spacing = 10.0
        this.alignment = Pos.CENTER_LEFT
        children.add(title)
        children.add(titleInfo)
        children.add(Xtitle)
        children.add(XtitleInfo)
        children.add(Ytitle)
        children.add(YtitleInfo)
        // event handling for textfield text change
        titleInfo.setOnKeyTyped {
            notRealUpdate = true
            model.changeName(titleInfo.text, XtitleInfo.text, YtitleInfo.text)
        }
        XtitleInfo.setOnKeyTyped {
            notRealUpdate = true
            model.changeName(titleInfo.text, XtitleInfo.text, YtitleInfo.text)
        }
        YtitleInfo.setOnKeyTyped {
            notRealUpdate = true
            model.changeName(titleInfo.text, XtitleInfo.text, YtitleInfo.text)
        }
    }
}