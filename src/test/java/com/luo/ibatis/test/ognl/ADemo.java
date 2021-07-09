package com.luo.ibatis.test.ognl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：archer
 * @date ：Created in 2021/7/8 9:47
 * @description：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ADemo implements Serializable {
    private String name;

    private Integer age;
}


