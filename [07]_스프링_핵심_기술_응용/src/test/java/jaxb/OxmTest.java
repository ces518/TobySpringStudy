package jaxb;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sqlservice.jaxb.SqlType;
import sqlservice.jaxb.Sqlmap;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    locations = "classpath:OxmTest-context.xml"
)
public class OxmTest {

    @Autowired
    Unmarshaller unmarshaller;

    @Test
    void unmarshallSqlMap() throws Exception {
        Source xmlSource = new StreamSource(getClass().getResourceAsStream("sqlmap.xml"));
        Sqlmap sqlMap = (Sqlmap) unmarshaller.unmarshal(xmlSource);

        List<SqlType> sqlList = sqlMap.getSql();

        assertThat(sqlList.size()).isEqualTo(3);
        assertThat(sqlList.get(0).getKey()).isEqualTo("add");
        assertThat(sqlList.get(0).getValue()).isEqualTo("insert");

        assertThat(sqlList.get(1).getKey()).isEqualTo("get");
        assertThat(sqlList.get(1).getValue()).isEqualTo("select");

        assertThat(sqlList.get(2).getKey()).isEqualTo("delete");
        assertThat(sqlList.get(2).getValue()).isEqualTo("delete");
    }
}
