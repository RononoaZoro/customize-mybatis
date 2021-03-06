package com.luo.ibatis.executor.keygen;

import com.luo.ibatis.executor.Executor;
import com.luo.ibatis.executor.ExecutorException;
import com.luo.ibatis.mapping.MappedStatement;
import com.luo.ibatis.reflection.MetaObject;
import com.luo.ibatis.session.Configuration;
import com.luo.ibatis.type.JdbcType;
import com.luo.ibatis.type.TypeHandler;
import com.luo.ibatis.type.TypeHandlerRegistry;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:04
 * @description：
 */
public class Jdbc3KeyGenerator  implements KeyGenerator {

    /**
     * A shared instance.
     * @since 3.4.3
     */
    public static final Jdbc3KeyGenerator INSTANCE = new Jdbc3KeyGenerator();

    @Override
    public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        // do nothing
    }

    @Override
    public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        processBatch(ms, stmt, getParameters(parameter));
    }

    public void processBatch(MappedStatement ms, Statement stmt, Collection<Object> parameters) {
        ResultSet rs = null;
        try {
            rs = stmt.getGeneratedKeys();
            final Configuration configuration = ms.getConfiguration();
            final TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            final String[] keyProperties = ms.getKeyProperties();
            final ResultSetMetaData rsmd = rs.getMetaData();
            TypeHandler<?>[] typeHandlers = null;
            if (keyProperties != null && rsmd.getColumnCount() >= keyProperties.length) {
                for (Object parameter : parameters) {
                    // there should be one row for each statement (also one for each parameter)
                    if (!rs.next()) {
                        break;
                    }
                    final MetaObject metaParam = configuration.newMetaObject(parameter);
                    if (typeHandlers == null) {
                        typeHandlers = getTypeHandlers(typeHandlerRegistry, metaParam, keyProperties, rsmd);
                    }
                    populateKeys(rs, metaParam, keyProperties, typeHandlers);
                }
            }
        } catch (Exception e) {
            throw new ExecutorException("Error getting generated key or setting result to parameter object. Cause: " + e, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }

    private Collection<Object> getParameters(Object parameter) {
        Collection<Object> parameters = null;
        if (parameter instanceof Collection) {
            parameters = (Collection) parameter;
        } else if (parameter instanceof Map) {
            Map parameterMap = (Map) parameter;
            if (parameterMap.containsKey("collection")) {
                parameters = (Collection) parameterMap.get("collection");
            } else if (parameterMap.containsKey("list")) {
                parameters = (List) parameterMap.get("list");
            } else if (parameterMap.containsKey("array")) {
                parameters = Arrays.asList((Object[]) parameterMap.get("array"));
            }
        }
        if (parameters == null) {
            parameters = new ArrayList<Object>();
            parameters.add(parameter);
        }
        return parameters;
    }

    private TypeHandler<?>[] getTypeHandlers(TypeHandlerRegistry typeHandlerRegistry, MetaObject metaParam, String[] keyProperties, ResultSetMetaData rsmd) throws SQLException {
        TypeHandler<?>[] typeHandlers = new TypeHandler<?>[keyProperties.length];
        for (int i = 0; i < keyProperties.length; i++) {
            if (metaParam.hasSetter(keyProperties[i])) {
                Class<?> keyPropertyType = metaParam.getSetterType(keyProperties[i]);
                typeHandlers[i] = typeHandlerRegistry.getTypeHandler(keyPropertyType, JdbcType.forCode(rsmd.getColumnType(i + 1)));
            } else {
                throw new ExecutorException("No setter found for the keyProperty '" + keyProperties[i] + "' in '"
                        + metaParam.getOriginalObject().getClass().getName() + "'.");
            }
        }
        return typeHandlers;
    }

    private void populateKeys(ResultSet rs, MetaObject metaParam, String[] keyProperties, TypeHandler<?>[] typeHandlers) throws SQLException {
        for (int i = 0; i < keyProperties.length; i++) {
            String property = keyProperties[i];
            TypeHandler<?> th = typeHandlers[i];
            if (th != null) {
                Object value = th.getResult(rs, i + 1);
                metaParam.setValue(property, value);
            }
        }
    }

}
