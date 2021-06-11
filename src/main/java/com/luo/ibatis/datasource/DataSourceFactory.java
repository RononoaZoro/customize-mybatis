package com.luo.ibatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author ：archer
 * @date ：Created in 2021/6/9 16:43
 * @description：数据源工厂
 * @modified By：
 */
public interface DataSourceFactory {

    void setProperties(Properties props);

    DataSource getDataSource();
}
