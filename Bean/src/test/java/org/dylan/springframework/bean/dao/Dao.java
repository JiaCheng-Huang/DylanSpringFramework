package org.dylan.springframework.bean.dao;

import org.dylan.springframework.annotation.Repository;

/**
 * @author Jiacheng Huang
 * @date 2019/03/04 00:09
 */
@Repository
public class Dao {
    public String query() {
        return "哈哈";
    }
}
