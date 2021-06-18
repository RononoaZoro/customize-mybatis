package com.luo.ibatis.mapping;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ：archer
 * @date ：Created in 2021/6/17 20:46
 * @description：
 * Should return an id to identify the type of this database.
 * That id can be used later on to build different queries for each database type
 * This mechanism enables supporting multiple vendors or versions
 */
public interface DatabaseIdProvider {

    void setProperties(Properties p);

    String getDatabaseId(DataSource dataSource) throws SQLException;
}
