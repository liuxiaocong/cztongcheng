package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

/**
 * Created by LiuXiaocong on 9/1/2016.
 */
public enum ENewsType {
    ECZCommon(0), EJianshu(1),EMinSheng(2),EJieyang(3);
    private final int value;

    private ENewsType(int value) {
        this.value = value;
    }

    public static ENewsType valueOf(int value) {
        switch (value) {
            case 0:
                return ECZCommon;
            case 1:
                return EJianshu;
            default:
                return EMinSheng;
        }
    }

    public int getValue() {
        return value;
    }
}
