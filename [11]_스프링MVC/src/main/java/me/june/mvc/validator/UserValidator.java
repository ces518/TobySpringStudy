package me.june.mvc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getName() == null || user.getName().length() == 0) {
            errors.rejectValue("name", "field.required");
        }
        // ValidationUtils 를 활용할 수 있다. 공백도 허용하지 않는다면 rejectIfEmptyOrWhitespace 메소드로 처리가 가능하다.
        ValidationUtils.rejectIfEmpty(errors, "name", "field.required");

        if (user.age < 0) {
            // 에러 메세지 제공시 활용
            errors.rejectValue("name", "field.min", new Object[]{0}, null);
        }

        // 여러 필드에 걸쳐 벨리데이션을 수행해야 한다면 reject 메소드를 활용한 글로벌 에러를 만드는것이 좋다.
        errors.reject("field.reject",null);

    }
}
