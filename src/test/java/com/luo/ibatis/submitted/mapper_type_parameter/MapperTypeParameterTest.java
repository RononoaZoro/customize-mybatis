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
package com.luo.ibatis.submitted.mapper_type_parameter;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.luo.ibatis.BaseDataTest;
import com.luo.ibatis.io.Resources;
import com.luo.ibatis.session.SqlSession;
import com.luo.ibatis.session.SqlSessionFactory;
import com.luo.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class MapperTypeParameterTest {
  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void setUp() throws Exception {
    // create an SqlSessionFactory
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/mapper_type_parameter/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    // populate in-memory database
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
            "org/apache/ibatis/submitted/mapper_type_parameter/CreateDB.sql");
  }

  @Test
  public void shouldResolveReturnType() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
      Person person = mapper.select(new Person(1));
      assertEquals("Jane", person.getName());
    }
  }

  @Test
  public void shouldResolveListTypeParam() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
      List<Person> persons = mapper.selectList(null);
      assertEquals(2, persons.size());
      assertEquals("Jane", persons.get(0).getName());
      assertEquals("John", persons.get(1).getName());
    }
  }

  @Test
  public void shouldResolveMultipleTypeParam() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
      Map<Long, Country> results = mapper.selectMap(new Country());
      assertEquals(2, results.size());
      assertEquals("Japan", results.get(1L).getName());
      assertEquals("New Zealand", results.get(2L).getName());
    }
  }

  @Test
  public void shouldResolveParameterizedReturnType() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      PersonListMapper mapper = sqlSession.getMapper(PersonListMapper.class);
      List<Person> persons = mapper.select(null);
      assertEquals(2, persons.size());
      assertEquals("Jane", persons.get(0).getName());
      assertEquals("John", persons.get(1).getName());
    }
  }

  @Test
  public void shouldResolveParam() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
      assertEquals(1, mapper.update(new Country(2L, "Greenland")));
    }
  }

  @Test
  public void shouldResolveListParam() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
      Person person1 = new Person("James");
      assertEquals(1, mapper.insert(Arrays.asList(person1)));
      assertNotNull(person1.getId());
    }
  }
}
