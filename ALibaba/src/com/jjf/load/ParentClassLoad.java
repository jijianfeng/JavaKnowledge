package com.jjf.load;

/**
 * sun.misc.Launcher$AppClassLoader@5d1eb50b  ????ËµºÃµÄExtClassLoaderÄØ
 * @author jjf_lenovo
 *
 */
public class ParentClassLoad {
	public static void main(String args[]){
		ClassLoader classLoader = ParentClassLoad.class.getClassLoader();
		if(classLoader!=null){
			System.out.println(classLoader.toString());
			classLoader = classLoader.getParent();
		}
	}
}
