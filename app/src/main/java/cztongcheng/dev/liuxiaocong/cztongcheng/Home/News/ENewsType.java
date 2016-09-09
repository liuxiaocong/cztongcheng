package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

/**
 * Created by LiuXiaocong on 9/8/2016.
 */
public enum ENewsType {
    ECZCommon(0), EJianshu(1), EMinSheng(2), EJieyang(3), EShantou(4);
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
            case 2:
                return EMinSheng;
            case 3:
                return EJieyang;
            case 4:
                return EShantou;
            default:
                return EMinSheng;
        }
    }

    public int getValue() {
        return value;
    }
}