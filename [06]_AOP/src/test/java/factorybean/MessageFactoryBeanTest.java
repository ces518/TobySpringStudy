package factorybean;

import static org.assertj.core.api.Assertions.assertThat;

import dao.DaoFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = DaoFactory.class
)
class MessageFactoryBeanTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void getMessageFromFactoryBean() {
        Object message = applicationContext.getBean("message");
        assertThat(message).isInstanceOf(Message.class);
        assertThat(((Message)message).getText()).isEqualTo("Factory Bean");
    }
}