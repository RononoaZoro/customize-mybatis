package com.luo.ibatis.session;

import com.luo.ibatis.datasource.jndi.JndiDataSourceFactory;
import com.luo.ibatis.datasource.pooled.PooledDataSourceFactory;
import com.luo.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.luo.ibatis.io.VFS;
import com.luo.ibatis.logging.Log;
import com.luo.ibatis.logging.LogFactory;
import com.luo.ibatis.logging.commons.JakartaCommonsLoggingImpl;
import com.luo.ibatis.logging.jdk14.Jdk14LoggingImpl;
import com.luo.ibatis.logging.log4j.Log4jImpl;
import com.luo.ibatis.logging.log4j2.Log4j2Impl;
import com.luo.ibatis.logging.nologging.NoLoggingImpl;
import com.luo.ibatis.logging.slf4j.Slf4jImpl;
import com.luo.ibatis.logging.stdout.StdOutImpl;
import com.luo.ibatis.mapping.Environment;
import com.luo.ibatis.parsing.XNode;
import com.luo.ibatis.reflection.DefaultReflectorFactory;
import com.luo.ibatis.reflection.ObjectWrapperFactory;
import com.luo.ibatis.reflection.ReflectorFactory;
import com.luo.ibatis.reflection.factory.DefaultObjectFactory;
import com.luo.ibatis.reflection.factory.ObjectFactory;
import com.luo.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import com.luo.ibatis.transaction.jdbc.JdbcTransactionFactory;
import com.luo.ibatis.transaction.managed.ManagedTransactionFactory;
import com.luo.ibatis.type.JdbcType;
import com.luo.ibatis.type.TypeAliasRegistry;
import com.luo.ibatis.type.TypeHandler;
import com.luo.ibatis.type.TypeHandlerRegistry;
import javassist.util.proxy.ProxyFactory;

import java.util.*;

/**
 * @author ：archer
 * @date ：Created in 2021/6/11 18:00
 * @description：核心配置类
 * @modified By：
 */
public class Configuration {

    protected Environment environment;

    protected boolean safeRowBoundsEnabled;
    protected boolean safeResultHandlerEnabled = true;
    protected boolean mapUnderscoreToCamelCase;
    protected boolean aggressiveLazyLoading;
    protected boolean multipleResultSetsEnabled = true;
    protected boolean useGeneratedKeys;
    protected boolean useColumnLabel = true;
    protected boolean cacheEnabled = true;
    protected boolean callSettersOnNulls;
    protected boolean useActualParamName = true;
    protected boolean returnInstanceForEmptyRow;

    protected String logPrefix;
    protected Class<? extends Log> logImpl;
    protected Class<? extends VFS> vfsImpl;
    //    protected LocalCacheScope localCacheScope = LocalCacheScope.SESSION;
    protected JdbcType jdbcTypeForNull = JdbcType.OTHER;
    protected Set<String> lazyLoadTriggerMethods = new HashSet<String>(Arrays.asList(new String[]{"equals", "clone", "hashCode", "toString"}));
    protected Integer defaultStatementTimeout;
    protected Integer defaultFetchSize;
//    protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;
//    protected AutoMappingBehavior autoMappingBehavior = AutoMappingBehavior.PARTIAL;
//    protected AutoMappingUnknownColumnBehavior autoMappingUnknownColumnBehavior = AutoMappingUnknownColumnBehavior.NONE;

    protected Properties variables = new Properties();
    protected ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
    protected ObjectFactory objectFactory = new DefaultObjectFactory();
    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

    protected boolean lazyLoadingEnabled = false;
//    protected ProxyFactory proxyFactory = new JavassistProxyFactory(); // #224 Using internal Javassist instead of OGNL

    protected String databaseId;

    /**
     * Configuration factory class.
     * Used to create Configuration for loading deserialized unread properties.
     *
     * @see <a href='https://code.google.com/p/mybatis/issues/detail?id=300'>Issue 300 (google code)</a>
     */
    protected Class<?> configurationFactory;

    //    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);
//    protected final InterceptorChain interceptorChain = new InterceptorChain();
    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
//    protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();

//    protected final Map<String, MappedStatement> mappedStatements = new StrictMap<MappedStatement>("Mapped Statements collection");
//    protected final Map<String, Cache> caches = new StrictMap<Cache>("Caches collection");
//    protected final Map<String, ResultMap> resultMaps = new StrictMap<ResultMap>("Result Maps collection");
//    protected final Map<String, ParameterMap> parameterMaps = new StrictMap<ParameterMap>("Parameter Maps collection");
//    protected final Map<String, KeyGenerator> keyGenerators = new StrictMap<KeyGenerator>("Key Generators collection");

    protected final Set<String> loadedResources = new HashSet<String>();
    protected final Map<String, XNode> sqlFragments = new StrictMap<XNode>("XML fragments parsed from previous mappers");

