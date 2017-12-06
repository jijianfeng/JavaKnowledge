package com.jjf.reference;

import java.util.Arrays;

/**
 * @author by jijianfeng on 2017/12/6.
 */
public class AbstractReference {
    static class ClassBean extends AbstratctBean {

        @Override
        public void action() {
            System.out.println("123123");
        }
    }

    public static void main(String[] args){
        ClassBean classBean = new ClassBean();
        Arrays.stream(classBean.getClass().getSuperclass().getDeclaredFields()).forEach(f->{
            System.out.println(f.getName());
        });
    }
}
