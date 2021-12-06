package me.june.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import me.june.mvc.servlet.ConfigurableDispatcherServlet;
import org.junit.jupiter.api.AfterEach;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

public class AbstractDispatcherServletTest implements AfterRunService {

    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;
    protected MockServletConfig config = new MockServletConfig("spring");
    protected MockHttpSession session;

    private ConfigurableDispatcherServlet dispatcherServlet;
    private Class<?>[] classes;
    private String[] locations;
    private String[] relativeLocations;
    private String servletPath;

    public AbstractDispatcherServletTest initRequest(String requestUri, String method) {
        this.request = new MockHttpServletRequest(method, requestUri);
        this.response = new MockHttpServletResponse();
        if (this.servletPath != null) {
            this.setServletPath(this.servletPath);
        }
        return this;
    }

    public AbstractDispatcherServletTest initRequest(String requestUri, RequestMethod method) {
        return this.initRequest(requestUri, method.toString());
    }

    public AbstractDispatcherServletTest initRequest(String requestUri) {
        initRequest(requestUri, RequestMethod.GET);
        return this;
    }

    public AbstractDispatcherServletTest addParameter(String name, String value) {
        if (this.request == null) {
            throw new IllegalStateException("request가 초기화되지 않았습니다.");
        }
        this.request.addParameter(name, value);
        return this;
    }

    public AbstractDispatcherServletTest buildDispatcherServlet() throws ServletException {
        if (this.classes == null && this.locations == null && this.relativeLocations == null) {
            throw new IllegalStateException("classes와 locations 중 하나는 설정해야 합니다.");

        }

        this.dispatcherServlet = new ConfigurableDispatcherServlet();
        this.dispatcherServlet.setClasses(this.classes);
        this.dispatcherServlet.setLocations(this.locations);
        if (this.relativeLocations != null) {
            this.dispatcherServlet.setRelativeLocations(getClass(), this.relativeLocations);
        }

        this.dispatcherServlet.init(this.config);

        return this;
    }

    public AfterRunService runService() throws ServletException, IOException {
        if (this.dispatcherServlet == null) {
            buildDispatcherServlet();
        }
        if (this.request == null) {
            throw new IllegalStateException("request가 준비되지 않았습니다.");
        }
        this.dispatcherServlet.service(this.request, this.response);
        return this;
    }

    public MockHttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(MockHttpServletRequest request) {
        this.request = request;
    }

    public MockHttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(MockHttpServletResponse response) {
        this.response = response;
    }

    public MockServletConfig getConfig() {
        return config;
    }

    public void setConfig(MockServletConfig config) {
        this.config = config;
    }

    public MockHttpSession getSession() {
        return session;
    }

    public void setSession(MockHttpSession session) {
        this.session = session;
    }

    public ConfigurableDispatcherServlet getDispatcherServlet() {
        return dispatcherServlet;
    }

    public void setDispatcherServlet(ConfigurableDispatcherServlet dispatcherServlet) {
        this.dispatcherServlet = dispatcherServlet;
    }

    public Class<?>[] getClasses() {
        return classes;
    }

    public AbstractDispatcherServletTest setClasses(Class<?>[] classes) {
        this.classes = classes;
        return this;
    }

    public String[] getLocations() {
        return locations;
    }

    public AbstractDispatcherServletTest setLocations(String... locations) {
        this.locations = locations;
        return this;
    }

    public String[] getRelativeLocations() {
        return relativeLocations;
    }

    public AbstractDispatcherServletTest setRelativeLocations(String... relativeLocations) {
        this.relativeLocations = relativeLocations;
        return this;
    }

    public String getServletPath() {
        return servletPath;
    }

    public AbstractDispatcherServletTest setServletPath(String servletPath) {
        if (this.request == null) {
            this.servletPath = servletPath;
        } else {
            this.request.setServletPath(servletPath);
        }
        return this;
    }

    @Override
    public String getContentAsString() throws UnsupportedEncodingException {
        return response.getContentAsString();
    }

    @Override
    public WebApplicationContext getContext() {
        if (this.dispatcherServlet == null) {
            throw new IllegalStateException("DispatcherServlet이 준비되지 않았습니다.");
        }

        return this.dispatcherServlet.getWebApplicationContext();
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        if (this.dispatcherServlet == null) {
            throw new IllegalStateException("DispatcherServlet이 준비되지 않았습니다.");
        }
        return this.getContext().getBean(beanType);
    }

    @Override
    public ModelAndView getModelAndView() {
        return this.dispatcherServlet.getModelAndView();
    }

    @Override
    public AfterRunService assertViewName(String viewName) {
        assertThat(getModelAndView().getViewName()).isEqualTo(viewName);
        return this;
    }

    @Override
    public AfterRunService assertModel(String name, Object value) {
        assertThat(getModelAndView().getModel().get(name)).isEqualTo(value);
        return this;
    }

    @AfterEach
    public void closeServletContext() {
        if (this.dispatcherServlet != null) {
            ((ConfigurableApplicationContext) dispatcherServlet.getWebApplicationContext()).close();
        }
    }

}
