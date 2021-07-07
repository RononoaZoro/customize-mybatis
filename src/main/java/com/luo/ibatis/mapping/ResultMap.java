package com.luo.ibatis.mapping;

import com.luo.ibatis.annotations.Param;
import com.luo.ibatis.builder.BuilderException;
import com.luo.ibatis.logging.Log;
import com.luo.ibatis.logging.LogFactory;
import com.luo.ibatis.reflection.Jdk;
import com.luo.ibatis.reflection.ParamNameUtil;
import com.luo.ibatis.session.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author ：archer
 * @date ：Created in 2021/6/30 21:11
 * @description： 一个完整的resultmap保存器
 */
public class ResultMap {

    private Configuration configuration;
    // <resultMap>标签id属性
    private String id;
    // <resultMap>标签type属性
    private Class<?> type;
    // <result>标签配置的映射信息
    private List<ResultMapping> resultMappings;
    // <id>标签配置的主键映射信息
    private List<ResultMapping> idResultMappings;
    // <constructor>标签配置的构造器映射信息
    private List<ResultMapping> constructorResultMappings;
    // <result>标签配置的结果集映射信息
    private List<ResultMapping> propertyResultMappings;
    // 存放所有映射的数据库字段信息,当使用columnPrefix配置字段前缀时，所有字段都会追加前缀
    private Set<String> mappedColumns;
    // 存放所有映射的属性信息
    private Set<String> mappedProperties;
    // <discriminator>标签配置的鉴别器信息
    private Discriminator discriminator;
    // 是否有嵌套的<resultMap>
    private boolean hasNestedResultMaps;
    // 是否有存在嵌套查询
    private boolean hasNestedQueries;
    // autoMapping属性值，是否自动映射
    private Boolean autoMapping;

    private ResultMap() {
    }

    public static class Builder {
        private static final Log log = LogFactory.getLog(Builder.class);

        private ResultMap resultMap = new ResultMap();

        public Builder(Configuration configuration, String id, Class<?> type, List<ResultMapping> resultMappings) {
            this(configuration, id, type, resultMappings, null);
        }

        public Builder(Configuration configuration, String id, Class<?> type, List<ResultMapping> resultMappings, Boolean autoMapping) {
            resultMap.configuration = configuration;
            resultMap.id = id;
            resultMap.type = type;
            resultMap.resultMappings = resultMappings;
            resultMap.autoMapping = autoMapping;
        }

        public Builder discriminator(Discriminator discriminator) {
            resultMap.discriminator = discriminator;
            return this;
        }

        public Class<?> type() {
            return resultMap.type;
        }

        public ResultMap build() {
            if (resultMap.id == null) {
                throw new IllegalArgumentException("ResultMaps must have an id");
            }
            resultMap.mappedColumns = new HashSet<String>();
            resultMap.mappedProperties = new HashSet<String>();
            // 將ResultMapping对象进行分类
            resultMap.idResultMappings = new ArrayList<ResultMapping>();
            resultMap.constructorResultMappings = new ArrayList<ResultMapping>();
            resultMap.propertyResultMappings = new ArrayList<ResultMapping>();
            final List<String> constructorArgNames = new ArrayList<String>();
            for (ResultMapping resultMapping : resultMap.resultMappings) {
                resultMap.hasNestedQueries = resultMap.hasNestedQueries || resultMapping.getNestedQueryId() != null;
                resultMap.hasNestedResultMaps = resultMap.hasNestedResultMaps || (resultMapping.getNestedResultMapId() != null && resultMapping.getResultSet() == null);
                final String column = resultMapping.getColumn();
                if (column != null) {
                    resultMap.mappedColumns.add(column.toUpperCase(Locale.ENGLISH));
                } else if (resultMapping.isCompositeResult()) {
                    for (ResultMapping compositeResultMapping : resultMapping.getComposites()) {
                        final String compositeColumn = compositeResultMapping.getColumn();
                        if (compositeColumn != null) {
                            resultMap.mappedColumns.add(compositeColumn.toUpperCase(Locale.ENGLISH));
                        }
                    }
                }
                final String property = resultMapping.getProperty();
                if(property != null) {
                    resultMap.mappedProperties.add(property);
                }
                if (resultMapping.getFlags().contains(ResultFlag.CONSTRUCTOR)) {
                    resultMap.constructorResultMappings.add(resultMapping);
                    if (resultMapping.getProperty() != null) {
                        constructorArgNames.add(resultMapping.getProperty());
                    }
                } else {
                    resultMap.propertyResultMappings.add(resultMapping);
                }
                if (resultMapping.getFlags().contains(ResultFlag.ID)) {
                    resultMap.idResultMappings.add(resultMapping);
                }
            }
            if (resultMap.idResultMappings.isEmpty()) {
                resultMap.idResultMappings.addAll(resultMap.resultMappings);
            }
            if (!constructorArgNames.isEmpty()) {
                final List<String> actualArgNames = argNamesOfMatchingConstructor(constructorArgNames);
                if (actualArgNames == null) {
                    throw new BuilderException("Error in result map '" + resultMap.id
                            + "'. Failed to find a constructor in '"
                            + resultMap.getType().getName() + "' by arg names " + constructorArgNames
                            + ". There might be more info in debug log.");
                }
                Collections.sort(resultMap.constructorResultMappings, new Comparator<ResultMapping>() {
                    @Override
                    public int compare(ResultMapping o1, ResultMapping o2) {
                        int paramIdx1 = actualArgNames.indexOf(o1.getProperty());
                        int paramIdx2 = actualArgNames.indexOf(o2.getProperty());
                        return paramIdx1 - paramIdx2;
                    }
                });
            }
            // ResultMap创建完毕后，属性不允许修改
            resultMap.resultMappings = Collections.unmodifiableList(resultMap.resultMappings);
            resultMap.idResultMappings = Collections.unmodifiableList(resultMap.idResultMappings);
            resultMap.constructorResultMappings = Collections.unmodifiableList(resultMap.constructorResultMappings);
            resultMap.propertyResultMappings = Collections.unmodifiableList(resultMap.propertyResultMappings);
            resultMap.mappedColumns = Collections.unmodifiableSet(resultMap.mappedColumns);
            return resultMap;
        }

