package org.dylan.springframework.factory;

/**
 * @author Jiacheng Huang
 * @date 2019/03/03 23:17
 */
public interface BeanFactory {
    Object getBean(String beanName);

    boolean containsBean(String beanName);
}
