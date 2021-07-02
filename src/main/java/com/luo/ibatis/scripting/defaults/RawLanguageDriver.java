package com.luo.ibatis.scripting.defaults;

import com.luo.ibatis.builder.BuilderException;
import com.luo.ibatis.mapping.SqlSource;
import com.luo.ibatis.parsing.XNode;
import com.luo.ibatis.scripting.xmltags.XMLLanguageDriver;
import com.luo.ibatis.session.Configuration;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:49
 * @description：
 * As of 3.2.4 the default XML language is able to identify static statements
 * and create a {@link RawSqlSource}. So there is no need to use RAW unless you
 * want to make sure that there is not any dynamic tag for any reason.
 */
public class RawLanguageDriver extends XMLLanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        SqlSource source = super.createSqlSource(configuration, script, parameterType);
        checkIsNotDynamic(source);
        return source;
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        SqlSource source = super.createSqlSource(configuration, script, parameterType);
        checkIsNotDynamic(source);
        return source;
    }

    private void checkIsNotDynamic(SqlSource source) {
        if (!RawSqlSource.class.equals(source.getClass())) {
            throw new BuilderException("Dynamic content is not allowed when using RAW language");
        }
    }

}
