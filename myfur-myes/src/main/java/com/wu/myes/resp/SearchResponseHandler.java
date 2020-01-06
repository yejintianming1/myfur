package com.wu.myes.resp;

import com.wu.myes.search.MySearchResponse;
import org.elasticsearch.action.search.SearchResponse;

public class SearchResponseHandler extends ResponseHandler<SearchResponse> {

    public SearchResponseHandler(SearchResponse searchResponse) {
        super(searchResponse);
    }

    public Object response() {
        return new MySearchResponse((SearchResponse)t);
    }

}