        private List<String> argNamesOfMatchingConstructor(List<String> constructorArgNames) {
            Constructor<?>[] constructors = resultMap.type.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                Class<?>[] paramTypes = constructor.getParameterTypes();
                if (constructorArgNames.size() == paramTypes.length) {
                    List<String> paramNames = getArgNames(constructor);
                    if (constructorArgNames.containsAll(paramNames)
                            && argTypesMatch(constructorArgNames, paramTypes, paramNames)) {
                        return paramNames;
                    }
                }
            }
            return null;
        }

        private boolean argTypesMatch(final List<String> constructorArgNames,
                                      Class<?>[] paramTypes, List<String> paramNames) {
            for (int i = 0; i < constructorArgNames.size(); i++) {
                Class<?> actualType = paramTypes[paramNames.indexOf(constructorArgNames.get(i))];
                Class<?> specifiedType = resultMap.constructorResultMappings.get(i).getJavaType();
                if (!actualType.equals(specifiedType)) {
                    if (log.isDebugEnabled()) {
                        log.debug("While building result map '" + resultMap.id
                                + "', found a constructor with arg names " + constructorArgNames
                                + ", but the type of '" + constructorArgNames.get(i)
                                + "' did not match. Specified: [" + specifiedType.getName() + "] Declared: ["
                                + actualType.getName() + "]");
                    }
                    return false;
                }
            }
            return true;
        }

        private List<String> getArgNames(Constructor<?> constructor) {
            List<String> paramNames = new ArrayList<String>();
            List<String> actualParamNames = null;
            final Annotation[][] paramAnnotations = constructor.getParameterAnnotations();
            int paramCount = paramAnnotations.length;
            for (int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
                String name = null;
                for (Annotation annotation : paramAnnotations[paramIndex]) {
                    if (annotation instanceof Param) {
                        name = ((Param) annotation).value();
                        break;
                    }
                }
                if (name == null && resultMap.configuration.isUseActualParamName() && Jdk.parameterExists) {
                    if (actualParamNames == null) {
                        actualParamNames = ParamNameUtil.getParamNames(constructor);
                    }
                    if (actualParamNames.size() > paramIndex) {
                        name = actualParamNames.get(paramIndex);
                    }
                }
                paramNames.add(name != null ? name : "arg" + paramIndex);
            }
            return paramNames;
        }
    }

    public String getId() {
        return id;
    }

    public boolean hasNestedResultMaps() {
        return hasNestedResultMaps;
    }

    public boolean hasNestedQueries() {
        return hasNestedQueries;
    }

    public Class<?> getType() {
        return type;
    }

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }

    public List<ResultMapping> getConstructorResultMappings() {
        return constructorResultMappings;
    }

    public List<ResultMapping> getPropertyResultMappings() {
        return propertyResultMappings;
    }

    public List<ResultMapping> getIdResultMappings() {
        return idResultMappings;
    }

    public Set<String> getMappedColumns() {
        return mappedColumns;
    }

    public Set<String> getMappedProperties() {
        return mappedProperties;
    }

    public Discriminator getDiscriminator() {
        return discriminator;
    }

    public void forceNestedResultMaps() {
        hasNestedResultMaps = true;
    }

    public Boolean getAutoMapping() {
        return autoMapping;
    }
}
