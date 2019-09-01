// 以随机数生成为例演示函数式的状态表达
// 函数式的状态转移通过返回一个新的状态对象实现

// 由s状态生成A和下一个状态s
type State[s, +A] = s => (s, A)

trait RNG {
  def nextInt: (Int, RNG)
}

case class SimpleRNG(seed: Long) extends RNG {
  override def nextInt: (Int, RNG) = {
    val nextSeed  = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRNG = SimpleRNG(nextSeed)
    val n = (nextSeed >> 16).toInt
    (n, nextRNG)
  }
}

type Rand[+A] = State[RNG, A]

def map[A, B](s: RNG)(f: A => B): Rand[B] = {
  rng => {
    val (a, rng2) = s(rng)
    (f(a), rng2)
  }
}

// 对map函数的使用
// 基于int的随机数生成double在0到1的随机数
def doubleRNG(rng: RNG): (RNG, Double) = {
  map[Int, Double](SimpleRNG)((a: Int) => (a / Int.MaxValue))(rng)
}


