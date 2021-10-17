package domain;

/**
 * Java Beans 규약을 따르는 User Object.
 * 최근에는 디폴트 생성자와 프로퍼티 (getter setter) 를 가진 POJO 객체를 JavaBeans 라고 표현한다.
 */
public class User {
    String id;
    String name;
    String password;

    public User() {
    }

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
