import kotlin.random.Random

class Model {
    // DataSets and other datas
    var DataSets: MutableList<DataSet> = mutableListOf(
        createTestDataSet("Increasing"),
        createTestDataSet("Large"),
        createTestDataSet("Middle"),
        createTestDataSet("Single"),
        createTestDataSet("Range"),
        createTestDataSet("Percentage")
    )
    var width = 800.0
    var height = 600.0
    var currentDataSet: DataSet = DataSets[0]
    var GraphTitle: String =  currentDataSet.title
    var GraphXTitle: String = currentDataSet.xAxis
    var GraphYTitle: String = currentDataSet.yAxis
    var addComboBox: Boolean = false
    var newDataSetNum: Int = 5
    // stores the spinner for numAdjust View
    var dataTable: MutableList<DataAdjuster> = mutableListOf(DataAdjuster(currentDataSet.data[0], this, 0))
    init {
        currentDataSet.data.forEachIndexed { i, it ->
            dataTable.add(DataAdjuster(it, this, i))
        }
    }
    private var NewDataSetCounts: Int = 0;

    // all views of this model
    private val views: ArrayList<IView> = ArrayList()

    // method that the views can use to register themselves with the Model
    // once added, they are told to update and get state from the Model
    fun addView(view: IView) {
        views.add(view)
        view.updateView()
    }

    // the model uses this method to notify all of the Views that the data has changed
    // the expectation is that the Views will refresh themselves to display new data when appropriate
    private fun notifyObservers() {
        println("notifyObservers Called")
        for (view in views) {
            view.updateView()
        }
    }

    // Names of the current graph being changed
    public fun changeName(title: String, XTitle: String, YTitle: String) {
        println("ChangeName Called")
        GraphTitle = title
        GraphXTitle = XTitle
        GraphYTitle = YTitle
        currentDataSet.title = title
        currentDataSet.xAxis = XTitle
        currentDataSet.yAxis = YTitle
        notifyObservers()
    }

    // add new DataSet to DataSets and refresh UI
    public fun addDataSet() {
        println("addDataSet Called")
        addComboBox = true
        NewDataSetCounts += 1
        val name: String = "New" + NewDataSetCounts.toString()
        val newDataSet = DataSet(
            name,
            SLITGenerator(3),
            SLITGenerator(1),
            SLITGenerator(1),
            listofRandomDataGenerator(newDataSetNum)
        )
        DataSets.add(newDataSet)
        GraphTitle = newDataSet.title
        GraphXTitle = newDataSet.xAxis
        GraphYTitle = newDataSet.yAxis
        currentDataSet = newDataSet
        dataTable.clear()
        currentDataSet.data.forEachIndexed { i, it ->
            dataTable.add(DataAdjuster(it, this, i))
        }

        notifyObservers()
    }

    // Change the current graph diplayed in UI
    public fun changeDataSet(title: String) {
        println("changeDataSet Called")
        currentDataSet = DataSets[titleSearch(title)]
        GraphTitle = currentDataSet.title
        GraphXTitle = currentDataSet.xAxis
        GraphYTitle = currentDataSet.yAxis
        print(currentDataSet.title)
        dataTable.clear()
//        print(dataTable.size)
        currentDataSet.data.forEachIndexed { i, it ->
            dataTable.add(DataAdjuster(it, this, i))
        }
        notifyObservers()
    }

    // Searches for the index of the titel in Datasets
    public fun titleSearch(title: String): Int {
        DataSets.forEachIndexed { i, it ->
            if (it.dataSetTitle == title) {
                return i
            }
        }
        return -1
    }

    // Refresh graph
    public fun graphRefresh() {
        notifyObservers()
    }

    val SLIT: String =
        "Lorem ipsum dolor sit amet consectetur adipiscing elit In interdum massa non eleifend porttitor Donec bibendum augue eu consequat convallis Morbi sit amet nisi purus Donec a tristique sem Quisque volutpat ex nec nibh volutpat molestie In mollis lectus vel ornare faucibus Mauris congue lacus non est semper faucibus Sed et ipsum nunc Orci varius natoque penatibus et magnis dis parturient montes nascetur ridiculus mus iaculis efficitur consequat Sed eu nisi in eros tincidunt aliquam Fusce porta dolor fringilla dignissim aliquet Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas Interdum et malesuada fames ac ante ipsum primis in faucibus Donec maximus viverra nibh ut convallis arcu lacinia nec Sed vulputate volutpat sapien eget egestas Nulla dolor ligula pharetra non ipsum dapibus ultricies mattis sem Proin accumsan sapien vel dictum sagittis Praesent ex eros tempor ac lacinia nec rhoncus nec ex Donec et velit convallis ultrices enim quis pulvinar sem In pulvinar tempor fermentum In vehicula massa ut tellus ullamcorper ut ultrices libero dictum Donec a fringilla turpis Vivamus vitae aliquam ex Nam sit amet mi commodo dui consequat consequat eu ut nunc Nunc lacus elit pellentesque ac porta quis tincidunt eget ipsum Nullam malesuada lectus non nibh semper cursus Cras eu molestie felis Duis ut felis ligula Cras quis arcu nec erat malesuada maximus Nunc id quam eu purus dictum tincidunt a non eros Sed blandit ex non consectetur cursus velit mauris hendrerit velit a ultrices felis nisl id augue In euismod purus tellus Pellentesque in ligula venenatis venenatis libero id pellentesque ipsum Cras molestie nulla sit amet ligula porttitor lacinia et efficitur elit Quisque a tempor elit In tellus turpis efficitur eget vestibulum id commodo ut nisi Sed ac arcu a diam egestas ornare et ac quam Nulla et cursus magna Vestibulum semper felis id massa egestas et pharetra magna eleifend Duis posuere ante a magna sodales cursus Integer venenatis tincidunt arcu sed posuere nibh scelerisque sit amet In felis tortor vehicula eget nibh sit amet porttitor tincidunt nisi Suspendisse consequat consequat lacus nec consequat metus cursus et Quisque dapibus rutrum enim id varius Nam dapibus ligula at ligula eleifend tempus Nullam eget nibh eu dolor tincidunt iaculis non sit amet sem Sed placerat ex mollis lacinia risus vitae tincidunt erat Sed id diam lacus Nunc vulputate sapien eu tempor dignissim risus sapien eleifend ex at lacinia est enim in diam Praesent varius egestas ipsum Integer nec dui tristique laoreet velit eget luctus nisl Etiam pharetra erat magna Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas Aliquam sed lectus vulputate tristique dolor elementum volutpat dui"
    var SLITWords: List<String> = SLIT.split(" ")
    val SLITLen: Int = SLITWords.size

    // Generator for random string from SLIT
    fun SLITGenerator(num: Int): String {
        var generatedString: String = SLITWords[(0..SLITLen - 1).random()].replaceFirstChar { it.uppercase() }
        for (i in num downTo 2) {
            generatedString += " " + SLITWords[(0..SLITLen - 1).random()].replaceFirstChar { it.uppercase() }
        }
        return generatedString
    }

    // Generator for random int from 1 to 100 in numberOfData amount
    fun listofRandomDataGenerator(numberOfData: Int): MutableList<Int> {
        val rand = Random(System.nanoTime())
        var Datas: MutableList<Int> = mutableListOf((0..100).random(rand))
        for (i in numberOfData downTo 2) {
            Datas.add((0..100).random(rand))
        }
        return Datas
    }
}