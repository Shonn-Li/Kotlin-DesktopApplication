/*
 * A simple dataset data type
 */
data class DataSet(
    var dataSetTitle: String,
    var title: String,
    var xAxis: String,
    var yAxis: String,
    var data: MutableList<Int>
)

/*
 * Create test DataSets
 */
fun createTestDataSet(name: String): DataSet {

    return when (name) {
        "Increasing" -> DataSet(
            "Increasing","Increasing from 0 to 100", "Datapoint", "Value",
            (10..100 step 10).toMutableList()
        )

        "Middle" -> DataSet(
            "Middle","Middle", "Datapoint", "Value",
            mutableListOf<Int>(20, 30, 50, 99, 50, 30, 20)
        )

        "Large" -> DataSet(
            "Large","Testing 20 Data Points", "Datapoint", "Value",
            (20 downTo 1).toMutableList()
        )
        "Single" -> DataSet(
            "Single","A Single Value", "XAxis", "YAxis",
            mutableListOf<Int>(50)
        )

        "Range" -> DataSet(
            "Range","Range Test", "Test", "Value",
            mutableListOf<Int>(100, 10, 1, 0)
        )

        "Percentage" -> DataSet(
            "Percentage","Test1", "Proportion", "Category",
            mutableListOf<Int>(5, 10, 25, 25, 35)
        )
        else -> DataSet(
            "WTF","Increasing from 0 to 100", "Datapoint", "Value",
            (10..100 step 10).toMutableList()
        )
    }
}
