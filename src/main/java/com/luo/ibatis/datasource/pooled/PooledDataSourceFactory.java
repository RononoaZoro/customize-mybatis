package com.luo.ibatis.datasource.pooled;

import com.luo.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * @author ：archer
 * @date ：Created in 2021/6/11 18:37
 * @description：池化连接池工厂
 * @modified By：
 */
public class PooledDataSourceFactory extends UnpooledDataSourceFactory {

    public PooledDataSourceFactory() {
        this.dataSource = new PooledDataSource();
    }

}
