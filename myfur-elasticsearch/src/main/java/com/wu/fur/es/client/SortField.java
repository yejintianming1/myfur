package com.wu.fur.es.client;

import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;

@Data
public class SortField {

    private String name;
    private SortOrder order;

}
