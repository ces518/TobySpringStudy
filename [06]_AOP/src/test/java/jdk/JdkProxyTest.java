package jdk;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class JdkProxyTest {

    @Test
    void simpleProxy() throws Exception {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("ncucu")).isEqualTo("Hello ncucu");
        assertThat(hello.sayHi("ncucu")).isEqualTo("Hi ncucu");
        assertThat(hello.sayThankYou("ncucu")).isEqualTo("Thank You ncucu");

        // 프록시의 문제
        // 인터페이스의 모든 메소드를 구현해 위임해야 하며
        // 부가기능이 모든 메소드에 중복해서 나타난다.
        Hello proxiedHello = new HelloUppercase(hello);
        assertThat(proxiedHello.sayHello("ncucu")).isEqualTo("HELLO NCUCU");
        assertThat(proxiedHello.sayHi("ncucu")).isEqualTo("HI NCUCU");
        assertThat(proxiedHello.sayThankYou("ncucu")).isEqualTo("THANK YOU NCUCU");
    }

    @Test
    void jdkProxy() throws Exception {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[]{Hello.class},
            new UppercaseHandler(new HelloTarget())
        );
        assertThat(proxiedHello.sayHello("ncucu")).isEqualTo("HELLO NCUCU");
        assertThat(proxiedHello.sayHi("ncucu")).isEqualTo("HI NCUCU");
        assertThat(proxiedHello.sayThankYou("ncucu")).isEqualTo("THANK YOU NCUCU");
    }

    @Test
    void proxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        proxyFactoryBean.addAdvice(new UpperCaseAdvice());
        Hello proxiedHello = (Hello) proxyFactoryBean.getObject();
        assertThat(proxiedHello.sayHello("ncucu")).isEqualTo("HELLO NCUCU");
        assertThat(proxiedHello.sayHi("ncucu")).isEqualTo("HI NCUCU");
        assertThat(proxiedHello.sayThankYou("ncucu")).isEqualTo("THANK YOU NCUCU");
    }

    @Test
    void pointCutAdvisor() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());

        // 포인트 컷 지정
        // 포인트컷 : 부가기능 적용 대상 선정 알고리즘
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        // 어드바이스 지정
        // 어드바이스 : 부가기능
        // 어드바이저 : 포인트컷 + 어드바이스
        // 어드바이저로 지정하는 이유 ?
        // 여러 개의 어드바이스와 포인트컷이 존재할 수 있기 때문이다.
        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UpperCaseAdvice()));

        Hello proxiedHello = (Hello) proxyFactoryBean.getObject();
        assertThat(proxiedHello.sayHello("ncucu")).isEqualTo("HELLO NCUCU");
        assertThat(proxiedHello.sayHi("ncucu")).isEqualTo("HI NCUCU");
        assertThat(proxiedHello.sayThankYou("ncucu")).isEqualTo("Thank You ncucu");
    }

    interface Hello {
        String sayHello(String name);
        String sayHi(String name);
        String sayThankYou(String name);
    }

    static class HelloTarget implements Hello {

        @Override
        public String sayHello(String name) {
            return "Hello " + name;
        }

        @Override
        public String sayHi(String name) {
            return "Hi " + name;
        }

        @Override
        public String sayThankYou(String name) {
            return "Thank You " + name;
        }
    }

    static class HelloUppercase implements Hello {

        Hello hello;

        public HelloUppercase(Hello hello) {
            this.hello = hello;
        }

        @Override
        public String sayHello(String name) {
            return hello.sayHello(name).toUpperCase();
        }

        @Override
        public String sayHi(String name) {
            return hello.sayHi(name).toUpperCase();
        }

        @Override
        public String sayThankYou(String name) {
            return hello.sayThankYou(name).toUpperCase();
        }
    }

    static class UppercaseHandler implements InvocationHandler {

        Hello target;

        public UppercaseHandler(Hello target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object ret = method.invoke(target, args);
            if (ret instanceof String) {
                return ((String)ret).toUpperCase();
            }
            return ret;
        }
    }


    static class UpperCaseAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String)invocation.proceed();
            return ret.toUpperCase();
        }
    }
}
