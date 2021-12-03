package me.june.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public HelloSpring helloSpring() {
        return new HelloSpring();
    }

    /**
     * BeanNameUrlHandlerMapping 에 의해 매핑되도록 등록한다.
     * 빈의 이름을 기반으로 Handler 를 찾는다.
     */
    @Bean(name = "/hello")
    public HelloController helloController() {
        return new HelloController();
    }

    /**
     * SimpleUrlHandlerMapping 매핑정보를 한곳에 모아볼 수 있다.
     */
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        Properties properties = new Properties();
        properties.put("/hello", "helloController");
        handlerMapping.setMappings(properties);
        handlerMapping.setInterceptors(handlerInterceptor()); //
        return handlerMapping;
    }

    /**
     * 핸들러 매핑의 역할은 단순 컨트롤러만 찾는게 아니라, 핸들러 실행 체인을 돌려준다.
     * 실행체인은 핸들러 인터셉터와 컨트롤러의 목록 구성이다.
     * HandlerInterceptor 는 컨트롤러 요청 전후처리가 가능하게 한다.
     * preHandle / postHandle / afterCompletion 이 있다.
     * 필터와 유사한 구조로 동작한다.
     */
    @Bean
    public HandlerInterceptor handlerInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
        };
    }

    /**
     * 주로 JSP 를 뷰로 사용할때 사용한다.
     * 기본적으로 등록되어 있다.
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * 컨트롤러 마다 뷰의 종류가 달라 질 수 있다면, 한가지 뷰만 지원하는 뷰 리졸버를 사용할 수 없다.
     * 이런 경우 외부 리소스 파일에 각 뷰에 해당하는 뷰 클래스와설정을 담아두고 이를 참조하는 ResourceBundleViewResolver, XmlViewResolver 를 사용해야 한다.
     * ResourceBundleViewResolver 는 기본적으로 views.properties 파일을 참조한다.
     * Deprecated 되었다.
     */
    @Bean
    public ResourceBundleViewResolver resourceBundleViewResolver() {
        return new ResourceBundleViewResolver();
    }

    /**
     * ResourceBundleViewResolver 와 유사하며, 이는 xml 파일을 참조한다.
     * 기본적으로 /WEB-INF/view.xml
     * 이는 ResourceBundleViewResolver 가 제공하는 지역과 기능이 제공되지 않는다.
     */
    @Bean
    public XmlViewResolver xmlViewResolver() {
        return new XmlViewResolver();
    }

    /**
     * BeanNameViewResolver 는 뷰이름과 동일한 빈 이름을 가진 빈을 찾아 뷰로 사용한다.
     * 이는 별도 설정파일이 아닌 서블릿 컨텍스트의 빈을 사용한다.
     */
    @Bean
    public BeanNameViewResolver beanNameViewResolver() {
        return new BeanNameViewResolver();
    }

    /**
     * ContentNegotiatingViewResolver 는 뷰이름을 이용해 뷰 오브젝트를 찾지 않는다.
     * 미디어 타입 정보를 활용해 다른 뷰 리졸버에서 위임한다.
     * 뷰 리졸버를 결정하는 리졸버이다.
     * 뷰 리볼버를 찾는 방식
     * 1. 미디어 타입의 결정(URL 확장자 / 포맷을 지정하는 파라미터 / Accept Header 3가지 방식 지원)
     * 2. 뷰 리볼버 위임을 통한 후보 뷰 선정
     * 3. 미디어 타입 비교를 통한 최종 뷰선정 (미디어 타입과 뷰 리졸버에서 찾는 후보 뷰 목록과 비교휴 최종 뷰 결정)
     */
    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        return new ContentNegotiatingViewResolver();
    }

    /**
     * 기본으로 등록되는 2가지 예외 핸들러에서 처리하지 못할경우 처리를 하는 마지막 핸들러
     * 스프링 내부적으로 발생하는 주요 예외를 처리하는 표준 예외처리를 담당한다.
     * 컨트롤러 메소드를 찾지 못하는 예외 (NoSuchRequestHandlingMethodException) 이 발생하면 404 응답을 내려준다.
     */
    @Bean
    public DefaultHandlerExceptionResolver exceptionResolver() {
        return new DefaultHandlerExceptionResolver();
    }

    /**
     * SimpleMappingExceptionResolver 는 web.xml 의 error-page 와 비슷하게 예외처리를 담당한다.
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

        Properties properties = new Properties();
        properties.put("DataAccessException", "error/dao");
        resolver.setExceptionMappings(properties);
        resolver.setDefaultErrorView("error/default");

        return resolver;
    }

    /**
     * LocaleResolver 지역정보 (Locale) 을 결정하는 방식
     * 디폴트로 AcceptHeaderLocaleResolver 를 사용한다.
     * 브라우저 설정을 따르지않는다면 SessionLocalResolver/CookieLocalResolver 를 사용하는 것이 낫다.
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    /**
     * MultipartResolver 는 파일업로드와 같은 멀티파트 포맷의 요청을 처리하는 방식
     * CommonsMultipartResolver/StandardServletMultipartResolver 가 있따.
     * StandardServletMultipartResolver 를 기본으로 사용한다 (스프링부트 2.5.x 기준)
     *
     * MultipartResolver 는 ㅁ러티파트 요청을 받으면 MultipartHttpServletRequest 로 전환해주는 역할을 한다.
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    /**
     * RequestToViewNameTranslator 는 컨트롤러에서 뷰이름이나 뷰 오브젝트를 반환하지 않은 경우 HTTP 요청 정보를 참고해 뷰 이름을 생성해준다.
     * /hello 라면 hello, /admin/user 라면 admin/user 와같은 형태로 뷰네임을 생성해준다.
     */
    @Bean
    public RequestToViewNameTranslator requestToViewNameTranslator() {
        return new DefaultRequestToViewNameTranslator();
    }
}
