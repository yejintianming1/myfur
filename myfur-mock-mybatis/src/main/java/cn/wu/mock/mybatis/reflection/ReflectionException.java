package cn.wu.mock.mybatis.reflection;

import cn.wu.mock.mybatis.exceptions.PersistenceException;

public class ReflectionException extends PersistenceException {
    private static final long serialVersionUID = 7642570221267566591L;

    public ReflectionException() {
        super();
    }

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(Throwable cause) {
        super(cause);
    }
}
