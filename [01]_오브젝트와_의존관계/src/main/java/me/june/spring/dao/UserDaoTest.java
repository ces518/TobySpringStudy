package me.june.spring.dao;

import java.sql.SQLException;
import me.june.spring.domain.User;

public class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new DaoFactory().userDao();

        User user = new User();
        user.setId("ncucu");
        user.setName("엔꾸꾸");
        user.setPassword("패스워드");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User findUser = dao.get(user.getId());
        System.out.println(findUser.getName());
    }
}
