/*
 * DownloadHelper    2017-03-31
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.filedownload.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-31
 */
public class DownloadHelper {

    private DaoMaster mMaster;

    private DaoSession mSession;

    private DownloadEntityDao mDao;

    private static DownloadHelper mHelper = new DownloadHelper();

    public static DownloadHelper getInstance() {
        return mHelper;
    }

    private DownloadHelper() {

    }

    public void init(Context context) {
        SQLiteDatabase db = new DaoMaster.DevOpenHelper(context, "download.db", null).getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        mSession = master.newSession();
        mDao = mSession.getDownloadEntityDao();
    }

    public void insert(DownloadEntity entity) {
        mDao.insertOrReplace(entity);
    }

    public List<DownloadEntity> getAll(String url) {
        return mDao.queryBuilder().where(DownloadEntityDao.Properties.Download_url.eq(url))
                .orderAsc(DownloadEntityDao.Properties.Thread_id).list();
    }
}