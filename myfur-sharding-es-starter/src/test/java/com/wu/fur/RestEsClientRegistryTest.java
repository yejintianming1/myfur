package com.wu.fur;

import com.wu.fur.es.boot.MainApplication;
import com.wu.fur.es.boot.client.EsApi;
import com.wu.fur.es.boot.client.EsPage;
import com.wu.fur.es.boot.client.EsPagination;
import com.wu.fur.es.boot.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= MainApplication.class)// 指定spring-boot的启动类 
public class RestEsClientRegistryTest {

//    @Autowired
//    private EsClientRegistry esClient;

    @Autowired
    private EsApi esApi;

    @Test
    public void test() throws Exception {

//        esApi.process();

        User u = new User();
        u.setName("小明");
        u.setPhone("18297767423");
        EsPagination<User> userEsPagination = esApi.search(u, null, null, null, null, null, new EsPage(1,100), User.class);
        System.out.println(userEsPagination.getData());

//        esClient.say();
//        System.out.println("111");
    }

}
