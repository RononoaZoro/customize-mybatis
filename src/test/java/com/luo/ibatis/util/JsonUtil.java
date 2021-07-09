package com.luo.ibatis.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.luo.ibatis.test.ognl.ADemo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Slf4j
public abstract class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(Object object) {
        try {
            if (Objects.isNull(object)) {
                return null;
            }
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("toJson error", e);
        }
        return null;
    }

    public static <T> T fromJson(String text, Class<T> clz) {
        try {
            if (StringUtils.isBlank(text)) {
                return null;
            }
            return objectMapper.readValue(text, clz);
        } catch (Exception e) {
            log.error("fromJson error", e);
        }
        return null;
    }

    public static <T> List<T> fromJsonArray(String text, Class<T> clz) {
        if (text == null) {
            return Lists.newArrayList();
        }
        return fromJsonArray(text, clz, List.class);
    }

    public static <T, C extends Collection<T>> C fromJsonArray(String text, Class<T> typeClz, Class<C> collectionClz) {
        try {
            return objectMapper.readValue(text, objectMapper.getTypeFactory().constructCollectionType(collectionClz, typeClz));
        } catch (Exception e) {
            log.error("fromJsonArray error", e);
        }
        if (List.class.isAssignableFrom(collectionClz)) {
            return (C) Lists.newArrayList();
        } else if (Set.class.isAssignableFrom(collectionClz)) {
            return (C) Sets.newHashSet();
        }
        return null;
    }

    public static <V> Map<String, V> toMap(String text, Class<V> valueClass) {
        return toMap(text, String.class, valueClass);
    }

    public static <K, V> Map<K, V> toMap(String text, Class<K> keyClass, Class<V> valueClass) {
        try {
            return objectMapper.readValue(text, objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
        } catch (Exception e) {
            log.error("parse json to object error", e);
        }
        return Maps.newHashMap();
    }

    public static Map<String, Object> toMap(String text) {
        try {
            return objectMapper.readValue(text, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            log.error("parse json to object error", e);
        }
        return Maps.newHashMap();

    }

    public static JsonNode toJsonNode(Object object) {
        try {
            return objectMapper.valueToTree(object);
        } catch (Exception e) {
            log.error("toJsonNode error", e);
        }
        return null;
    }

    public static JsonNode toJsonNode(String text) {
        try {
            return objectMapper.readTree(text);
        } catch (Exception e) {
            log.error("parseJsonNode error", e);
        }
        return null;
    }

    public static <T> T fromJsonNode(JsonNode jsonNode, Class<T> clz) {
        try {
            return objectMapper.convertValue(jsonNode, clz);
        } catch (Exception e) {
            log.error("fromJsonNode error", e);
        }
        return null;
    }

    public static void main(String[] args) {
        ADemo aDemo = JsonUtil.fromJson("{\"name\":\"xx\", \"age\": 20}", ADemo.class);
    }

}
