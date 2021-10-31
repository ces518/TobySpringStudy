package infra;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // D 사의 커넥션 생성로직...
        return null;
    }
}
