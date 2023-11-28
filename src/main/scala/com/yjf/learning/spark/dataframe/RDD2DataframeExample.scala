package com.yjf.learning.spark.dataframe

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.slf4j.LoggerFactory

/**
 * @Author Created by Jeff Yang on 11/28/23 11:27.
 * @Update date:
 * @Project: learning-spark-group
 * @Package: com.yjf.learning.spark.dataframe
 * @Describe: 三种转换 RDD 为 DataFrame 的方法
 *
 */
object RDD2DataframeExample {
  private val LOG = LoggerFactory.getLogger(RDD2DataframeExample.getClass)

  // 构建 3位学生信息
  private val student = Array(
    (1001, "Cassie", 21, "北京市朝阳区望京南地铁站D口", "13000001234"),
    (1002, "Tom", 22, "山东省青岛市黄岛区阿里山路10号", "15000001234"),
    (1003, "Tina", 25, "山东省青岛市开发区金沙滩路", "18000001234")
  )

  /**
   * 主函数
   */
  def main(args:Array[String]): Unit = {
    LOG.warn("======> 1、创建 spark session")
    val conf: SparkConf = new SparkConf()
    conf.setMaster("local[2]")
    conf.setAppName(RDD2DataframeExample.getClass.getCanonicalName)
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    LOG.warn("======> 2、创建测试数据集")
    val studentRDD: RDD[(Int, String, Int, String, String)] = spark.sparkContext.parallelize(student)
    LOG.warn("======> 3、转换 RDD 为 DataFrame\n")
    LOG.warn("======> 方法一：通过 StructType 创建 Dataframe")
    func1(spark, studentRDD)
    LOG.warn("======> 方法二：通过 RDD 类型推断，创建 DataFrame")
    func2(spark, studentRDD)
    LOG.warn("======> 方法三：通过定义 schema 类，创建 DataFrame")
    func3(spark, studentRDD)

    LOG.warn("======> 关闭 spark 资源")
    spark.stop()
  }

  /**
   * 方法一：通过 StructType 创建 Dataframe
   * @param spark SparkSession
   * @param rdd 输入
   * @return 输出
   */
  private def func1(spark:SparkSession, rdd:RDD[(Int, String, Int, String, String)]):DataFrame ={
    LOG.warn("------> (1) 构建 structSchema")
    val structSchema: StructType = StructType(
      List(
        StructField("id", IntegerType, true),
        StructField("name", StringType, true),
        StructField("age", IntegerType, true),
        StructField("address", StringType, true),
        StructField("phoneNumber", StringType, false)
      )
    )
    LOG.warn("------>  (2)创建 DF")
    val row = rdd.map(r => Row(r._1, r._2, r._3, r._4, r._5))
    val dataFrame = spark.createDataFrame(row, structSchema)
    LOG.warn("------>  (3)显示创建的 DF 的 schema 信息")
    dataFrame.printSchema()
    dataFrame.show(false)
    dataFrame
  }

  /**
   * 方法二：通过 RDD 类型推断，创建 DataFrame
   * PS：不建议使用，因复杂类型无法准确判定
   *
   * @param spark SparkSession
   * @param rdd   输入
   * @return 输出
   */
  private def func2(spark:SparkSession, rdd:RDD[(Int, String, Int, String, String)]):DataFrame ={
    LOG.warn("------>  (1)引入隐式转换")
    import spark.implicits._
    LOG.warn("------>  (2)类型推断生成 DF，并添加列名称")
    val dataFrame = rdd.toDF("id", "name", "age", "address", "phoneNumber")
    LOG.warn("------>  (3)显示创建的 DF 的 schema 信息")
    dataFrame.printSchema()
    dataFrame.show(false)
    dataFrame
  }

  /**
   * 方法三：通过定义 schema 类，创建 DataFrame
   *
   * @param spark SparkSession
   * @param rdd   输入
   * @return 输出
   */
  private def func3(spark:SparkSession, rdd:RDD[(Int, String, Int, String, String)]):DataFrame ={
    LOG.warn("------>  (1)引入隐式转换")
    import spark.implicits._
    LOG.warn("------>  (2)定义 schema 类生成 DF")
    val dataFrame = rdd.map(r => Student(r._1, r._2, r._3, r._4, r._5)).toDF()
    dataFrame.printSchema()
    dataFrame.show(false)
    dataFrame
  }

  // 样例类：学生
  case class Student(
                      id: Int,
                      name: String,
                      age: Int,
                      address: String,
                      phoneNumber: String)
}
