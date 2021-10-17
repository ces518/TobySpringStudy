package infra;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {

    private ConnectionMaker connectionMaker;
    int counter = 0;

    public CountingConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        counter++;
        return connectionMaker.makeConnection();
    }

    public int getCounter() {
        return counter;
    }
}
