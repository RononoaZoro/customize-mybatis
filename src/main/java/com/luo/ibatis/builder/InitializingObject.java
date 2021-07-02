package com.luo.ibatis.builder;

/**
 * @author ：archer
 * @date ：Created in 2021/6/30 22:04
 * @description：
 * Interface that indicate to provide a initialization method.
 */
public interface InitializingObject {

    /**
     * Initialize a instance.
     * <p>
     * This method will be invoked after it has set all properties.
     * </p>
     * @throws Exception in the event of misconfiguration (such as failure to set an essential property) or if initialization fails
     */
    void initialize() throws Exception;
}
