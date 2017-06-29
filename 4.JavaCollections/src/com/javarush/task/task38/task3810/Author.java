package com.javarush.task.task38.task3810;

public @interface Author {
    String value() default "NoName";

    Position position() default Position.OTHER;

    //напиши свой код
}
