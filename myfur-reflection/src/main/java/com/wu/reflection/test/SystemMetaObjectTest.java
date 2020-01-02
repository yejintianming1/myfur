package com.wu.reflection.test;

import com.wu.reflection.DefaultReflectorFactory;
import com.wu.reflection.MetaObject;
import com.wu.reflection.factory.DefaultObjectFactory;
import com.wu.reflection.test.entity.Say;
import com.wu.reflection.test.entity.User;
import com.wu.reflection.wrapper.DefaultObjectWrapperFactory;

import javax.jws.soap.SOAPBinding;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class SystemMetaObjectTest {

//    public static void main(String[] args) {
//        User user = new User();
//
//        MetaObject metaObject = SystemMetaObject.forObject(user);
//
//        boolean hasAge = metaObject.hasGetter("age");//判断某个属性是否存在
//        boolean hasIsRich = metaObject.hasGetter("isRich");//判断某个属性是否存在
//        System.out.println("hasAge:"+hasAge);
//        System.out.println("hasIsRich:"+hasIsRich);
//
//        Object age = metaObject.getValue("age");
//        System.out.println("age value:"+age);
//
//        metaObject.setValue("isRich", true);
//        Object isRich = metaObject.getValue("isRich");
//        System.out.println("isRich value:"+isRich);
//
//    }

    public static void main(String[] args) {

//        User u = new User();
//        Method[] declaredMethods = u.getClass().getDeclaredMethods();
//        for (Method m: declaredMethods
//             ) {
////            if ()
////            Object invoke = m.invoke(u, );
//        }

        //获取Class类的方法并生成方法签名
        Method[] methods = User.class.getDeclaredMethods();
        Map<String, Method> uniqueMethods = new HashMap<>();//方法签名和方法
        for (Method m: methods
             ) {
            if (!m.isBridge()) {//只针对非桥接方法处理
                String signature = getSignature(m);//方法签名
                uniqueMethods.put(signature,m);
            }
        }

        //

        //根据实际的方法调用，选择具体的方法调用
        Properties props = new Properties();
        props.put("name","小王");
//        props.setProperty("name","小王");
//        props.setProperty("age", "29");
        props.put("age", 29);
        props.put("sex", "MALE");
//        props.setProperty("sex", "MALE");
        props.put("isRich", true);
//        props.setProperty("isRich", "true");
        Say user = new User();
//        Iterator<Map.Entry<Object, Object>> iterator = props.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Object, Object> next = iterator.next();
//            Object key = next.getKey();
//            Object value = next.getValue();
//            System.out.println("key:"+key+",value:"+value);
//
//            //找出用哪个方法
//
//
//        }

        user.say(props);
//
        System.out.println(user);
//        MetaObject metaObject = MetaObject.forObject(user, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());


    }

    private static String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            sb.append(returnType.getName()).append('#');
        }
        sb.append(method.getName());
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            sb.append(i == 0 ? ':' : ',').append(parameters[i].getName());
        }
        return sb.toString();
    }

}
