package cn.wu.mock.mybatis.mapping;

import java.sql.ResultSet;

public enum ResultSetType {

    DEFAULT(-1),
    FORWARD_ONLY(ResultSet.TYPE_FORWARD_ONLY),//结果集的游标只能向下滚动
    SCROLL_INSENSITIVE(ResultSet.TYPE_SCROLL_INSENSITIVE),//结果集的游标可以上下移动，当数据库变化时，当前结果集不变
    SCROLL_SENSITIVE(ResultSet.TYPE_SCROLL_SENSITIVE);//返回可滚动的结果集，当数据库变化时，当前结果集同步改变

    private final int value;

    ResultSetType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
