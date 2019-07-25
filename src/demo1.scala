// 条件类与模式匹配
// 在树形结构上实现简单的求和和求导
/*
树形结构设计 :
1. 叶节点为运算符类型(只有加法)
2. 非叶节点为变量类型(string)或者常量类型(int)
 */

object demo1{
  abstract class Tree
  // 定义叶节点的运算符形式
  case class Sum(l: Tree, r: Tree) extends Tree
  // 定义非叶节点的变量或常量
  case class Var(n: String) extends Tree
  case class Const(v: Int) extends Tree

  //定义环境(上下文)类型 : 在该例子中即变量的取值
  type Environment = String => Int

  // 定义在数结构上的运算函数
  // 参数 : 树形的根节点, 上下文
  // 返回值 : int
  // 逐个匹配各种模式,对应相应的操作
  def eval(t: Tree, env: Environment): Int = t match {
    case Sum(l, r) => eval(l, env) + eval(r, env)
    case Var(n) => env(n)
    case Const(v) => v
  }
  def main(args: Array[String]): Unit = {
    val env: Environment = {
      case "x" => 5
    }
    val t: Tree = Sum(Var("x"), Const(3))
    println(eval(t, env))
  }
}
