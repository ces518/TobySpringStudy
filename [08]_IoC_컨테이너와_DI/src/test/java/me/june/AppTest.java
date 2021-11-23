package me.june;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

public class AppTest {

    @Test
    void registerSingleton() throws Exception {
        StaticApplicationContext applicationContext = new StaticApplicationContext();
        // registerSingleton 메소드로 등록할 경우 GenericBeanDefinition 으로 등록됨
        applicationContext.registerSingleton("hello1", Hello.class);

        Hello hello1 = applicationContext.getBean("hello1", Hello.class);
        assertThat(hello1, is(notNullValue()));
    }

    @Test
    void rootBeanDefinition() throws Exception {
        StaticApplicationContext applicationContext = new StaticApplicationContext();
        // RootBeanDefinition 은 가장 기본적인 구현체
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        applicationContext.registerBeanDefinition("hello2", helloDef);

        Hello hello2 = applicationContext.getBean("hello2", Hello.class);
        assertThat(hello2, is(notNullValue()));
        assertThat(hello2.sayHello(), is("Hello Spring"));

        // BeanDefinition 정보를 가져오는것도 가능함
        assertThat(applicationContext.getBeanFactory().getBeanDefinitionCount(), is(1));
    }

    @Test
    void registerBeanWithDependency() throws Exception {
        StaticApplicationContext applicationContext = new StaticApplicationContext();

        // StringPrinter 빈 등록
        applicationContext.registerBeanDefinition("printer",
            new RootBeanDefinition(StringPrinter.class));

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        // ID 가 printer 로 등록된 빈의 레퍼런스를 프로퍼티로 주입
        helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));

        applicationContext.registerBeanDefinition("hello", helloDef);

        Hello hello = applicationContext.getBean("hello", Hello.class);
        hello.print();

        assertThat(applicationContext.getBean("printer").toString(), is("Hello Spring"));
    }

    @Test
    void genericApplicationContext() throws Exception {
        // GenericApplicationContext 는 파일이나, 애노테이션 등 외부리소스를 이용해 설정정보를 읽어온다.
        // 이때 BeanDefinitionReader 를 사용한다.
        GenericApplicationContext applicationContext = new GenericApplicationContext();

        // XmlBeanDefinitionReader 은 기본적으로 클래스패스에서 리소스를 찾는다.
        // classpath:, file: http: 같은 접두어로 리소스 타입 지정도 가능하다.
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);
        reader.loadBeanDefinitions("genericApplicationContext.xml");
        applicationContext.refresh(); // 메타 정보 등록후 애플리케이션 컨텍스트 초기화

        Hello hello = applicationContext.getBean("hello", Hello.class);
        hello.print();

        assertThat(applicationContext.getBean("printer").toString(), is("Hello Spring"));
    }

    @Test
    void genericXmlApplicationContext() throws Exception {
        // GenericXmlApplicationContext = GenericApplicationContext + XmlBeanDefinitionReader
        GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext(
            "genericApplicationContext.xml");
        Hello hello = applicationContext.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));
    }

    /**
     * 계층형 컨텍스트 구성시
     * 하위 계층의 빈이 우선순위가 더 높다.
     * 하지만 방법이 제공된다해서 권장하는 방식은 아니다.
     * 부모.자식 컨텍스트에 빈이 중복되는 경우 찾기 힘든 버그가 발생하기 쉽다.
     * 이는 피해야할 방식이다.
     */
    @Test
    void hierarchyContext() throws Exception {
        // RootContext
        GenericXmlApplicationContext parent = new GenericXmlApplicationContext("parentContext.xml");
        // ChildContext
        GenericApplicationContext child = new GenericApplicationContext(parent);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
        reader.loadBeanDefinitions("childContext.xml");
        child.refresh();

        Printer printer = child.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));

        Hello hello = child.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));

        hello.print();
        assertThat(printer.toString(), is("Hello Child"));
    }
}
