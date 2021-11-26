package me.june.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContext 를 DI 받기 위한 인터페이스 구현
 * BeanFactory 를 DI 받기 위한 인터페이스 구현
 */
public class SystemBean implements ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext context;

    /**
     * ApplicationContext 의 기본 구현체는 DefaultListableBeanFactory 를 내부적으로 가지고있고, 해당 객체로 빈 팩토리로서의 기능을 위임한다.
     * ApplicationContext 를 통한 것이 아닌 빈 팩토리의 기능을 사용해야 한다면 이를 주입받아 사용하면 된다.
     */
    private BeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
