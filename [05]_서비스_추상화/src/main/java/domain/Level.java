package domain;

public enum Level {
    GOLD(3, null),
    SILVER(2, Level.GOLD),
    BASIC(1, Level.SILVER),
    ;

    private final int value;
    private final Level next;

    Level(int value, Level next) {
        this.value = value;
        this.next = next;
    }

    /**
     * Getter 사용을 **지양** 해야한다.
     * Getter 는 객체를 객체로 존중하는 것이 아닌, 무시하고 데이터 자료구조로 쓰이게만 할 뿐이다.
     * 또한 내부 필드 (구현) 이 그대로 노출된다.
     */
    public int intValue() {
        return value;
    }

    public Level nextLevel() {
        return next;
    }

    public static Level valueOf(int value) {
        switch (value) {
            case 1 : return BASIC;
            case 2 : return SILVER;
            case 3 : return GOLD;
            default : throw new AssertionError("Unknown value : " + value);
        }
    }
}
