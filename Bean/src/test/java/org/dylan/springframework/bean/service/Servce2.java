package org.dylan.springframework.bean.service;

import org.dylan.springframework.annotation.Qualifier;
import org.dylan.springframework.annotation.Service;
import org.dylan.springframework.bean.dao.Dao2;
import org.dylan.springframework.bean.serviceI.ServiceI;

/**
 * @author Jiacheng Huang
 * @date 2019/03/05 12:39
 */
@Service(name = "anotherService")
public class Servce2 implements ServiceI {

    @Qualifier("anotherDao")
    private Dao2 dao;

    public String query() {
        return dao.query();
    }

    public boolean delete() {
        return dao.delete();
    }
}
