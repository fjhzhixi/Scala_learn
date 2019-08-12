// Scala的错误处理示范
// 自定义Option与Either类
// 错误处理使用返回值传递错误实现


trait Option[+A] {
  // 如果option不为None,则对其应用f
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(a) => Some(f(a))
  }
  // 如果option不为None,则对其应用f,可能会失败
  def flatMap[B](f: A => Option[B]): Option[B] = this match {
    case None => None
    case Some(a) => f(a)
  }
  // 如果值不满足f,则转换Some为None
  def filter(f: A => Boolean): Option[A] = this match {
    case None => None
    case Some(a) => {
      if (f(a)) this
      else None
    }
  }
}

// 提升函数,将一个普通函数封装成Option=>Option模式的

def lift[A, B](f: A => B): Option[A] => Option[B] = _ map f

// 封装函数,将一个异常处理类函数可以使用Option处理
def Try[A](a: =>A): Option[A] = {
  try {
    Some(a)
  }catch {
    case _: Exception => None
  }
}

case class Some[+A] (get: A) extends Option[A]
case object None extends Option[Nothing]