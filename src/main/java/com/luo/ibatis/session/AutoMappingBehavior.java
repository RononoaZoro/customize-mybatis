package com.luo.ibatis.session;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 14:55
 * @description：
 * Specifies if and how MyBatis should automatically map columns to fields/properties.
 */
public enum AutoMappingBehavior {
    /**
     * Disables auto-mapping.
     */
    NONE,

    /**
     * Will only auto-map results with no nested(嵌套) result mappings defined inside.
     */
    PARTIAL,

    /**
     * Will auto-map result mappings of any complexity (containing nested or otherwise).
     */
    FULL

}
