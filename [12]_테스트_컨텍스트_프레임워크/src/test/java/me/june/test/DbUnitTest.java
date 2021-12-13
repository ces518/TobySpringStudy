package me.june.test;

import java.io.InputStream;
import java.sql.Connection;
import javax.sql.DataSource;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.postgresql.jdbc2.optional.SimpleDataSource;

/**
 * https://techblog.woowahan.com/2650
 */
public class DbUnitTest {

    DataSource dataSource;

    IDatabaseConnection iDatabaseConnection;

    Connection connection;

    FlatXmlDataSet flatXmlDataSet;

    @BeforeEach
    void setUp() throws Exception {
        dataSource = new SimpleDataSource();
        connection = dataSource.getConnection();
        iDatabaseConnection = new MySqlConnection(connection, "dbunit"); // DbUnit 이 접근할 커넥션
        InputStream is = this.getClass().getResourceAsStream("classpath:test.xml"); // 테스트 데이터 세팅
        flatXmlDataSet = new FlatXmlDataSetBuilder().build(is);
        DatabaseOperation.CLEAN_INSERT.execute(iDatabaseConnection, flatXmlDataSet);
    }

    @AfterEach
    void tearDown() throws Exception {
        DatabaseOperation.DELETE_ALL.execute(iDatabaseConnection, flatXmlDataSet);
        if (connection != null) {
            connection.close();
        }

        if (iDatabaseConnection != null) {
            iDatabaseConnection.close();
        }
    }

}
