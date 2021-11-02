package service;

import domain.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    void add(User user);

    void upgradeLevels();

    @Transactional(readOnly = true)
    User get(String id);

    void deleteAll();

    @Transactional(readOnly = true)
    int getCount();


    @Transactional(readOnly = true)
    List<User> getAll();
}
