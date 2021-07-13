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
package com.luo.ibatis.submitted.xml_external_ref;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import com.luo.ibatis.BaseDataTest;
import com.luo.ibatis.datasource.unpooled.UnpooledDataSource;
import com.luo.ibatis.io.Resources;
import com.luo.ibatis.mapping.Environment;
import com.luo.ibatis.session.Configuration;
import com.luo.ibatis.session.SqlSession;
import com.luo.ibatis.session.SqlSessionFactory;
import com.luo.ibatis.session.SqlSessionFactoryBuilder;
import com.luo.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

public class ReverseIncludeTest {

  @Test
  public void testReverseIncludeXmlConfig() throws Exception {
    testReverseIncludes(getSqlSessionFactoryXmlConfig());
  }

  @Test
  public void testReverseIncludeJavaConfig() throws Exception {
    testReverseIncludes(getSqlSessionFactoryJavaConfig());
  }

  private void testReverseIncludes(SqlSessionFactory sqlSessionFactory) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      ReverseIncludePersonMapper personMapper = sqlSession.getMapper(ReverseIncludePersonMapper.class);
      Person person = personMapper.select(1);
      assertEquals((Integer)1, person.getId());
      assertEquals("John", person.getName());
    }
  }

  private SqlSessionFactory getSqlSessionFactoryXmlConfig() throws Exception {
    try (Reader configReader = Resources
        .getResourceAsReader("org/apache/ibatis/submitted/xml_external_ref/ReverseIncludeMapperConfig.xml")) {
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configReader);

      initDb(sqlSessionFactory);

      return sqlSessionFactory;
    }
  }

  private SqlSessionFactory getSqlSessionFactoryJavaConfig() throws Exception {
    Configuration configuration = new Configuration();
    Environment environment = new Environment("development", new JdbcTransactionFactory(), new UnpooledDataSource(
        "org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:xmlextref", null));
    configuration.setEnvironment(environment);
    configuration.addMapper(ReverseIncludePersonMapper.class);

    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    initDb(sqlSessionFactory);

    return sqlSessionFactory;
  }

  private static void initDb(SqlSessionFactory sqlSessionFactory) throws IOException, SQLException {
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
            "org/apache/ibatis/submitted/xml_external_ref/CreateDB.sql");
  }

}