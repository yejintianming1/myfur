package com.wu.fur.es;

import com.wu.fur.es.client.EsPage;
import com.wu.fur.es.client.EsPagination;
import com.wu.fur.es.client.RestEsClient;
import com.wu.fur.es.mapping.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=MainApplication.class)// 指定spring-boot的启动类 
public class RestEsClientTest{

    @Autowired
    private RestEsClient restEsClient1;

    @Autowired
    private RestEsClient restEsClient2;

//    @Autowired
//    private RestEsClient restEsClient;


    @Test
    public void test1() throws IOException {
//        restEsClient1.getDocHockey();
//        restEsClient2.createIndex(User.class);
        User u = new User();
//        u.setName("小明");
        u.setAge("28");
//        restEsClient2.indexDoc(u);
        EsPagination<User> userEsPagination = restEsClient2.search(u, null, null, null, null, null, new EsPage(1,100), User.class);
        System.out.println(userEsPagination.getData());

    }
}
