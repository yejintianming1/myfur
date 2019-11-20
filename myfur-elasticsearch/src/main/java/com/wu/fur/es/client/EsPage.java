package com.wu.fur.es.client;

import lombok.Data;

@Data
public class EsPage {

    private int currentPage = 1;//页数从1开始
    private int pageSize = 100;

    public EsPage() {
    }

    public EsPage(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getFrom() {
        if (currentPage < 1) currentPage = 1;
        return (currentPage - 1) * 100;
    }

}
