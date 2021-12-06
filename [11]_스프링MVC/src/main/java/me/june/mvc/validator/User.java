package me.june.mvc.validator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * JSR 303 애노테이션을 활용한 벨리데이션 하려면 LocalValidatorFactoryBean 이 빈으로 등록되어 있어야 한다.
 */
public class User {

    int id;

    @Min(0)
    int age;

    @NotNull
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
