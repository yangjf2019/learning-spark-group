package com.yjf.learning.commom.utils
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

/**
  * @author Created by Jeff Yang on 2019-09-26 14:51.
  * Update date:
  * Project: learningsparkgroup
  * Package: com.yjf.learning.commom.utils
  * Describe :   时间工具类
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
object LearningDateUtils extends Serializable {

  def getSimpleDateFormat(): SimpleDateFormat = getSimpleDateFormat(null)

  /**
    * 给小于一位数的数字补0，变为2位数
    *
    * @param num 数字
    * @return 补位后的值
    */
  def transformDate(num: Int): String = if (num < 10) "0" + num else num + ""

  /**
    * 获取指定时间的 long 数值的时间
    *
    * @param day 天数字
    * @param hour 小时
    * @param minute 分钟
    * @param second 秒钟
    * @return Long
    */
  def specialLong(day: Integer,
                  hour: Integer,
                  minute: Integer,
                  second: Integer): Long = {
    val cal: Calendar = Calendar.getInstance
    cal.add(Calendar.DATE, day)
    cal.set(Calendar.HOUR_OF_DAY, hour) //小时
    cal.set(Calendar.MINUTE, minute) //分钟
    cal.set(Calendar.SECOND, second) //秒
    cal.set(Calendar.MILLISECOND, 999) //毫秒
    cal.getTimeInMillis
  }

  /**
    * 将指定时间转换为想要的时间范围
    * 比如 5分钟 ，则 specialTime=300000L
    *
    * @param millisecond 时间
    * @param specialTime 切分的大小
    * @return yyyyMMddHHmm
    */
  def getFiveMinute(millisecond: Long, specialTime: Long): String = {
    val longTime = millisecond / specialTime * specialTime
    getSimpleDateFormat("yyyyMMddHHmm").format(new Date(longTime))

  }

  def getSimpleDateFormat(format: String): SimpleDateFormat =
    if (format == null) new SimpleDateFormat("yyyyMMddHHmm")
    else new SimpleDateFormat(format)

  /**
    * 获取从当前时间往前推n个月的日期
    *
    * @param before 往前几个月(用负数表示)
    * @return 日期字符串
    */
  def getSpecialMonth(before: Int): String = {
    val sdf = new SimpleDateFormat("yyyyMMdd")
    val cl = Calendar.getInstance()
    // cl.setTime(dateNow);
    // cl.add(Calendar.DAY_OF_YEAR, -1);//一天
    // cl.add(Calendar.WEEK_OF_YEAR, -1);//一周
    cl.add(Calendar.MONTH, before) //从现在算，之前一个月,如果是2个月，那么-1-----》改为-2
    val dateFrom = cl.getTime
    sdf.format(dateFrom)
  }
}
