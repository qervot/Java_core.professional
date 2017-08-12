package com.nicolas;

import java.util.function.Consumer;

// Класс, в котором объявлено поле, в которое можно записать функцию
public final class WorkClass<T> {

    private Consumer<T> myCallBack; //в нашем случае, функция принимает на вход один параметр, Consumer - функциональный интерфейс

    /**
     * Инициализируем наш коллбэк
     * @param myCallBack
     */
    public void setMyCallBack(Consumer<T> myCallBack) {
        this.myCallBack = myCallBack;
    }

    /**
     * Выполняем функцию, поданную извне
     * @param var
     */
    public void someMeth(T var) {
        if (myCallBack != null) {
            myCallBack.accept(var);
        }
    }
}
