package com.yjf.learning.commom.format
import com.yjf.learning.commom.utils.LearningDateUtils
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat

/**
  * @author Created by Jeff Yang on 2019-09-26 14:47.
  * Update date:
  * Project: learningsparkgroup
  * Package: com.yjf.learning.commom
  * Describe :  多格式输出类
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
class RDDMultipleTextOutPutFormat extends Serializable {}

/**
  * 不对 key 做修改，直接输出 key
  */
class RDDMultipleOutputFormat extends MultipleTextOutputFormat[Any, Any] with Serializable{
  override def generateActualKey(key: Any, value: Any): Any =
    NullWritable.get()

  override def generateFileNameForKeyValue(key: Any, value: Any, name: String): String =
    key.asInstanceOf[String]
}

/**
  * 在 key 的后面添加上个月的时间 _yyyyMMdd
  */
class RDDSpecialTextOutputFormat( ) extends MultipleTextOutputFormat[Any, Any] with Serializable{
  override def generateActualKey(key: Any, value: Any): Any =
    NullWritable.get()

  override def generateFileNameForKeyValue(key: Any, value: Any, name: String): String =
    key.asInstanceOf[String] + "_" + LearningDateUtils.getSpecialMonth(-1)
}
