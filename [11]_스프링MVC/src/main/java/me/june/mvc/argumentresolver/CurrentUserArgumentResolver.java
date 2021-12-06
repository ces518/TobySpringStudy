package me.june.mvc.argumentresolver;

import me.june.mvc.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * WebArgumentResolver 를 활용하면 애플리케이션에 특화된 파라미터 리졸버를 만들 수 있다.
 * Spring 3.1 부터는 HandlerMethodArgumentResolver 를 사용해야 한다.
 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver
 */
public class CurrentUserArgumentResolver implements WebArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest)
        throws Exception {
        if (!methodParameter.hasMethodAnnotation(CurrentUser.class)) {
            return WebArgumentResolver.UNRESOLVED;
        }
        // ... CurrentUser 세팅
        return new User();
    }
}
