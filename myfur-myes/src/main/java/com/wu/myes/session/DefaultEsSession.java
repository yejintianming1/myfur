package com.wu.myes.session;

import com.wu.myes.client.MyRestHighLevelClient;
import com.wu.myes.resp.ResponseProcessor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.lang.reflect.Method;
import java.util.List;

public class DefaultEsSession implements EsSession {

    private RestHighLevelClient originClient;

    private RestHighLevelClient clientProxy;//持有一个RestHigLevelClient

    private final ResponseProcessor responseProcessor = new ResponseProcessor();


    public DefaultEsSession(RestClientBuilder builder) {
        this.originClient = new MyRestHighLevelClient(builder);
        RestHighLevelClientMethodInterceptor methodInterceptor = new RestHighLevelClientMethodInterceptor();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MyRestHighLevelClient.class);
        enhancer.setCallback(methodInterceptor);
        this.clientProxy = (RestHighLevelClient) enhancer.create(new Class[]{RestClientBuilder.class},new Object[]{builder});
    }

    public <T> SearchResponse search(T t) throws Exception {
        //在这里我们可以对方法进行增强
        //添加路由
        SearchRequest searchRequest = new SearchRequest("nabs_user_info0");
        SearchResponse search = clientProxy.search(searchRequest, RequestOptions.DEFAULT);

        return search;
    }

    public <T> List<T> searchT(T t) throws Exception {
        if (MyRestHighLevelClient.class.isAssignableFrom(clientProxy.getClass())) {
            MyRestHighLevelClient myRestHighLevelClient = (MyRestHighLevelClient) clientProxy;
            List<T> search = myRestHighLevelClient.search(t);
            return search;
        }
        return null;
    }

    class RestHighLevelClientMethodInterceptor implements MethodInterceptor {

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object o = methodProxy.invokeSuper(obj, args);
            return responseProcessor.process(o);
        }
    }


}
