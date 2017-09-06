package com.jjf.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * http://www.importnew.com/19345.html  java8特性
 * Created by jijianfeng on 2017/9/6.
 */
public class MethodReferenceTest {
  /**
  方法引用提供了一个很有用的语义来直接访问类或者实例的已经存在的方法或者构造方法。结合Lambda表达式，方法引用使语法结构紧凑简明。不需要复杂的引用。
   */
  public static class Car {
    public static Car create( final Supplier< Car > supplier ) {
      return supplier.get();
    }

    public static void collide( final Car car ) {
      System.out.println( "Collided " + car.toString() );
    }

    public void follow( final Car another ) {
      System.out.println( "Following the " + another.toString() );
    }

    public void repair() {
      System.out.println( "Repaired " + this.toString() );
    }
  }

  public static void main(String[] args){
    //第一种方法引用是构造方法引用，语法是：Class::new ，对于泛型来说语法是：Class<T >::new，请注意构造方法没有参数:
    final Car car = Car.create( Car::new );
    final List< Car > cars = Arrays.asList(car );
    //第二种方法引用是静态方法引用，语法是：Class::static_method请注意这个静态方法只支持一个类型为Car的参数。
    cars.forEach( Car::collide );
    //第三种方法引用是类实例的方法引用，语法是：Class::method请注意方法没有参数。
    cars.forEach( Car::repair );
    //最后一种方法引用是引用特殊类的方法，语法是：instance::method，请注意只接受Car类型的一个参数。
    final Car police = Car.create( Car::new );
    cars.forEach( police::follow );
  }
}
