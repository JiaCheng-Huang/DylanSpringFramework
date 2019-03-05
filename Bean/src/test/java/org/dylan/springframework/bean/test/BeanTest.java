package org.dylan.springframework.bean.test;

import org.dylan.springframework.annotation.Repository;
import org.dylan.springframework.bean.serviceI.ServiceI;
import org.dylan.springframework.factory.AnnotationBeanFactory;
import org.dylan.springframework.factory.BeanFactory;
import org.dylan.springframework.io.Loader;
import org.dylan.springframework.io.PropertiesLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jiacheng Huang
 * @date 2019/03/03 22:34
 */
@Repository
public class BeanTest {
    BeanFactory beanFactory;

    @Before
    public void load() {
        Loader loader = new PropertiesLoader("properties.properties");
        beanFactory = new AnnotationBeanFactory(loader);
    }

    @Test
    public void AutowiredTest() {
        ServiceI service = (ServiceI) beanFactory.getBean("service");
        Assert.assertEquals("哈哈", service.query());
        Assert.assertEquals(true, service.delete());
    }

    @Test
    public void QualifierTest() {
        ServiceI service = (ServiceI) beanFactory.getBean("anotherService");
        Assert.assertEquals("啦啦", service.query());
        Assert.assertEquals(false, service.delete());
    }

}
