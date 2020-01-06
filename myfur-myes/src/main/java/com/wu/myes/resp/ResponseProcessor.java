package com.wu.myes.resp;

import org.elasticsearch.action.search.SearchResponse;

public class ResponseProcessor {

    public <T> Object process(T resp) {
        //进行类型类型探测
        ResponseHandler responseHandler = new ResponseHandlerFind<T>(resp).findCandidate();
        return responseHandler.response();
    }


    class ResponseHandlerFind<T> {

        private ResponseHandler responseHandler;

        public ResponseHandlerFind(T resp) {
            if (resp instanceof SearchResponse) {//类型探测
                responseHandler = new SearchResponseHandler((SearchResponse) resp);
            } else {
                responseHandler = new OriginResponseHandler(resp);
            }
        }

        public ResponseHandler findCandidate() {
            return this.responseHandler;
        }
    }
}
