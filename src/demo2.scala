// 采用traits(特性)来实现通用比较器

// >, <, =, >=, <=, != 6种只需要< 和 =两种就可以表示
// 其中=默认通过equal方法实现
trait Order {
  def < (that: Any): Boolean  // 抽象类由具体对象实现
  def <= (that: Any): Boolean = (this < that) || (this == that)
  def > (that: Any): Boolean = !(this <= that)
  def >= (that: Any): Boolean = !(this < that)
}

// 定义Date对象并继承自order
class Date(y: Int, m: Int, d: Int) extends Order {
  def year = y
  def month = m
  def day = d

  override def toString: String = year + "-" + month + "-" + day

  override def equals(obj: Any): Boolean =
    obj.isInstanceOf[Date] && {
      val o = obj.asInstanceOf[Date]
      o.day == this.day && o.month == this.month && o.year == this.year
    }

  override def <(that: Any): Boolean = {
    if (!that.isInstanceOf[Date]) {
      println("this object is not a Date")
    }
    val o = that.asInstanceOf[Date]
    (this.year < o.year) ||
      (this.year == o.year && (this.month < o.month ||
        (this.month == o.month && this.day < o.day)))
  }
}

object demo2 {
  def main(args: Array[String]): Unit = {
    val date1 = new Date(2019, 7, 25)
    val date2 = new Date(2019, 8, 22)
    println(date1.toString)
    println(date2.toString)
    println(date1 < date2)
  }

}
