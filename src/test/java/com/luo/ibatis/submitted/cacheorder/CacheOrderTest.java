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
package com.luo.ibatis.submitted.cacheorder;

import static org.junit.Assert.assertEquals;

import java.io.Reader;

import com.luo.ibatis.BaseDataTest;
import com.luo.ibatis.cache.Cache;
import com.luo.ibatis.io.Resources;
import com.luo.ibatis.mapping.MappedStatement;
import com.luo.ibatis.session.SqlSessionFactory;
import com.luo.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class CacheOrderTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void setUp() throws Exception {
    // create a SqlSessionFactory
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/cacheorder/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    // populate in-memory database
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
            "org/apache/ibatis/submitted/cacheorder/CreateDB.sql");
  }

  @Test
  public void shouldResolveACacheRefNotYetRead() {
    MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement("getUser");
    Cache cache = ms.getCache();
    assertEquals("org.apache.ibatis.submitted.cacheorder.Mapper2", cache.getId());
  }

}
