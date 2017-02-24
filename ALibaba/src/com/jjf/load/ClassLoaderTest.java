package com.jjf.load;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器在类相等判断中的影响
 * 
 * instanceof关键字
 * 
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        // 自定义类加载器
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(fileName);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);   
                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };

        // 使用ClassLoaderTest的类加载器加载本类
//        System.out.println("----"+ClassLoaderTest.class.getClassLoader().getSystemClassLoader().toString());
        Object obj1 = ClassLoaderTest.class.getClassLoader().loadClass("com.jjf.load.ClassLoaderTest").newInstance();
        System.out.println(obj1.getClass());
        System.out.println(obj1 instanceof com.jjf.load.ClassLoaderTest);

        // 使用自定义类加载器加载本类
        Object obj2 = myLoader.loadClass("com.jjf.load.ClassLoaderTest").newInstance();
        System.out.println(obj2.getClass());
        System.out.println(obj2 instanceof com.jjf.load.ClassLoaderTest);
    }
}