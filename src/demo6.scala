// 利用运筹学中的函子概念构建泛华的数据结构
// 构造泛华的对集合求和的函数sum
// 定义泛化的集合结构

trait Monad[A] {
  def mappend(a1: A, a2: A): A  //运算的闭包性
  def mzero: A  //单位元
}

object Monad{
  implicit object intMonad extends Monad[Int] {
    override def mappend(a1: Int, a2: Int): Int = a1 + a2

    override def mzero: Int = 0
  }
  implicit object strMonad extends Monad[String] {
    override def mappend(a1: String, a2: String): String = a1 + a2

    override def mzero: String = ""
  }
}

// 定义泛化的求和过程(通过折叠过程)
trait foldLeft[M[_]] {
  // xs为集合,b为求和初始值(对于sum即为上述的mzero),f为求和方式(对于sum即为上述的mappend)
  def foldLeft[A, B](xs: M[A])(b: B)(f: (B, A) => A): A
}

object foldLeft {
  // 对列表这一集合的折叠方式
  implicit object listFoldLeft extends foldLeft[List] {
    override def foldLeft[A, B](xs: List[A])(b: B)(f: (B, A) => A): A = xs.foldLeft(b)(f)
  }
}
List


object demo6 {
  //sum的泛化形式,M为集合类型,A为数据类型
  def sum[M[_], A](xs: M[A])(implicit m: Monad[A], f: foldLeft[M]): A = {
    f(xs)(m.mzero)(m.mappend)
  }

  def main(args: Array[String]): Unit = {
    print(sum(List(1, 2, 3, 4)))
  }
}
