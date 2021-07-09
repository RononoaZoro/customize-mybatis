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
package com.luo.ibatis.submitted.named_constructor_args;

import static org.junit.Assert.*;

import java.io.Reader;

import com.luo.ibatis.BaseDataTest;
import com.luo.ibatis.io.Resources;
import com.luo.ibatis.session.Configuration;
import com.luo.ibatis.session.SqlSession;
import com.luo.ibatis.session.SqlSessionFactory;
import com.luo.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class NamedConstructorArgsTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void setUp() throws Exception {
    // create an SqlSessionFactory
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/named_constructor_args/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    Configuration configuration = sqlSessionFactory.getConfiguration();
    configuration.setUseActualParamName(false);
    configuration.addMapper(Mapper.class);

    // populate in-memory database
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
            "org/apache/ibatis/submitted/named_constructor_args/CreateDB.sql");
  }

  @Test
  public void argsWithParamAnnos() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      User user = mapper.mapConstructorWithParamAnnos(1);
      assertEquals(Integer.valueOf(1), user.getId());
      assertEquals("User1", user.getName());
      assertEquals(Long.valueOf(99L), user.getTeam());
    }
  }

  @Test
  public void argsWithParamAnnosXml() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      User user = mapper.mapConstructorWithParamAnnosXml(1);
      assertEquals(Integer.valueOf(1), user.getId());
      assertEquals("User1", user.getName());
      assertEquals(Long.valueOf(99L), user.getTeam());
    }
  }

}
