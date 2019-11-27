package com.wu.fur.es.boot.client;

import lombok.Data;

import java.util.List;

@Data
public class EsPagination<T> {

    private List<T> data;
    private EsPage page;

}
