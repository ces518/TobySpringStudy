package pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class PointcutTest {

    @Test
    void expression() throws Exception {
        System.out.println(Target.class.getMethod("minus", int.class, int.class));
    }

    @Test
    void methodSignaturePointcut() throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(
            "execution(public int pointcut.PointcutTest$Target.minus(int,int) throws java.lang.RuntimeException)"
        );
        assertThat(pointcut.getClassFilter().matches(Target.class) &&
            pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class ,int.class), null)).isTrue();

        assertThat(pointcut.getClassFilter().matches(Target.class) &&
            pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class ,int.class), null)).isFalse();
    }

    interface TargetInterface {

        void hello();

        void hello(String a);

        int minus(int a, int b) throws RuntimeException;

        int plus(int a, int b);
    }

    static class Target implements TargetInterface {

        @Override
        public void hello() {
        }

        @Override
        public void hello(String a) {
        }

        @Override
        public int minus(int a, int b) throws RuntimeException {
            return 0;
        }

        @Override
        public int plus(int a, int b) {
            return 0;
        }

        public void method() {
        }
    }

    static class Bean {

        public void method() throws RuntimeException {

        }
    }

}