    // 存放解析异常的XMLStatementBuilder对象
//    protected final Collection<XMLStatementBuilder> incompleteStatements = new LinkedList<XMLStatementBuilder>();
//    // 存放解析异常的CacheRefResolver对象
//    protected final Collection<CacheRefResolver> incompleteCacheRefs = new LinkedList<CacheRefResolver>();
//    // 存放解析异常的ResultMapResolver对象
//    protected final Collection<ResultMapResolver> incompleteResultMaps = new LinkedList<ResultMapResolver>();
//    // 存放解析异常的MethodResolver对象
//    protected final Collection<MethodResolver> incompleteMethods = new LinkedList<MethodResolver>();

    /*
     * A map holds cache-ref relationship. The key is the namespace that
     * references a cache bound to another namespace and the value is the
     * namespace which the actual cache is bound to.
     */
    protected final Map<String, String> cacheRefMap = new HashMap<String, String>();

    public Configuration(Environment environment) {
        this();
        this.environment = environment;
    }

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("MANAGED", ManagedTransactionFactory.class);

        typeAliasRegistry.registerAlias("JNDI", JndiDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);

//        typeAliasRegistry.registerAlias("PERPETUAL", PerpetualCache.class);
//        typeAliasRegistry.registerAlias("FIFO", FifoCache.class);
//        typeAliasRegistry.registerAlias("LRU", LruCache.class);
//        typeAliasRegistry.registerAlias("SOFT", SoftCache.class);
//        typeAliasRegistry.registerAlias("WEAK", WeakCache.class);
//
//        typeAliasRegistry.registerAlias("DB_VENDOR", VendorDatabaseIdProvider.class);
//
//        typeAliasRegistry.registerAlias("XML", XMLLanguageDriver.class);
//        typeAliasRegistry.registerAlias("RAW", RawLanguageDriver.class);

        typeAliasRegistry.registerAlias("SLF4J", Slf4jImpl.class);
        typeAliasRegistry.registerAlias("COMMONS_LOGGING", JakartaCommonsLoggingImpl.class);
        typeAliasRegistry.registerAlias("LOG4J", Log4jImpl.class);
        typeAliasRegistry.registerAlias("LOG4J2", Log4j2Impl.class);
        typeAliasRegistry.registerAlias("JDK_LOGGING", Jdk14LoggingImpl.class);
        typeAliasRegistry.registerAlias("STDOUT_LOGGING", StdOutImpl.class);
        typeAliasRegistry.registerAlias("NO_LOGGING", NoLoggingImpl.class);

//        typeAliasRegistry.registerAlias("CGLIB", CglibProxyFactory.class);
//        typeAliasRegistry.registerAlias("JAVASSIST", JavassistProxyFactory.class);

//        languageRegistry.setDefaultDriverClass(XMLLanguageDriver.class);
//        languageRegistry.register(RawLanguageDriver.class);
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public Properties getVariables() {
        return variables;
    }

    public void setVariables(Properties variables) {
        this.variables = variables;
    }

    public ReflectorFactory getReflectorFactory() {
        return reflectorFactory;
    }

    public void setReflectorFactory(ReflectorFactory reflectorFactory) {
        this.reflectorFactory = reflectorFactory;
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public ObjectWrapperFactory getObjectWrapperFactory() {
        return objectWrapperFactory;
    }

    public void setObjectWrapperFactory(ObjectWrapperFactory objectWrapperFactory) {
        this.objectWrapperFactory = objectWrapperFactory;
    }

    /**
     * Set a default {@link TypeHandler} class for {@link Enum}.
     * A default {@link TypeHandler} is {@link com.luo.ibatis.type.EnumTypeHandler}.
     * @param typeHandler a type handler class for {@link Enum}
     * @since 3.4.5
     */
    public void setDefaultEnumTypeHandler(Class<? extends TypeHandler> typeHandler) {
        if (typeHandler != null) {
            getTypeHandlerRegistry().setDefaultEnumTypeHandler(typeHandler);
        }
    }

    public boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled) {
        this.lazyLoadingEnabled = lazyLoadingEnabled;
    }



    public boolean isAggressiveLazyLoading() {
        return aggressiveLazyLoading;
    }

    public void setAggressiveLazyLoading(boolean aggressiveLazyLoading) {
        this.aggressiveLazyLoading = aggressiveLazyLoading;
    }

    public void setMultipleResultSetsEnabled(boolean multipleResultSetsEnabled) {
        this.multipleResultSetsEnabled = multipleResultSetsEnabled;
    }

    public Set<String> getLazyLoadTriggerMethods() {
        return lazyLoadTriggerMethods;
    }

    /**
     * @since 3.3.0
     */
    public Integer getDefaultFetchSize() {
        return defaultFetchSize;
    }

    /**
     * @since 3.3.0
     */
    public void setDefaultFetchSize(Integer defaultFetchSize) {
        this.defaultFetchSize = defaultFetchSize;
    }

