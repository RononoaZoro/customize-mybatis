package com.luo.ibatis.executor.loader;

import java.io.ObjectStreamException;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 14:52
 * @description：
 */
public interface WriteReplaceInterface {

    Object writeReplace() throws ObjectStreamException;

}
