import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.DoubleType

val dfRead = spark.read.option("header", "false").csv("proj/input/covid_19_data_cleaned.csv")
val df = dfRead.toDF("State_name", "Date", "Confirmed", "Deaths", "Recovered", "Incident_rate", "fatality_ratio")

// Convert columns to appropriate types
val data = df.withColumn("Confirmed", df("Confirmed").cast("Int"))
  .withColumn("Deaths", df("Deaths").cast("Int"))
  .withColumn("Recovered", df("Recovered").cast("Int"))
  .withColumn("Incident_rate", df("Incident_rate").cast("Double"))
  .withColumn("fatality_ratio", df("fatality_ratio").cast("Double"))

// Compute statistics for each column
for (col <- data.columns) {
  val colData = data.select(col)
  val summary = colData.summary()
  summary.show()

  val uniqueValues = colData.distinct().count()
  println(s"Number of unique values in $col: $uniqueValues")

  val nanValues = colData.filter(colData(col).isNull).count()
  println(s"Number of NaN values in $col: $nanValues")
}

// Compute min and max dates
val dateData = df.select(to_date(df("Date"), "yyyy-MM-dd").as("Date"))
val minDate = dateData.agg(min("Date")).first().getDate(0)
val maxDate = dateData.agg(max("Date")).first().getDate(0)
println(s"Min date: $minDate, Max date: $maxDate")

spark.stop()
