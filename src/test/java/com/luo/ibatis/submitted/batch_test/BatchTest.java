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
package com.luo.ibatis.submitted.batch_test;

import com.luo.ibatis.BaseDataTest;
import com.luo.ibatis.io.Resources;
import com.luo.ibatis.session.ExecutorType;
import com.luo.ibatis.session.SqlSession;
import com.luo.ibatis.session.SqlSessionFactory;
import com.luo.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;

public class BatchTest
{

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void setUp() throws Exception {
    // create an SqlSessionFactory
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/batch_test/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    // populate in-memory database
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
            "org/apache/ibatis/submitted/batch_test/CreateDB.sql");
  }

  @Test
  public void shouldGetAUserNoException() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false)) {
      try {
        Mapper mapper = sqlSession.getMapper(Mapper.class);

        User user = mapper.getUser(1);

        user.setId(2);
        user.setName("User2");
        mapper.insertUser(user);
        Assert.assertEquals("Dept1", mapper.getUser(2).getDept().getName());
      } finally {
        sqlSession.commit();
      }
    }
    catch (Exception e)
    {
      Assert.fail(e.getMessage());

    }
  }



}
