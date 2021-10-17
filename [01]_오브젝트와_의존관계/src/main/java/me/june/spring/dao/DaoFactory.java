package me.june.spring.dao;

import me.june.spring.infra.ConnectionMaker;
import me.june.spring.infra.SimpleConnectionMaker;

public class DaoFactory {

    public UserDao userDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        return new UserDao(connectionMaker);
    }

    public AccountDao accountDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        return new AccountDao(connectionMaker);
    }

    public MessageDao messageDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        return new MessageDao(connectionMaker);
    }

    private ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
