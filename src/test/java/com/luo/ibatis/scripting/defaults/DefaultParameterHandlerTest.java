/**
 *    Copyright 2009-2017 the original author or authors.
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
package com.luo.ibatis.scripting.defaults;

import com.luo.ibatis.builder.StaticSqlSource;
import com.luo.ibatis.mapping.*;
import com.luo.ibatis.session.Configuration;
import com.luo.ibatis.type.JdbcType;
import com.luo.ibatis.type.TypeException;
import com.luo.ibatis.type.TypeHandler;
import com.luo.ibatis.type.TypeHandlerRegistry;
import org.junit.Assert;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * DefaultParameterHandlerTest
 *
 * @author Ryan Lamore
 */
public class DefaultParameterHandlerTest {

  @Test
  public void setParametersThrowsProperException() throws SQLException {
    final MappedStatement mappedStatement = getMappedStatement();
    final Object parameterObject = null;
    final BoundSql boundSql = mock(BoundSql.class);

    TypeHandler<Object> typeHandler = mock(TypeHandler.class);
    doThrow(new SQLException("foo")).when(typeHandler).setParameter(any(PreparedStatement.class), anyInt(), any(), any(JdbcType.class));
    ParameterMapping parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "prop", typeHandler).build();
    List<ParameterMapping> parameterMappings = Collections.singletonList(parameterMapping);
    when(boundSql.getParameterMappings()).thenReturn(parameterMappings);

    DefaultParameterHandler defaultParameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);

    PreparedStatement ps = mock(PreparedStatement.class);
    try {
      defaultParameterHandler.setParameters(ps);
      Assert.fail("Should have thrown TypeException");
    } catch (Exception e) {
      Assert.assertTrue("expected TypeException", e instanceof TypeException);
      Assert.assertTrue("", e.getMessage().contains("mapping: ParameterMapping"));
    }

  }

  MappedStatement getMappedStatement() {
    final Configuration config = new Configuration();
    final TypeHandlerRegistry registry = config.getTypeHandlerRegistry();
    return new MappedStatement.Builder(config, "testSelect", new StaticSqlSource(config, "some select statement"), SqlCommandType.SELECT).resultMaps(
        new ArrayList<ResultMap>() {
          {
            add(new ResultMap.Builder(config, "testMap", HashMap.class, new ArrayList<ResultMapping>() {
              {
                add(new ResultMapping.Builder(config, "cOlUmN1", "CoLuMn1", registry.getTypeHandler(Integer.class)).build());
              }
            }).build());
          }
        }).build();
  }

}
