package com.wu.fur.es.boot.controller;

import com.wu.fur.es.boot.utils.SpringContextUtils;
import groovy.lang.GroovyClassLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

@RestController
public class GroovyController {

    @RequestMapping("/runScript")
    public Object runScript(String script) throws Exception {
        if (script != null) {
            // 这里其实就是groovy的api动态的加载生成一个Class，然后反射生成对象，然后执行run方法，最后返回结果
            // 最精华的地方就是SpringContextUtils.autowireBean，可以实现自动注入bean，
            Class clazz = new GroovyClassLoader().parseClass(script);
            Method run = clazz.getMethod("run");
            Object o = clazz.newInstance();
            SpringContextUtils.autowireBean(o);
            Object ret = run.invoke(o);
            return ret;
        } else {
            return "no script";
        }
    }
}
