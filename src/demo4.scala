// 定义Scala的简单数据结构
// 以定义单向链表为例
// 函数式编程的数据结构只能由纯函数来操作,所以一般定义为不可变对象

trait List[+A]  // 定义统一泛型接口
case object Nill extends List[Nothing]  // 定义空链表对象
// 链表的定义为一个头结点接一个链表对象
case class Cons[+A](head: A, tail: List[A]) extends List[A]

// 创建整数链表对象和其上的一些操作
// 对不可变对象的操作都是返回一个新对象,并且共享不可变的公共区域
object List {
  // 对链表中的元素求和
  def sum(ints: List[Int]): Int = ints match {
    case Nill => 0
    case Cons(x, xs) => x + sum(xs)
  }
  // 删除前n个元素
  def drop[A](l: List[A], n: Int): List[A] = l match {
    case Nill => Nill
    case Cons(_, xs) => {
      if (n > 1) drop(xs, n-1)
      else xs
    }
  }

  // 删除所有符合判定的元素
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nill => Nill
    case Cons(x, xs) => {
      if (f(x)) dropWhile(xs, f)
      else Cons(x, dropWhile(xs, f))
    }
  }

  // 按顺序合并两个链表
  def append[A](a1: List[A], a2: List[A]): List[A] = a1 match {
    case Nill => a2
    case Cons(x, xs) => Cons(x, append(xs, a2))
  }

  def apply[A](as: A*): List[A] = {
    if (as.isEmpty) Nill
    else Cons(as.head, apply(as.tail: _*))
  }
}

object demo4 {
  def main(args: Array[String]): Unit = {
    val l: List[Int] = List(1, 2, 3, 4)
    println(List.sum(l))
    val l1: List[Int] = List.drop(l, 2)
    println(List.sum(l1))
    val l2: List[Int] = List.dropWhile(l, (x: Int) => (x < 4))
    println(List.sum(l2))
  }
}

