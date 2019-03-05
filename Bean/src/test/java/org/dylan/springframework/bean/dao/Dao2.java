package org.dylan.springframework.bean.dao;

import org.dylan.springframework.annotation.Repository;

/**
 * @author Jiacheng Huang
 * @date 2019/03/05 12:40
 */
@Repository(name = "anotherDao")
public class Dao2 {
    public String query() {
        return "啦啦";
    }

    public boolean delete() {
        return false;
    }
}
