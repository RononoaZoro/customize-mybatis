package com.luo.ibatis.builder;

import com.luo.ibatis.mapping.Discriminator;
import com.luo.ibatis.mapping.ResultMap;
import com.luo.ibatis.mapping.ResultMapping;

import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/6/30 21:18
 * @description： result 解析器
 */
public class ResultMapResolver {

    private final MapperBuilderAssistant assistant;
    private final String id;
    private final Class<?> type;
    private final String extend;
    private final Discriminator discriminator;
    private final List<ResultMapping> resultMappings;
    private final Boolean autoMapping;

    public ResultMapResolver(MapperBuilderAssistant assistant, String id, Class<?> type, String extend, Discriminator discriminator, List<ResultMapping> resultMappings, Boolean autoMapping) {
        this.assistant = assistant;
        this.id = id;
        this.type = type;
        this.extend = extend;
        this.discriminator = discriminator;
        this.resultMappings = resultMappings;
        this.autoMapping = autoMapping;
    }

    public ResultMap resolve() {
        return assistant.addResultMap(this.id, this.type, this.extend, this.discriminator, this.resultMappings, this.autoMapping);
    }
}
