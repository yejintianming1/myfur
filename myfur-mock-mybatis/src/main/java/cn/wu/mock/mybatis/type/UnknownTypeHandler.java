package cn.wu.mock.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnknownTypeHandler extends BaseTypeHandler<Object> {

    private static final ObjectTypeHandler OBJECT_TYPE_HANDLER = new ObjectTypeHandler();

    private TypeHandlerRegistry typeHandlerRegistry;

    public UnknownTypeHandler(TypeHandlerRegistry typeHandlerRegistry) {
        this.typeHandlerRegistry = typeHandlerRegistry;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        TypeHandler handler = resolveTypeHandler(parameter, jdbcType);
        handler.setParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }

    private TypeHandler resolveTypeHandler(Object parameter, JdbcType jdbcType) {
        TypeHandler<?> handler;
        if (parameter == null) {
            handler = OBJECT_TYPE_HANDLER;
        } else {
            handler = typeHandlerRegistry.getTypeHandler(parameter.getClass(), jdbcType);//根据参数类型和jdbcType获取类型处理器
        }
    }


}
