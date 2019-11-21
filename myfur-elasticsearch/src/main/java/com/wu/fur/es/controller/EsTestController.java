package com.wu.fur.es.controller;

import com.wu.fur.es.client.RestEsClient;
import com.wu.fur.es.mapping.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class EsTestController {

    @Autowired
    private RestEsClient userRestEsClient;

    @RequestMapping("/say")
    public String sayHello() {
        return "Hello Mr. 伍";
    }

    @RequestMapping("/indexDoc")
    public boolean indexDoc() throws Exception {
        User u = new User();
        u.setName("小明");
        u.setAge(28);
        return userRestEsClient.indexDoc(u);
    }
}
