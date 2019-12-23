package cn.wu.mock.mybatis.datasource.pooled;

import cn.wu.mock.mybatis.datasource.unpooled.UnpooledDataSourceFactory;

public class PooledDataSourceFactory extends UnpooledDataSourceFactory {

    public PooledDataSourceFactory() {
        this.dataSource = new PooledDataSource();
    }
}
