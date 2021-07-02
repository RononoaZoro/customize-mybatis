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
package com.luo.ibatis.executor.loader;

import net.sf.cglib.proxy.Factory;
import com.luo.ibatis.domain.blog.Author;
import com.luo.ibatis.executor.ExecutorException;
import com.luo.ibatis.executor.loader.cglib.CglibProxyFactory;
import com.luo.ibatis.reflection.factory.DefaultObjectFactory;
import com.luo.ibatis.session.Configuration;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CglibProxyTest extends SerializableProxyTest {

  public CglibProxyTest() {
    proxyFactory = new CglibProxyFactory();
  }

  @Test
  public void shouldCreateAProxyForAPartiallyLoadedBean() throws Exception {
    ResultLoaderMap loader = new ResultLoaderMap();
    loader.addLoader("id", null, null);
    Object proxy = proxyFactory.createProxy(author, loader, new Configuration(), new DefaultObjectFactory(), new ArrayList<Class<?>>(), new ArrayList<Object>());
    Author author2 = (Author) deserialize(serialize((Serializable) proxy));
    assertTrue(author2 instanceof Factory);
  }

  @Test(expected = ExecutorException.class)
  public void shouldFailCallingAnUnloadedProperty() throws Exception {
    // yes, it must go in uppercase
    HashMap<String, ResultLoaderMap.LoadPair> unloadedProperties = new HashMap<String, ResultLoaderMap.LoadPair>();
    unloadedProperties.put("ID", null);
    Author author2 = (Author) ((CglibProxyFactory)proxyFactory).createDeserializationProxy(author, unloadedProperties, new DefaultObjectFactory(), new ArrayList<Class<?>>(), new ArrayList<Object>());
    author2.getId();
  }

  @Test
  public void shouldLetCallALoadedProperty() throws Exception {
    Author author2 = (Author) ((CglibProxyFactory)proxyFactory).createDeserializationProxy(author, new HashMap<String, ResultLoaderMap.LoadPair>(), new DefaultObjectFactory(), new ArrayList<Class<?>>(), new ArrayList<Object>());
    assertEquals(999, author2.getId());
  }

  @Test
  public void shouldSerizalizeADeserlizaliedProxy() throws Exception {
    Object proxy = ((CglibProxyFactory)proxyFactory).createDeserializationProxy(author, new HashMap<String, ResultLoaderMap.LoadPair>(), new DefaultObjectFactory(), new ArrayList<Class<?>>(), new ArrayList<Object>());
    Author author2 = (Author) deserialize(serialize((Serializable) proxy));
    assertEquals(author, author2);
    assertFalse(author.getClass().equals(author2.getClass()));
  }

}
