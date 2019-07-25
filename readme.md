# Scala demo learn

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

