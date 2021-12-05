package me.june.mvc;

/**
 * 클래스 상속의 경우 슈퍼 클래스의 매핑정보가 자식에게 그대로 적용된다.
 * 매핑정보가 명시된 슈퍼 클래스의 메소드를 오버라이드 했더라도, 자식클래스에 매핑정보를 명시하지 않는한 해당 정보도 상속된다.
 */
public class Sub extends Super {

    @Override
    public String list() {
        return super.list();
    }
}
