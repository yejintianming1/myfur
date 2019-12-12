import junit.framework.TestCase;
import org.apache.ibatis.parsing.PropertyParser;
import org.junit.Test;

import java.util.Properties;

public class MyBatisTest extends TestCase {

    @Test
    public void testPropertyParser() {
        Properties properties = new Properties();
        properties.setProperty("name","我是小王");
        properties.setProperty("age","12");
        String result = PropertyParser.parse("${name}", properties);
        System.out.println(result);
    }
}
