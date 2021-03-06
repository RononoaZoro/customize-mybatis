package com.luo.ibatis.builder.annotation;

import com.luo.ibatis.builder.BuilderException;
import com.luo.ibatis.builder.SqlSourceBuilder;
import com.luo.ibatis.mapping.BoundSql;
import com.luo.ibatis.mapping.SqlSource;
import com.luo.ibatis.parsing.PropertyParser;
import com.luo.ibatis.reflection.ParamNameResolver;
import com.luo.ibatis.session.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 12:06
 * @description：
 */
public class ProviderSqlSource implements SqlSource {

    private final Configuration configuration;
    private final SqlSourceBuilder sqlSourceParser;
    private final Class<?> providerType;
    private Method providerMethod;
    private String[] providerMethodArgumentNames;
    private Class<?>[] providerMethodParameterTypes;
    private ProviderContext providerContext;
    private Integer providerContextIndex;

    /**
     * @deprecated Please use the {@link #ProviderSqlSource(Configuration, Object, Class, Method)} instead of this.
     */
    @Deprecated
    public ProviderSqlSource(Configuration configuration, Object provider) {
        this(configuration, provider, null, null);
    }

    /**
     * @since 3.4.5
     */
    public ProviderSqlSource(Configuration configuration, Object provider, Class<?> mapperType, Method mapperMethod) {
        String providerMethodName;
        try {
            this.configuration = configuration;
            this.sqlSourceParser = new SqlSourceBuilder(configuration);
            this.providerType = (Class<?>) provider.getClass().getMethod("type").invoke(provider);
            providerMethodName = (String) provider.getClass().getMethod("method").invoke(provider);

            for (Method m : this.providerType.getMethods()) {
                if (providerMethodName.equals(m.getName()) && CharSequence.class.isAssignableFrom(m.getReturnType())) {
                    if (providerMethod != null){
                        throw new BuilderException("Error creating SqlSource for SqlProvider. Method '"
                                + providerMethodName + "' is found multiple in SqlProvider '" + this.providerType.getName()
                                + "'. Sql provider method can not overload.");
                    }
                    this.providerMethod = m;
                    this.providerMethodArgumentNames = new ParamNameResolver(configuration, m).getNames();
                    this.providerMethodParameterTypes = m.getParameterTypes();
                }
            }
        } catch (BuilderException e) {
            throw e;
        } catch (Exception e) {
            throw new BuilderException("Error creating SqlSource for SqlProvider.  Cause: " + e, e);
        }
        if (this.providerMethod == null) {
            throw new BuilderException("Error creating SqlSource for SqlProvider. Method '"
                    + providerMethodName + "' not found in SqlProvider '" + this.providerType.getName() + "'.");
        }
        for (int i = 0; i< this.providerMethodParameterTypes.length; i++) {
            Class<?> parameterType = this.providerMethodParameterTypes[i];
            if (parameterType == ProviderContext.class) {
                if (this.providerContext != null){
                    throw new BuilderException("Error creating SqlSource for SqlProvider. ProviderContext found multiple in SqlProvider method ("
                            + this.providerType.getName() + "." + providerMethod.getName()
                            + "). ProviderContext can not define multiple in SqlProvider method argument.");
                }
                this.providerContext = new ProviderContext(mapperType, mapperMethod);
                this.providerContextIndex = i;
            }
        }
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        SqlSource sqlSource = createSqlSource(parameterObject);
        return sqlSource.getBoundSql(parameterObject);
    }

    private SqlSource createSqlSource(Object parameterObject) {
        try {
            int bindParameterCount = providerMethodParameterTypes.length - (providerContext == null ? 0 : 1);
            String sql;
            if (providerMethodParameterTypes.length == 0) {
                sql = invokeProviderMethod();
            } else if (bindParameterCount == 0) {
                sql = invokeProviderMethod(providerContext);
            } else if (bindParameterCount == 1 &&
                    (parameterObject == null || providerMethodParameterTypes[(providerContextIndex == null || providerContextIndex == 1) ? 0 : 1].isAssignableFrom(parameterObject.getClass()))) {
                sql = invokeProviderMethod(extractProviderMethodArguments(parameterObject));
            } else if (parameterObject instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> params = (Map<String, Object>) parameterObject;
                sql = invokeProviderMethod(extractProviderMethodArguments(params, providerMethodArgumentNames));
            } else {
                throw new BuilderException("Error invoking SqlProvider method ("
                        + providerType.getName() + "." + providerMethod.getName()
                        + "). Cannot invoke a method that holds "
                        + (bindParameterCount == 1 ? "named argument(@Param)": "multiple arguments")
                        + " using a specifying parameterObject. In this case, please specify a 'java.util.Map' object.");
            }
            Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
            return sqlSourceParser.parse(replacePlaceholder(sql), parameterType, new HashMap<String, Object>());
        } catch (BuilderException e) {
            throw e;
        } catch (Exception e) {
            throw new BuilderException("Error invoking SqlProvider method ("
                    + providerType.getName() + "." + providerMethod.getName()
                    + ").  Cause: " + e, e);
        }
    }

    private Object[] extractProviderMethodArguments(Object parameterObject) {
        if (providerContext != null) {
            Object[] args = new Object[2];
            args[providerContextIndex == 0 ? 1 : 0] = parameterObject;
            args[providerContextIndex] = providerContext;
            return args;
        } else {
            return new Object[] { parameterObject };
        }
    }

    private Object[] extractProviderMethodArguments(Map<String, Object> params, String[] argumentNames) {
        Object[] args = new Object[argumentNames.length];
        for (int i = 0; i < args.length; i++) {
            if (providerContextIndex != null && providerContextIndex == i) {
                args[i] = providerContext;
            } else {
                args[i] = params.get(argumentNames[i]);
            }
        }
        return args;
    }

    private String invokeProviderMethod(Object... args) throws Exception {
        Object targetObject = null;
        if (!Modifier.isStatic(providerMethod.getModifiers())) {
            targetObject = providerType.newInstance();
        }
        CharSequence sql = (CharSequence) providerMethod.invoke(targetObject, args);
        return sql != null ? sql.toString() : null;
    }

    private String replacePlaceholder(String sql) {
        return PropertyParser.parse(sql, configuration.getVariables());
    }

}
