package com.jjf.utils;

/**
 * Created by jjf_lenovo on 2017/5/20.
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取spring容器，以访问容器中定义的其他bean
 * @author Simon
 *
 */
public class SpringContextUtil implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)  {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) throws BeansException  {
        return applicationContext.getBean(name);

    }

    public static String getContextPath(){
        return applicationContext.getApplicationName();
    }

}
