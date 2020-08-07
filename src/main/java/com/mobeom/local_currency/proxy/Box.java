package com.mobeom.local_currency.proxy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.function.Function;

@Component
@Lazy
public class Box<T> {

    private HashMap<String,T> box;

    public Box(){box =new HashMap<>();}
    public void put(String s, T t){box.put(s,t);}
    public T get(String s){
        Function<String,T> f = box :: get;
        return f.apply(s);
    }
    public HashMap<String,T> get(){return box;}
    public int size(){return box.size();}
    public void clear(){box.clear();}
    public void newBox(){box=new HashMap<String, T>();}

}