package domain;

/**
 * UserLevel 을 Upgrade 할 수 있는지 판단하고, LevelUpgrade 까지는 정책의 **책임** 이 맞다.
 * 하지만 DB 에 반영하는 것 까지 정책의 책임일까 ?...
 */
public class DefaultUserLevelUpgradePolicy implements UserLevelUpgradePolicy {

    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    @Override
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER;
            case SILVER:
                return user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD;
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unknown Level : " + currentLevel);
        }
    }

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
    }
}
