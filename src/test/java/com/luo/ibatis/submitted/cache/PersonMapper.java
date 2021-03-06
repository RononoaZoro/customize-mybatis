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

import java.util.List;

import com.luo.ibatis.annotations.CacheNamespace;
import com.luo.ibatis.annotations.Delete;
import com.luo.ibatis.annotations.Insert;
import com.luo.ibatis.annotations.Options;
import com.luo.ibatis.annotations.Options.FlushCachePolicy;
import com.luo.ibatis.annotations.Select;

@CacheNamespace
public interface PersonMapper {

  @Insert("insert into person (id, firstname, lastname) values (#{id}, #{firstname}, #{lastname})")
  public void create(Person person);

  @Insert("insert into person (id, firstname, lastname) values (#{id}, #{firstname}, #{lastname})")
  @Options
  public void createWithOptions(Person person);

  @Insert("insert into person (id, firstname, lastname) values (#{id}, #{firstname}, #{lastname})")
  @Options(flushCache = FlushCachePolicy.FALSE)
  public void createWithoutFlushCache(Person person);

  @Delete("delete from person where id = #{id}")
  public void delete(int id);

  @Select("select id, firstname, lastname from person")
  public List<Person> findAll();

  @Select("select id, firstname, lastname from person")
  @Options(flushCache = FlushCachePolicy.TRUE)
  public List<Person> findWithFlushCache();
}
