package me.june.mvc.propertyeditor;

/**
 * 모조 오브젝트 사용으로 인한 잘못된 버그를 사전에 방지하기 위해 Fake 객체를 사용하는것도 하나의 방법이다.
 */
public class FakeCode extends Code {

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException();
    }
}
