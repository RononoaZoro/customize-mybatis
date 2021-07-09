/**
 *    Copyright 2009-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.luo.ibatis.submitted.xml_references;

import java.io.Reader;
import java.util.Properties;

import com.luo.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.luo.ibatis.io.Resources;
import com.luo.ibatis.mapping.Environment;
import com.luo.ibatis.session.Configuration;
import com.luo.ibatis.session.SqlSessionFactory;
import com.luo.ibatis.session.SqlSessionFactoryBuilder;
import com.luo.ibatis.session.defaults.DefaultSqlSessionFactory;
import com.luo.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

public class EnumWithOgnlTest {
    
    @Test
    public void testConfiguration() {
        UnpooledDataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        Properties dataSourceProperties = new Properties();
        dataSourceProperties.put("driver", "org.hsqldb.jdbcDriver");
        dataSourceProperties.put("url", "jdbc:hsqldb:mem:xml_references");
        dataSourceProperties.put("username", "sa");
        dataSourceFactory.setProperties(dataSourceProperties);
        Environment environment = new Environment("test", new JdbcTransactionFactory(), dataSourceFactory.getDataSource());
        Configuration configuration = new Configuration();
        configuration.setEnvironment(environment);
        configuration.getTypeAliasRegistry().registerAlias(Person.class);
        configuration.addMapper(PersonMapper.class);
        configuration.addMapper(PersonMapper2.class);
        new DefaultSqlSessionFactory(configuration);
    }
    @Test
    public void testMixedConfiguration() throws Exception {
      try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/xml_references/ibatisConfig.xml")) {
          SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
          sqlSessionFactory.getConfiguration().addMapper(PersonMapper2.class);
      }
    }
}
