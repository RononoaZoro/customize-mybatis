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
package com.luo.ibatis.submitted.generictypes;

import java.io.Reader;

import com.luo.ibatis.BaseDataTest;
import org.junit.Assert;

import com.luo.ibatis.io.Resources;
import com.luo.ibatis.session.SqlSession;
import com.luo.ibatis.session.SqlSessionFactory;
import com.luo.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

public class GenericTypesTest {

  private SqlSessionFactory sqlSessionFactory;

  @Before
  public void setUp() throws Exception {
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/generictypes/Config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
            "org/apache/ibatis/submitted/generictypes/CreateDB.sql");
  }

  @Test
  public void testShouldGetAListOfMaps() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Group group = mapper.getGroup();
      Assert.assertNotNull(group.getOwner());
    }
  }

}
