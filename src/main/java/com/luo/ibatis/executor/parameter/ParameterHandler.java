package com.luo.ibatis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 10:35
 * @description：
 */
public interface ParameterHandler {

    Object getParameterObject();

    void setParameters(PreparedStatement ps)
            throws SQLException;
}
