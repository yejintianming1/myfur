package com.wu.myes.resp;

public class OriginResponseHandler extends ResponseHandler {

    public OriginResponseHandler(Object object) {
        super(object);
    }

    public Object response() {
        return t;
    }

}
