package cztongcheng.dev.liuxiaocong.cztongcheng.Data;


import java.util.List;

import cztongcheng.dev.liuxiaocong.cztongcheng.GreendaoGen.DaoMaster;
import cztongcheng.dev.liuxiaocong.cztongcheng.GreendaoGen.DaoSession;
import cztongcheng.dev.liuxiaocong.cztongcheng.GreendaoGen.TitleModelDao;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.ENewsType;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.TitleModel;
import cztongcheng.dev.liuxiaocong.cztongcheng.MyApplication;

/**
 * Created by LiuXiaocong on 8/15/2016.
 */
public class DBHelper {
    private String dbName = "cz-db";
    private static DBHelper INSTANCE = null;
    private DaoMaster mDaoMaster;
    private DaoMaster.DevOpenHelper mDevOpenHelper;

    public static synchronized DBHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DBHelper();
        }
        return INSTANCE;
    }

    private DBHelper() {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), dbName, null);
        mDaoMaster = new DaoMaster(mDevOpenHelper.getWritableDatabase());
    }

    public DaoSession getDaoSession() {
        DaoSession daoSession = mDaoMaster.newSession();
        return daoSession;
    }

    public List<TitleModel> getTitleModelListByType(ENewsType eNewsType) {
        DaoSession daoSession = getDaoSession();
        TitleModelDao titleModelDaoDao = daoSession.getTitleModelDao();
        List<TitleModel> titleModelList = titleModelDaoDao.queryBuilder().where(TitleModelDao.Properties.NewsType.eq(eNewsType.getValue())).list();
        return titleModelList;
    }

    public void insertTitleModel(TitleModel titleModel) {
        DaoSession session = getDaoSession();
        TitleModelDao titleModelDao = session.getTitleModelDao();
        titleModelDao.insert(titleModel);
    }

    public void deleteTitleModelByType(ENewsType eNewsType) {
        DaoSession daoSession = getDaoSession();
        TitleModelDao titleModelDaoDao = daoSession.getTitleModelDao();
        List<TitleModel> titleModelList = titleModelDaoDao.queryBuilder().where(TitleModelDao.Properties.NewsType.eq(eNewsType)).list();
        if (titleModelList != null && titleModelList.size() > 0) {
            daoSession.delete(titleModelList);
        }
    }
}
