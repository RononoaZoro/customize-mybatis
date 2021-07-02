package com.luo.ibatis.scripting.xmltags;

import com.luo.ibatis.io.Resources;
import ognl.DefaultClassResolver;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 17:01
 * @description：
 *
 * Custom ognl {@code ClassResolver} which behaves same like ognl's
 * {@code DefaultClassResolver}. But uses the {@code Resources}
 * utility class to find the target class instead of {@code Class#forName(String)}.
 *
 * @author Daniel Guggi
 *
 * @see <a href='https://github.com/mybatis/mybatis-3/issues/161'>Issue 161</a>
 */
public class OgnlClassResolver extends DefaultClassResolver {

    @Override
    protected Class toClassForName(String className) throws ClassNotFoundException {
        return Resources.classForName(className);
    }

}
