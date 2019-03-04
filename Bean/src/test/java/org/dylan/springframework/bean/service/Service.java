package org.dylan.springframework.bean.service;

import org.dylan.springframework.annotation.Autowired;
import org.dylan.springframework.bean.dao.Dao;
import org.dylan.springframework.bean.serviceI.ServiceI;

/**
 * @author Jiacheng Huang
 * @date 2019/03/04 00:44
 */
@org.dylan.springframework.annotation.Service
public class Service implements ServiceI {
    @Autowired
    private Dao dao;

    public String query() {
        return dao.query();
    }
}
