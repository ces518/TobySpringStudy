package test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class JUnitTest {

    @Autowired
    ApplicationContext context;

    static JUnitTest testObject;
    static Set<JUnitTest> testObjects = new HashSet<>();

    static ApplicationContext contextObject = null;

    @Test
    void test1() {
        assertThat(this).isNotEqualTo(testObject);
        assertThat(testObjects).isNotIn(this);
        testObject = this;
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    void test2() {
        assertThat(this).isNotEqualTo(testObject);
        assertThat(testObjects).isNotIn(this);
        testObject = this;
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    void test3() {
        assertThat(this).isNotEqualTo(testObject);
        assertThat(testObjects).isNotIn(this);
        testObject = this;
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }
}
