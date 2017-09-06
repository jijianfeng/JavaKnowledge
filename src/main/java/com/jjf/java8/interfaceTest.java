package com.jjf.java8;

import java.util.function.Supplier;

/**
 * http://www.importnew.com/19345.html  java8特性
 * Created by jijianfeng on 2017/9/6.
 */
public class interfaceTest {
  private interface Defaulable {
    // Interfaces now allow default methods, the implementer may or
    // may not implement (override) them.
    /**
     Java 8增加了两个新的概念在接口声明的时候：默认和静态方法。默认方法和Trait有些类似，但是目标不一样。
     默认方法允许我们在接口里添加新的方法，而不会破坏实现这个接口的已有类的兼容性，也就是说不会强迫实现接口的类实现默认方法。
     */
    default String notRequired() {
      return "Default implementation";
    }
  }

  private static class DefaultableImpl implements Defaulable {}

  private static class OverridableImpl implements Defaulable {
    @Override
    public String notRequired() {
      return "Overridden implementation";
    }
  }

  /**
   * Java 8 的另外一个有意思的新特性是接口里可以声明静态方法，并且可以实现。
   */
  private interface DefaulableFactory {
    // Interfaces now allow static methods
    static Defaulable create( Supplier< Defaulable > supplier ) {
      return supplier.get();
    }
  }

  public static void main( String[] args ) {
    Defaulable defaulable = DefaulableFactory.create( DefaultableImpl::new );
    System.out.println( defaulable.notRequired() );

    defaulable = DefaulableFactory.create( OverridableImpl::new );
    System.out.println( defaulable.notRequired() );
  }
}


