/**
 *    Copyright 2009-2015 the original author or authors.
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
package com.luo.ibatis.submitted.integer_enum;

import java.io.Reader;

import com.luo.ibatis.BaseDataTest;
import com.luo.ibatis.io.Resources;
import com.luo.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class IntegerEnumTest extends BaseDataTest {

  @Test
  public void shouldParseMapWithIntegerJdbcType() throws Exception {
    String resource = "org/apache/ibatis/submitted/integer_enum/MapperConfig.xml";
    Reader reader = Resources.getResourceAsReader(resource);
    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
    builder.build(reader);
  }

}
