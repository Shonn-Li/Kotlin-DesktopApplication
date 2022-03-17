import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.*
import javafx.stage.Stage
import javafx.scene.shape.Rectangle
import javafx.stage.Modality
import javafx.stage.Popup
import javafx.stage.StageStyle
import javax.imageio.stream.MemoryCacheImageOutputStream
import kotlin.random.Random
val SLIT:String = "Lorem ipsum dolor sit amet consectetur adipiscing elit In interdum massa non eleifend porttitor Donec bibendum augue eu consequat convallis Morbi sit amet nisi purus Donec a tristique sem Quisque volutpat ex nec nibh volutpat molestie In mollis lectus vel ornare faucibus Mauris congue lacus non est semper faucibus Sed et ipsum nunc Orci varius natoque penatibus et magnis dis parturient montes nascetur ridiculus mus iaculis efficitur consequat Sed eu nisi in eros tincidunt aliquam Fusce porta dolor fringilla dignissim aliquet Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas Interdum et malesuada fames ac ante ipsum primis in faucibus Donec maximus viverra nibh ut convallis arcu lacinia nec Sed vulputate volutpat sapien eget egestas Nulla dolor ligula pharetra non ipsum dapibus ultricies mattis sem Proin accumsan sapien vel dictum sagittis Praesent ex eros tempor ac lacinia nec rhoncus nec ex Donec et velit convallis ultrices enim quis pulvinar sem In pulvinar tempor fermentum In vehicula massa ut tellus ullamcorper ut ultrices libero dictum Donec a fringilla turpis Vivamus vitae aliquam ex Nam sit amet mi commodo dui consequat consequat eu ut nunc Nunc lacus elit pellentesque ac porta quis tincidunt eget ipsum Nullam malesuada lectus non nibh semper cursus Cras eu molestie felis Duis ut felis ligula Cras quis arcu nec erat malesuada maximus Nunc id quam eu purus dictum tincidunt a non eros Sed blandit ex non consectetur cursus velit mauris hendrerit velit a ultrices felis nisl id augue In euismod purus tellus Pellentesque in ligula venenatis venenatis libero id pellentesque ipsum Cras molestie nulla sit amet ligula porttitor lacinia et efficitur elit Quisque a tempor elit In tellus turpis efficitur eget vestibulum id commodo ut nisi Sed ac arcu a diam egestas ornare et ac quam Nulla et cursus magna Vestibulum semper felis id massa egestas et pharetra magna eleifend Duis posuere ante a magna sodales cursus Integer venenatis tincidunt arcu sed posuere nibh scelerisque sit amet In felis tortor vehicula eget nibh sit amet porttitor tincidunt nisi Suspendisse consequat consequat lacus nec consequat metus cursus et Quisque dapibus rutrum enim id varius Nam dapibus ligula at ligula eleifend tempus Nullam eget nibh eu dolor tincidunt iaculis non sit amet sem Sed placerat ex mollis lacinia risus vitae tincidunt erat Sed id diam lacus Nunc vulputate sapien eu tempor dignissim risus sapien eleifend ex at lacinia est enim in diam Praesent varius egestas ipsum Integer nec dui tristique laoreet velit eget luctus nisl Etiam pharetra erat magna Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas Aliquam sed lectus vulputate tristique dolor elementum volutpat dui"
var SLITWords:List<String> = SLIT.split(" ")
val SLITLen:Int = SLITWords.size
class A1Basic : Application() {
    // Data values
    var numOfNotes:Int = 0
    var numOfNotesAdded:Int = 0
    var listOfNotes:MutableSet<Note> = mutableSetOf()
    var listOfDNotes:MutableSet<Note> = mutableSetOf()
    var Important:Boolean = false
    lateinit var CurrentNote:Note
    var PrevAction:String = "No action"

    // Widget values
    val NewButton = Button("Add")
    val RandomButton = Button("Random")
    val DeleteButton = Button("Delete")
    val ClearButton = Button("Clear")
    val ImportantButton = ToggleButton("!")
    val search = TextField()
    val stats = Label()

