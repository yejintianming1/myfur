package com.wu.fur.es.boot.groovy

import com.alibaba.fastjson.JSON
import com.wu.fur.es.boot.client.EsApi
import com.wu.fur.es.boot.client.EsPage
import com.wu.fur.es.boot.client.EsPagination
import com.wu.fur.es.boot.model.User
import org.springframework.beans.factory.annotation.Autowired

class Foo {

    @Autowired
    private EsApi esApi;

    Object run() {
        // do something
        User u = new User();
        u.setName("小明");
        u.setPhone("18297767423");
        EsPagination<User> userEsPagination = esApi.search(u, null, null, null, null, null, new EsPage(1,100), User.class);
        System.out.println(userEsPagination.getData());
        return JSON.toJSONString(userEsPagination.getData());
    }
}
