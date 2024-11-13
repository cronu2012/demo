package com.rabbitmq.demo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Bean实例工厂，用于获取Bean实例。
 * 
 * @author jqlin
 *
 */
@Component
public class BeanFactory
    implements ApplicationContextAware
{
    private static ApplicationContext context;

    /**
     * 获得Bean实例。
     * 
     * @param <T>
     *            目标类。
     * @param clazz
     *            目标类型。
     * @return Bean实例；如果存在多个将随机返回一个，如果不存在将返回null。
     */
    public static <T> T getBean(Class<T> clazz)
    {
        Collection<T> beans = getBeans(clazz);

        return beans.isEmpty()?null:beans.iterator().next();
    }

    /**
     * 获取Bean实例。
     * 
     * @param <T>
     *            目标类。
     * @param beanName
     *            Bean定义名称。
     * @param clazz
     *            目标类型。
     * @return Bean实例，如果不存在则返回null。
     */
    public static <T> T getBean(String beanName, Class<T> clazz)
    {
        try
        {
            return context.getBean(beanName, clazz);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * 获得Bean实例集。
     * 
     * @param <T>
     *            目标类。
     * @param clazz
     *            目标类型。
     * @return Bean实例集，如果不存在则返回空集。
     */
    public static <T> Collection<T> getBeans(Class<T> clazz)
    {
        return context.getBeansOfType(clazz).values();
    }

    @Override
    public void setApplicationContext(ApplicationContext context)
        throws BeansException
    {
        BeanFactory.context = context;
    }
}
