package com.luo.ibatis.scripting.xmltags;

import com.luo.ibatis.reflection.Reflector;
import ognl.MemberAccess;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.util.Map;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 17:02
 * @description：
 * The {@link MemberAccess} class that based on <a href=
 * 'https://github.com/jkuhnert/ognl/blob/OGNL_3_2_1/src/java/ognl/DefaultMemberAccess.java'>DefaultMemberAccess</a>.
 *
 * @author Kazuki Shimizu
 * @since 3.5.0
 *
 * @see <a href=
 *      'https://github.com/jkuhnert/ognl/blob/OGNL_3_2_1/src/java/ognl/DefaultMemberAccess.java'>DefaultMemberAccess</a>
 * @see <a href='https://github.com/jkuhnert/ognl/issues/47'>#47 of ognl</a>
 */
public class OgnlMemberAccess implements MemberAccess {

    private final boolean canControlMemberAccessible;

    OgnlMemberAccess() {
        this.canControlMemberAccessible = Reflector.canControlMemberAccessible();
    }

    @Override
    public Object setup(Map context, Object target, Member member, String propertyName) {
        Object result = null;
        if (isAccessible(context, target, member, propertyName)) {
            AccessibleObject accessible = (AccessibleObject) member;
            if (!accessible.isAccessible()) {
                result = Boolean.FALSE;
                accessible.setAccessible(true);
            }
        }
        return result;
    }

    @Override
    public void restore(Map context, Object target, Member member, String propertyName,
                        Object state) {
        if (state != null) {
            ((AccessibleObject) member).setAccessible(((Boolean) state));
        }
    }

    @Override
    public boolean isAccessible(Map context, Object target, Member member, String propertyName) {
        return canControlMemberAccessible;
    }

}
