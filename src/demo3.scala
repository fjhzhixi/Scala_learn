// 继承父类与实现特性
// 类之间的结构

// 抽象泛型类迭代器
abstract class AbsIterator {
  type T
  def hasNext: Boolean
  def next(): T
}

// 迭代器特性
// 对每一个元素进行f函数操作,相当于实现map
trait RichIterator extends AbsIterator {
  def foreach(f: T=>Unit): Unit = while(hasNext) f(next())
}

class StringIterator(s: String) extends AbsIterator with RichIterator {
  type T = Char
  private var i = 0
  def hasNext = i < s.length
  def next() = {
    val ch = s charAt(i)
    i += 1
    ch
  }
}

object demo3 {
  def main(args: Array[String]): Unit = {
    val test = new StringIterator("HelloWorld")
    test foreach print
  }
}