package com.wu.myes.session;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClientBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * es session
 */
public class MyEsSession implements EsSession {

    private final EsSession esSessionProxy;

    private final EsSession esSession;

    public MyEsSession(RestClientBuilder builder) {
        esSessionProxy = (EsSession) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{EsSession.class},new EsSessionInvocationHandler());
        esSession = new DefaultEsSession(builder);

    }

    public <T> SearchResponse search(T t) throws Exception {
        return esSessionProxy.search(t);
    }

    public <T> List<T> searchT(T t) throws Exception {
        return esSessionProxy.searchT(t);
    }

    public class EsSessionInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //调用代理对象的方法时会执行此方法
            //调用DefaultEsSession
            //TODO 执行公共逻辑，例如
            Object result = method.invoke(esSession, args);
            return result;
        }
    }
}
