package org.dylan.springframework.factory;

import java.util.*;

/**
 * @author Jiacheng Huang
 * @date 2019/03/04 00:50
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    Properties properties;
    Map<String, Object> ioc = new HashMap<String, Object>();
    List<String> classNames = new ArrayList<String>();
    String basePackage;
}
