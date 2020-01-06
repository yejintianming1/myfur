package test;

import com.wu.mock.spring.mybatis.TestFactoryBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFactoryBeanTest {

    @Test
    public void test() {
        ApplicationContext app = new ClassPathXmlApplicationContext("test/spring-test-config.xml");
//        TestFactoryBean testFactoryBean = (TestFactoryBean) app.getBean("testFactoryBean");

    }
}
