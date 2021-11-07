package jaxb;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Test;
import sqlservice.jaxb.SqlType;
import sqlservice.jaxb.Sqlmap;

public class JaxbTest {

    @Test
    void readSqlMap() throws Exception {
        String contextPath = Sqlmap.class.getPackage().getName();
        JAXBContext context = JAXBContext.newInstance(contextPath);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Sqlmap sqlMap = (Sqlmap) unmarshaller.unmarshal(
            getClass().getResourceAsStream("dao/sqlmap.xml")
        );

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
