package com.yjf.learning.spark.core
import com.yjf.learning.commom.format.RDDSpecialTextOutputFormat
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Created by Jeff Yang on 2019-09-26 15:05.
  * Update date:
  * Project: learningsparkgroup
  * Package: com.yjf.learning.spark.core
  * Describe :   基础学习
  * Dependency :
  * Frequency: Calculate once a day.
  * Result of Test: test ok
  * Command:
  *
  * Email:  highfei2011@126.com
  * Status：Using online
  *
  * Please note:
  *    Must be checked once every time you submit a configuration file is correct!
  *    Data is priceless! Accidentally deleted the consequences!
  *
  */
object SpecialOutPutWordApp {
  def main(args: Array[String]): Unit = {
    val inputPath = "public/data/city/provice.txt"
    val outputPath = s"public/output/spark/${System.currentTimeMillis()}"
    // 1 、初始化 SparkContext
    val spark = new SparkContext(
      new SparkConf().setMaster("local[2]").setAppName("TestWC")
    )

    // 2、创建 RDD（读取数据源）
    val wordRDD = spark.textFile(inputPath)

    // 3、Transformation
    val mapRDD = wordRDD
      .filter(_.split(",").length >= 2)
      .map(x => {
        val arrays = x.split(",")
        // (省，市)
        (arrays(0), arrays(1))
      })

    // 4、Action
    mapRDD.saveAsHadoopFile(
      outputPath,
      classOf[String],
      classOf[String],
      classOf[RDDSpecialTextOutputFormat]
    )

    // 5、停止 SparkContext
    spark.stop()
  }

}
