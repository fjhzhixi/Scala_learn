# Scala demo learn

## 基础

静态语言,对每一个对象都要确定类型(对象名: 类型)

1. `val x: Int  = ` : 定义常量

2. `var x: Int =` : 定义变量

3. `{}` : 代码块,**代码块中最后一个表达式的结果即为整个代码块的结果**

4. 函数 : `val add = (x: Int, y:Int) => x + y`

   只使用一次的多用匿名函数

5. 方法 : `def add(x: Int, y: Int): Int = {x + y}`

   有参数和参数类型还有返回值类型

   方法体为一个代码块,最后一个表达式的值即为返回值

   无返回值使用`Unit`相当于`void`

6. 类 : `class ClassName(para: Type){}`

7. 对象 : 定义的类的单个实例

   `object O {}`

8. `trait` : 类似于接口

## 语言特性

### 一切都是对象

![](image/unified-types-diagram.svg)

1. 对象 :

   ```scala
   // 默认类内属性就是传入参数,并且是公有的
   // 当人为设置属性为私有的时候要配置get/set方法
   // get和set方法的设置有些特别
   class Point {
     private var _x = 0
     private var _y = 0
     private val bound = 100
   
     def x = _x	//get方法
     def x_= (newValue: Int): Unit = {
       if (newValue < bound) _x = newValue else printWarning
     }//set方法
   
     def y = _y
     def y_= (newValue: Int): Unit = {
       if (newValue < bound) _y = newValue else printWarning
     }
     //默认set方法为get方法名之后加_
     //这样做的好处是将方法调用伪装成对属性直接操作
     val point = new Point
     point.x = 3	//调用set方法
     var i = point.x //调用get方法
   ```

   注 : **不带`val`或者`var`的参数是私有的**

### Traits

实现类之间的共享程序接口和字段

```scala
trait Iterator[A] {
  def hasNext: Boolean
  def next(): A
}
```

### 元组

`(n1, n2, ...): Tuplen(n从2到22)[type1, type2, ...]`

1. 支持不同类型的对象
2. 访问 : `Tuplen._n`访问第`n`个
3. **解构** : `(n1, n2, ...) = Tuplen`

### 混入

1. 一个类只能有一个父类,使用`extend`继承,但是可以有多个混入特性,使用`with`实现

### 高阶函数

使用函数作为参数,或者返回值为函数的函数

### 柯里化

1. 方法可以定义多个参数列表,当使用较少的参数列表调用多参数列表的方法时,会产生一个新的函数,该函数接收剩余的参数列表作为其参数.
2. 参数从左向右自动进行类型推断

```scala 
def foldLeft[B](z: B)(op: (B, A) => B): B
// 使用
// 传递完整参数
foldLeft(0)((m, n) => m + n)
// 传递部分参数
foldLeft(0)
//返回的结果为F(op: (B, A) => B): B 类型的函数
```

### 案例类

`case class`

1. 案例类在比较时是**按值比较**而不是按引用比较(特殊)
2. **通常案例类实例设置为不可变对象**
3. 案例类用于模式匹配可以解构出内部对象

### 模式匹配

1. 当做`switch - case`语句使用

2. 匹配案例类

   ```scala
   abstract class Notification
   case class Email(sender: String, title: String, body: String) extends Notification
   case class SMS(caller: String, message: String) extends Notification
   case class VoiceRecording(contactName: String, link: String) extends Notification
   
   def showNotification(notification: Notification): String = {
     notification match {
       case Email(sender, title, _) =>
         s"You got an email from $sender with title: $title"
       case SMS(number, message) =>
         s"You got an SMS from $number! Message: $message"
       case VoiceRecording(name, link) =>
         s"you received a Voice Recording from $name! Click the link to hear it: $link"
     }
   }
   ```

3. 仅仅匹配数据类型

   ```scala
   abstract class Device
   case class Phone(model: String) extends Device {
     def screenOff = "Turning screen off"
   }
   case class Computer(model: String) extends Device {
     def screenSaverOn = "Turning screen saver on..."
   }
   
   def goIdle(device: Device) = device match {
     case p: Phone => p.screenOff
     case c: Computer => c.screenSaverOn
   }
   ```

### 单例对象

只有一个实例的类,使用`object`定义,在第一次使用时创建

1. 定义只有一次实例的对象
2. **伴生对象** : 当一个单例对象和某个类共享一个名称时,这个单例对象称为 伴生对象. 同理,这个类被称为是这个单例对象的伴生类.类和它的伴生对象可以互相访问其私有成员.**使用伴生对象来定义那些在伴生类中不依赖于实例化对象而存在的成员变量或者方法**
3. 在 Java 中 `static` 成员对应于 Scala 中的伴生对象的普通成员



## 条件类和模式匹配

### 条件类

1. 都继承自同一抽象类
2. 根据实例化传入的不同参数区分
3. 定义 : `case class Name(parm: type) extend parent`

### 环境(上下文)

1. 为变量赋予一个特定值的函数

2. 定义 

   ```scala
   type Environment = String => Int	//定义环境映射的形式
   val env: Environment = {
       case "x" => 5
       case "y" => 3
   }
   ```

### 模型匹配

1. 在函数中根据具体的类型匹配某一种条件类之后进行对应的操作

2. ```scala
   //Sum, Var, Const均是Tree的条件子类
   //根据t变量match的子类类型对应不同的操作
   //返回值时Int类型的值
   def eval(t: Tree, env: Environment): Int = t match{
     case Sum(l, r) => eval(l, env) + env(r, env)
     case Var(n) => env(n)
     case Const(v) => v
   }
   ```

## Traits (特征特性)

