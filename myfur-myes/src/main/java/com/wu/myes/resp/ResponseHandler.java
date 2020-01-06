package com.wu.myes.resp;

public abstract class ResponseHandler<T> {

    protected T t;//持有的源response

    public ResponseHandler(T t) {
        this.t = t;
    }

    public abstract Object response();


}
