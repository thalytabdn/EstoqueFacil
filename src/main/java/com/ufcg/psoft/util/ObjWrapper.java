package com.ufcg.psoft.util;

import java.io.Serializable;

public class ObjWrapper<T> implements Serializable {


    private T obj;

    public ObjWrapper(T obj) {
        this.obj = obj;
    }

    public ObjWrapper(){}

    public T getObj() {
        return obj;
    }

}

