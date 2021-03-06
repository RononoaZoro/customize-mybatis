/**
 *    Copyright 2009-2016 the original author or authors.
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
package com.luo.ibatis.submitted.cache;

import com.luo.ibatis.annotations.CacheNamespaceRef;
import com.luo.ibatis.annotations.Options;
import com.luo.ibatis.annotations.Options.FlushCachePolicy;
import com.luo.ibatis.annotations.Select;

import java.util.List;

@CacheNamespaceRef(PersonMapper.class) // by type
public interface ImportantPersonMapper {

  @Select("select id, firstname, lastname from person")
  @Options(flushCache = FlushCachePolicy.TRUE)
  List<Person> findWithFlushCache();

}
