package com.luo.ibatis.builder.xml;

import com.luo.ibatis.builder.BaseBuilder;
import com.luo.ibatis.builder.BuilderException;
import com.luo.ibatis.executor.ErrorContext;
import com.luo.ibatis.parsing.XNode;
import com.luo.ibatis.parsing.XPathParser;
import com.luo.ibatis.reflection.DefaultReflectorFactory;
import com.luo.ibatis.reflection.ReflectorFactory;
import com.luo.ibatis.session.Configuration;

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * @author ：archer
 * @date ：Created in 2021/6/11 17:40
 * @description： xml config 配置解析器
 * @modified By：
 */
public class XMLConfigBuilder extends BaseBuilder {

    private boolean parsed;
    private final XPathParser parser;
    private String environment;
    private final ReflectorFactory localReflectorFactory = new DefaultReflectorFactory();

    public XMLConfigBuilder(Reader reader) {
        this(reader, null, null);
    }

    public XMLConfigBuilder(Reader reader, String environment) {
        this(reader, environment, null);
    }

    public XMLConfigBuilder(Reader reader, String environment, Properties props) {
        this(new XPathParser(reader, true, props, new XMLMapperEntityResolver()), environment, props);
    }

    public XMLConfigBuilder(InputStream inputStream) {
        this(inputStream, null, null);
    }

    public XMLConfigBuilder(InputStream inputStream, String environment) {
        this(inputStream, environment, null);
    }

    public XMLConfigBuilder(InputStream inputStream, String environment, Properties props) {
        this(new XPathParser(inputStream, true, props, new XMLMapperEntityResolver()), environment, props);
    }

    private XMLConfigBuilder(XPathParser parser, String environment, Properties props) {
        super(new Configuration());
        ErrorContext.instance().resource("SQL Mapper Configuration");
        this.configuration.setVariables(props);
        this.parsed = false;
        this.environment = environment;
        this.parser = parser;
    }

    public Configuration parse() {
        // 防止parse（）方法被同一个实例多次调用
        if (parsed) {
            throw new BuilderException("Each XMLConfigBuilder can only be used once.");
        }
        parsed = true;
        // 调用XPathParser.evalNode（）方法，创建表示configuration节点的XNode对象。
        // 调用parseConfiguration（）方法对XNode进行处理
        parseConfiguration(parser.evalNode("/configuration"));
        return configuration;
    }

    private void parseConfiguration(XNode root) {
        try {
            //issue #117 read properties first
//            propertiesElement(root.evalNode("properties"));
//            Properties settings = settingsAsProperties(root.evalNode("settings"));
//            loadCustomVfs(settings);
//            typeAliasesElement(root.evalNode("typeAliases"));
//            pluginElement(root.evalNode("plugins"));
//            objectFactoryElement(root.evalNode("objectFactory"));
//            objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
//            reflectorFactoryElement(root.evalNode("reflectorFactory"));
//            settingsElement(settings);
//            // read it after objectFactory and objectWrapperFactory issue #631
//            environmentsElement(root.evalNode("environments"));
//            databaseIdProviderElement(root.evalNode("databaseIdProvider"));
//            typeHandlerElement(root.evalNode("typeHandlers"));
//            mapperElement(root.evalNode("mappers"));
        } catch (Exception e) {
            throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
    }

}
