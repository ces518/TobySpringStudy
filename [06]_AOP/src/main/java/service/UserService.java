package service;

import domain.User;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;

public interface UserService {

    void add(User user);

    void upgradeLevels();

    User get(String id);

    void deleteAll();

    int getCount();

    List<User> getAll();
}