    // layout values
    val notelist = FlowPane()
    val border = BorderPane()
    val stack = StackPane(border)
    val toolbar = HBox(NewButton, RandomButton, DeleteButton, ClearButton, ImportantButton, search)
    val rootLO = ScrollPane()
    val status = ToolBar(stats)
    val scene = Scene(stack, 800.0, 600.0)

    override fun start(stage: Stage) {
        // default current note for tracking
        // Widget layout setup, make Button width and height
        NewButton.prefWidth = 100.00
        RandomButton.prefWidth = 100.00
        DeleteButton.prefWidth = 100.00
        ClearButton.prefWidth = 100.00
        ClearButton.prefWidth = 100.00
        search.prefHeight = 25.0
        search.prefWidth = 200.0
        // initialize delete, clear to be unclickable and search to be editable
        DeleteButton.isDisable = true
        ClearButton.isDisable = true
        search.isEditable = true

        // Events handling
        // New BUtton, brings up new button features
        NewButton.setOnAction {
            NewNoteWindow(stage)
        }
        // RandomButton, brings up RandomButton features
        RandomButton.setOnAction {
            val NumOfTitle:Int = (1..3).random()
            val NumOfSentence:Int = (2..5).random()
            val IsImportant:Int = (1..5).random()
            var RanTitle:String = SLITGenerator(1)
            for (i in NumOfTitle downTo 2) {
                RanTitle += " " + SLITGenerator(1)
            }
            var RanText:String = SLITGenerator((3..10).random()) + "."
            println(RanTitle)
            println(RanText)
            for (num in NumOfSentence downTo  3) {
                RanText += " " +  SLITGenerator(NumOfSentence) + "."
            }
            println(RanText)
            var newnote:Note = Note(stage, numOfNotesAdded, RanTitle, RanText)
            if (IsImportant == 1) {
                newnote.highlight = true
                newnote.highlightUpdate()
            }
            UpdateList(newnote)
        }
        // DeleteButton, brings up DeleteButton features
        DeleteButton.setOnAction {
            notelist.children.removeAll(CurrentNote.display)
            listOfDNotes.remove(CurrentNote)
            listOfNotes.remove(CurrentNote)
            PrevAction = "Deleted Note #" + CurrentNote.notenum
            numOfNotes -= 1
            stats.text = numOfNotes.toString() + "     " + PrevAction
            if (listOfNotes.size == 0) {
                ClearButton.isDisable = true
            }
            DeleteButton.isDisable = false
        }
        // ClearButton, brings up ClearButton features
        ClearButton.setOnAction {
            DeleteButton.isDisable = true
            for (note in listOfDNotes) {
                notelist.children.removeAll(note.display)
                listOfNotes.remove(note)
            }
            numOfNotes -= listOfDNotes.size
            PrevAction = "Cleared "+ listOfDNotes.size.toString() + " notes"
            stats.text = numOfNotes.toString() + "     " + PrevAction
            listOfDNotes.clear()
            if (numOfNotes == 0) {
                ClearButton.isDisable = true
            }
        }
        // ImportantButton, brings up ImportantButton features
        ImportantButton.setOnMouseClicked {
            println("triggered")
            Important = ImportantButton.isSelected
            println(Important)
            filter()
        }
        search.setOnKeyPressed {
            println("word typed in search")
            filter(search.text)
        }
        // layout setup, build the layout of the main page
        toolbar.setPadding(Insets(10.0, 10.0, 10.0, 10.0))
        toolbar.spacing = 10.0
        notelist.alignment = Pos.TOP_LEFT
        notelist.vgap = 10.0
        notelist.hgap = 10.0
        notelist.setPadding(Insets(10.0, 10.0, 10.0, 10.0))
        rootLO.content = notelist
        rootLO.isFitToHeight = true
        rootLO.isFitToWidth = true
        border.top = toolbar
        border.center = rootLO
        border.bottom = status
        stats.text = "0"

        // Stage setup
        stage.title = "A1 Notes (s854li)"
        stage.scene = scene
        stage.isResizable = true
        stage.minWidth = 400.00
        stage.minHeight = 400.00

        stage.show()
    }
    // Makes a new note windower that has a title, body, and save cancel button to save.
    // edit decides whether this window is a edit window or a new window, and string will
    // provide the name based on edit value.
    fun NewNoteWindow(stage: Stage, edit:Boolean = false, name:String = "") {
        // Widget definition
        val title = TextArea()
        val Body = TextArea()
        val SaveButton = Button("Save")
        val CancelButton = Button("Cancel")
        var newWindowName:String = "Add New Note"
        // Handling edit situation
        if (edit) {
            newWindowName = "Edit Note #" + name
            title.text = CurrentNote.title
            Body.text = CurrentNote.content
        }
        val noteLabel = Label(newWindowName)
        // Layout definitions
        val titleLabel = Label("Title")
        val bodyLabel = Label("Body")
        val ImportantCheckBox = CheckBox("Important")
        val options = HBox(CancelButton, SaveButton)
        val label = GridPane()
        val note = BorderPane()
        val rectangle = Rectangle()
        // Widget layout
        title.setMaxSize(400.0, 10.0)
        Body.prefWidth = 400.0
        Body.prefHeight = 150.0
        SaveButton.prefWidth = 100.00
        CancelButton.setOnAction { println("Open") }
        CancelButton.prefWidth = 100.00
        // Window layout
        noteLabel.setPadding(Insets(10.0, 10.0, 10.0, 10.0))
        titleLabel.prefWidth = 50.0
        bodyLabel.prefWidth = 50.0
        label.add(titleLabel, 0, 0)
        label.add(title, 1, 0)
        label.add(bodyLabel, 0, 1)
        label.add(Body, 1, 1)
        label.add(ImportantCheckBox, 1, 2)
        label.vgap = 10.0
        label.hgap = 10.0
        label.setPadding(Insets(10.0, 10.0, 10.0, 10.0))
        options.alignment = Pos.BOTTOM_RIGHT
        options.spacing = 10.0
        options.setPadding(Insets(10.0, 10.0, 10.0, 10.0))
        note.top = noteLabel
        note.bottom = options
        note.center = label
        rectangle.heightProperty().bind(stack.heightProperty())
        rectangle.widthProperty().bind(stack.widthProperty())
        rectangle.opacity = 0.5
        stack.children.addAll(rectangle)
        // new window setup
        val NotesScreen = Stage()
        NotesScreen.initModality(Modality.APPLICATION_MODAL)
        NotesScreen.initOwner(stage)
        NotesScreen.initStyle(StageStyle.UNDECORATED)
        NotesScreen.scene = Scene(note, 450.0, 300.0)
        NotesScreen.show()
        NotesScreen.setX(stage.x + ( stage.getWidth() - NotesScreen.getWidth()) / 2)
        NotesScreen.setY(stage.y + (stage.getHeight() - NotesScreen.getHeight()) / 2)
        // Events
        CancelButton.setOnAction {
            stack.children.removeAll(rectangle)
            NotesScreen.close()
        }

        SaveButton.setOnAction {
            if (edit) {
                CurrentNote.updateNote(title.text, Body.text, ImportantCheckBox.isSelected)
                NotesScreen.close()
            } else {
                var newnote: Note = Note(stage,numOfNotesAdded, title.text, Body.text, ImportantCheckBox.isSelected)
                if (ImportantCheckBox.isSelected) {
                    newnote.highlight = true
                    newnote.highlightUpdate()
                }
                UpdateList(newnote)
            }
            stack.children.removeAll(rectangle)
            NotesScreen.close()
        }
    }
    // Filters the notes based on Important button and search button
    fun filter(filters:String = "") {
        for (note in listOfNotes) {
            notelist.children.removeAll(note.display)
        }
        listOfDNotes.clear()
        var num:Int = 0
        for (note in listOfNotes) {
            if (Important == true && (note.highlight) &&
                ((note.fulltext).contains(filters, ignoreCase = true))) {
                notelist.children.addAll(note.display)
                listOfDNotes.add(note)
                num += 1
            } else if (!Important && ((note.fulltext).contains(filters, ignoreCase = true))) {
                notelist.children.addAll(note.display)
                listOfDNotes.add(note)
                num += 1
            }
        }
        if (DeleteButton.isDisable && (filters == "")) {
            stats.text = numOfNotes.toString() + "     " + PrevAction
        } else if (DeleteButton.isDisable) {
            stats.text = num.toString() + "(of " + numOfNotes + ")     " + PrevAction
        }
    }
    // Updates the note by addding newnote to the display
    fun UpdateList(newnote:Note) {
        numOfNotes += 1
        PrevAction = "Added Note #" + numOfNotesAdded.toString()
        stats.text = numOfNotes.toString() + "     " + PrevAction
        numOfNotesAdded += 1
        ClearButton.isDisable = false
        listOfNotes.add(newnote)
        if (Important) {
            filter()
        } else {
            notelist.getChildren().addAll(newnote.display)
            listOfDNotes.add(newnote)
        }
    }
    // based on the int, generate a sentence of word length num of
    // SLIT without period but with capital
    fun SLITGenerator(num:Int) : String {
        var generatedString:String = SLITWords[(0..SLITLen-1).random()].replaceFirstChar { it.uppercase() }
        for (i in num downTo  2) {
            generatedString += " " + SLITWords[(0..SLITLen-1).random()]
        }
        return generatedString
    }
    // Note class stores the informations of a note
    inner class Note(stage: Stage, number:Int, title:String, content:String) {
        var notenum = number
        var title = title
        var content = content
        var fulltext = title + "\n\n" + content
        var highlight:Boolean = false
        val note = TextArea()
        val display = StackPane(note)
        val rectangle = Rectangle(150.0, 200.0, Color.YELLOW)
        // Characterize some properties and set mouse event for clicking the note
        init {
            note.text = fulltext
            note.isEditable = false
            note.isWrapText = true
            note.setMaxSize(150.00, 200.00)
            // Handles single and double mouse click of a note
            note.setOnMouseClicked { event ->
                if (event.clickCount == 2) {
                    NewNoteWindow(stage, true, CurrentNote.notenum.toString())
                } else if (DeleteButton.isDisable) {
                    DeleteButton.isDisable = false
                    note.background = Background(BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY))
                    CurrentNote = this
                    stats.text = "#" + number.toString() + " | " + numOfNotes + "     " + PrevAction
                } else {
                    if (CurrentNote == this) {
                        CurrentNote.note.background =
                            Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
                        DeleteButton.isDisable = true
                        stats.text = numOfNotes.toString() + "     " + PrevAction
                    } else {
                        CurrentNote.note.background =
                            Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
                        note.background = Background(BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY))
                        CurrentNote = this
                        stats.text = "#" + number.toString() + " | " + numOfNotes + "     " + PrevAction
                    }
                }
            }
        }
        constructor(stage: Stage, number:Int, title:String, content:String, highlight:Boolean) : this(stage, number, title, content) {
            this.highlight = highlight
        }
        // Handles note update from Editing a note
        fun updateNote(title:String, content:String, highlight:Boolean) {
            this.title = title
            this.content = content
            this.highlight = highlight
            if (highlight) {
                highlightUpdate()
            }
            fulltext = title + "\n\n" + content
            note.text = fulltext
        }
        // Handles Important check box being clicked and updated in view
        fun highlightUpdate() {
            if (highlight) {
                display.children.removeAll(note)
                note.opacity = 0.90
//                note.background = Background(BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets(100.0, 100.0, 100.0, 100.0)))
                display.children.addAll(rectangle, note)
            } else {
                display.children.removeAll(rectangle)
            }
        }
    }
}

