package cn.wu.mock.mybatis.exceptions;

import cn.wu.mock.mybatis.executor.ErrorContext;

public class ExceptionFactory {

    private ExceptionFactory() {
    }

    public static RuntimeException wrapException(String message, Exception e) {
        return new PersistenceException(ErrorContext.instance().message(message).cause(e).toString(), e);
    }
}