    public boolean isUseColumnLabel() {
        return useColumnLabel;
    }

    public void setUseColumnLabel(boolean useColumnLabel) {
        this.useColumnLabel = useColumnLabel;
    }


    public String getLogPrefix() {
        return logPrefix;
    }

    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }

    public Class<? extends Log> getLogImpl() {
        return logImpl;
    }

    public void setLogImpl(Class<? extends Log> logImpl) {
        if (logImpl != null) {
            this.logImpl = logImpl;
            // 调用LogFactory类的useCustomLogging（）方法指定日志实现类
            LogFactory.useCustomLogging(this.logImpl);
        }
    }

    public Class<? extends VFS> getVfsImpl() {
        return this.vfsImpl;
    }

    public void setVfsImpl(Class<? extends VFS> vfsImpl) {
        if (vfsImpl != null) {
            this.vfsImpl = vfsImpl;
            VFS.addImplClass(this.vfsImpl);
        }
    }

    public boolean isMultipleResultSetsEnabled() {
        return multipleResultSetsEnabled;
    }

    public void setLazyLoadTriggerMethods(Set<String> lazyLoadTriggerMethods) {
        this.lazyLoadTriggerMethods = lazyLoadTriggerMethods;
    }

    public boolean isUseGeneratedKeys() {
        return useGeneratedKeys;
    }

    public void setUseGeneratedKeys(boolean useGeneratedKeys) {
        this.useGeneratedKeys = useGeneratedKeys;
    }


    public boolean isCallSettersOnNulls() {
        return callSettersOnNulls;
    }

    public void setCallSettersOnNulls(boolean callSettersOnNulls) {
        this.callSettersOnNulls = callSettersOnNulls;
    }

    public boolean isUseActualParamName() {
        return useActualParamName;
    }

    public void setUseActualParamName(boolean useActualParamName) {
        this.useActualParamName = useActualParamName;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    public Class<?> getConfigurationFactory() {
        return configurationFactory;
    }

    public void setConfigurationFactory(Class<?> configurationFactory) {
        this.configurationFactory = configurationFactory;
    }

    public boolean isReturnInstanceForEmptyRow() {
        return returnInstanceForEmptyRow;
    }

    public void setReturnInstanceForEmptyRow(boolean returnEmptyInstance) {
        this.returnInstanceForEmptyRow = returnEmptyInstance;
    }


    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public boolean isSafeResultHandlerEnabled() {
        return safeResultHandlerEnabled;
    }

    public void setSafeResultHandlerEnabled(boolean safeResultHandlerEnabled) {
        this.safeResultHandlerEnabled = safeResultHandlerEnabled;
    }

    public boolean isSafeRowBoundsEnabled() {
        return safeRowBoundsEnabled;
    }

    public void setSafeRowBoundsEnabled(boolean safeRowBoundsEnabled) {
        this.safeRowBoundsEnabled = safeRowBoundsEnabled;
    }

    public Integer getDefaultStatementTimeout() {
        return defaultStatementTimeout;
    }

    public void setDefaultStatementTimeout(Integer defaultStatementTimeout) {
        this.defaultStatementTimeout = defaultStatementTimeout;
    }

    public boolean isMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
    }

    public JdbcType getJdbcTypeForNull() {
        return jdbcTypeForNull;
    }

    public void setJdbcTypeForNull(JdbcType jdbcTypeForNull) {
        this.jdbcTypeForNull = jdbcTypeForNull;
    }






    protected static class StrictMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -4950446264854982944L;
        private final String name;

        public StrictMap(String name, int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
            this.name = name;
        }

        public StrictMap(String name, int initialCapacity) {
            super(initialCapacity);
            this.name = name;
        }

        public StrictMap(String name) {
            super();
            this.name = name;
        }

        public StrictMap(String name, Map<String, ? extends V> m) {
            super(m);
            this.name = name;
        }

        @SuppressWarnings("unchecked")
        public V put(String key, V value) {
            if (containsKey(key)) {
                throw new IllegalArgumentException(name + " already contains value for " + key);
            }
            if (key.contains(".")) {
                final String shortKey = getShortName(key);
                if (super.get(shortKey) == null) {
                    super.put(shortKey, value);
                } else {
                    super.put(shortKey, (V) new Ambiguity(shortKey));
                }
            }
            return super.put(key, value);
        }

        public V get(Object key) {
            V value = super.get(key);
            if (value == null) {
                throw new IllegalArgumentException(name + " does not contain value for " + key);
            }
            if (value instanceof Ambiguity) {
                throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name
                        + " (try using the full name including the namespace, or rename one of the entries)");
            }
            return value;
        }

        private String getShortName(String key) {
            final String[] keyParts = key.split("\\.");
            return keyParts[keyParts.length - 1];
        }

        protected static class Ambiguity {
            final private String subject;

            public Ambiguity(String subject) {
                this.subject = subject;
            }

            public String getSubject() {
                return subject;
            }
        }
    }
}
