package me.june.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * ApplicationContext 를 DI 받기 위한 인터페이스 구현 BeanFactory 를 DI 받기 위한 인터페이스 구현
 */
public class SystemBean implements ApplicationContextAware, BeanFactoryAware, ResourceLoaderAware,
    ApplicationEventPublisherAware {

    private ApplicationContext context;

    /**
     * ApplicationContext 의 기본 구현체는 DefaultListableBeanFactory 를 내부적으로 가지고있고, 해당 객체로 빈 팩토리로서의 기능을
     * 위임한다. ApplicationContext 를 통한 것이 아닌 빈 팩토리의 기능을 사용해야 한다면 이를 주입받아 사용하면 된다.
     */
    private BeanFactory beanFactory;

    /**
     * ApplicationContext 는 ResourceLoader 이기도 하다. ApplicationContext 를 주입받아 사용해도 되지만, 리소스를 읽어오는 것이
     * 목적이라면 목적에 맞는 인터페이스를 주입받아 사용하는것이 좋다.
     */
    private ResourceLoader resourceLoader;

    /**
     * 스프링은 다양한 이벤트를 발생시킬 수 있고, 해당 이벤트를 처리할 Listener 를 등록해 처리할 수 있다.
     * 이벤트 발생이 목적이라면 ApplicationEventPublisher 를 주입받아 사용하자
     */
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
