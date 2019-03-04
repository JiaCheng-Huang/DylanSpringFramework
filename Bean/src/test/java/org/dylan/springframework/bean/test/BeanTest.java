package org.dylan.springframework.bean.test;

import org.dylan.springframework.bean.service.Service;
import org.dylan.springframework.factory.AnnotationBeanFactory;
import org.dylan.springframework.factory.BeanFactory;
import org.dylan.springframework.io.Loader;
import org.dylan.springframework.io.PropertiesLoader;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jiacheng Huang
 * @date 2019/03/03 22:34
 */
public class BeanTest {

    @Test
    public void loadProp() {

    }

    @Test
    public void getBean() {
        Loader loader = new PropertiesLoader("properties.properties");
        BeanFactory beanFactory = new AnnotationBeanFactory(loader);
        Service service = (Service) beanFactory.getBean("service");
        Assert.assertEquals("哈哈", service.query());
    }

}
