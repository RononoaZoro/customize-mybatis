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
package com.luo.ibatis.submitted.manyanno;

import com.luo.ibatis.BaseDataTest;
import com.luo.ibatis.mapping.Environment;
import com.luo.ibatis.session.Configuration;
import com.luo.ibatis.session.SqlSession;
import com.luo.ibatis.session.SqlSessionFactory;
import com.luo.ibatis.session.SqlSessionFactoryBuilder;
import com.luo.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ManyAnnoTest extends BaseDataTest {

  @Test
  public void testGetMessageForEmptyDatabase() throws Exception {
    final Environment environment = new Environment("test", new JdbcTransactionFactory(), BaseDataTest.createBlogDataSource());
    final Configuration config = new Configuration(environment);
    config.addMapper(PostMapper.class);
    final SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(config);
    try (SqlSession session = factory.openSession()) {

      PostMapper mapper = session.getMapper(PostMapper.class);
      List<AnnoPost> posts = mapper.getPosts(101);


      assertEquals(3, posts.size());
      assertEquals(3, posts.get(0).getTags().size());
      assertEquals(1, posts.get(1).getTags().size());
      assertEquals(0, posts.get(2).getTags().size());
    }
  }

}