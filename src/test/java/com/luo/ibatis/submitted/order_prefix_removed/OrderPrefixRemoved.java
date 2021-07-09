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
package com.luo.ibatis.submitted.order_prefix_removed;

import static org.junit.Assert.assertNotNull;

import java.io.Reader;

import com.luo.ibatis.BaseDataTest;
import com.luo.ibatis.io.Resources;
import com.luo.ibatis.session.ExecutorType;
import com.luo.ibatis.session.SqlSession;
import com.luo.ibatis.session.SqlSessionFactory;
import com.luo.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class OrderPrefixRemoved {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void initDatabase() throws Exception {
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/order_prefix_removed/ibatisConfig.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
            "org/apache/ibatis/submitted/order_prefix_removed/CreateDB.sql");
  }

  @Test
  public void testOrderPrefixNotRemoved() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE)) {
      PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

      Person person = personMapper.select(new String("slow"));

      assertNotNull(person);
      
      sqlSession.commit();
    }
  }
}
