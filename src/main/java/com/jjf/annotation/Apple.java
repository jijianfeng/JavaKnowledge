package com.jjf.annotation;

/**
 * Created by jjf_lenovo on 2017/5/30.
 */

public class Apple {

    @FruitName("Apple")
    private String appleName;

    @FruitColor(fruitColor= FruitColor.Color.RED)
    private String appleColor;

    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }
    public String getAppleColor() {
        return appleColor;
    }


    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }
    public String getAppleName() {
        return appleName;
    }

    public void displayName(){
        System.out.println("Ë®¹ûÊÇ£º"+this.appleColor+this.appleName);
    }

    public static void main(String args[]){
        Apple apple = new Apple();
        apple.displayName();
    }
}