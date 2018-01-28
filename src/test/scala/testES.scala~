import org.elasticsearch.spark._
import org.elasticsearch.spark.rdd.EsSpark
import org.elasticsearch.spark.sql._
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.scalatest.{Assertions, BeforeAndAfter, FunSuite}


class TestES extends FunSuite with  BeforeAndAfter with Serializable {
   test("Insert2ES") {
      val spark = SparkSession.builder
      .master("local[3]")
      .config(new SparkConf()
          .set("es.nodes", "192.168.0.21:9200")
          .set("es.port", "9200")
          .set("es.index.auto.create", "true")
          .set("es.write.operation", "upsert")
          .set("es.mapping.id", "Code")
          )
      .appName("Spar2ES2Spark")
      .getOrCreate()
import spark.implicits._

      val sc = spark.sparkContext
      val vals = sc.parallelize(
              """{
      			   "uniqueInstrId":"1a",
      			   "Code": 0,
      			   "Symbol":"AAA",
      			   "ProductDescription": "descr",
      			   "LocalCodeStr":"lcs",
      			   "ProductISIN":"isin",
      			   "EID":0,
      			   "AssetClass":"asset",
      			   "ExchangeName":"exn",
      			   "Availability": "|",
      			   "ADTV":0.0,
      			   "TNTD":0.0,
      			   "PricingTier":1,
      			   "ContractID":"contractId"
      			   }"""
      			   :: Nil
				)

      val schema = StructType(
      	Array(
      	  StructField("uniqueInstrId", StringType),
      	  StructField("Code", IntegerType),
      	  StructField("Symbol", StringType),
      	  StructField("ProductDescription", StringType),
      	  StructField("LocalCodeStr", StringType),
      	  StructField("ProductISIN", StringType),
      	  StructField("EID", IntegerType),
      	  StructField("AssetClass", StringType),
      	  StructField("ExchangeName", StringType),
      	  StructField("Availability", StringType),
      	  StructField("ADTV", DoubleType),
      	  StructField("TNTD", DoubleType),
      	  StructField("PricingTier", StringType),
      	  StructField("ContractID", StringType)
			  )
			)


		val df = spark.read.schema(schema).json(vals).select($"*")
		df.printSchema()

		df.repartition(1).write.mode(SaveMode.Overwrite).parquet("/tmp/import/referential/empty")


      df.saveToEs("test-index/productDB")

   }
}
