package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by LiuXiaocong on 9/8/2016.
 */
public  class ENewsTypeConverter implements PropertyConverter<ENewsType, Integer> {
    @Override
    public ENewsType convertToEntityProperty(Integer databaseValue) {
        return ENewsType.valueOf(databaseValue);
    }

    @Override
    public Integer convertToDatabaseValue(ENewsType entityProperty) {
        return entityProperty.getValue();
    }
}