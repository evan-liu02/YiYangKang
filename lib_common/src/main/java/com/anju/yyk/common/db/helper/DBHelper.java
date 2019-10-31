package com.anju.yyk.common.db.helper;

import android.content.Context;

import com.anju.yyk.common.app.Constants;
import com.anju.yyk.common.db.CommonOrderConfig;
import com.anju.yyk.common.db.CommonOrderConfigDao;
import com.anju.yyk.common.db.CommonType;
import com.anju.yyk.common.db.CommonTypeDao;
import com.anju.yyk.common.db.DaoMaster;
import com.anju.yyk.common.db.DaoSession;

import java.util.List;

public class DBHelper {

    // DB相关
    private static DaoMaster mDaoMaster = null;
    private static DaoSession mDaoSession = null;

    /** 通用工单配置表操作*/
    private CommonOrderConfigDao mCommonOrderConfigDao;
    /** 城市自定义工单类型表操作*/
    private CommonTypeDao mCommonTypeDao;

    /** 通用工单配置表tag*/
    public static final int COMMON_ORDER_CONFIG_TABLE_TAG = 3;
    /** 城市自定义工单类型表tag*/
    public static final int COMMONTYPE_TABLE_TAG = 4;

    public DBHelper(Context context){

        if (mDaoMaster == null){
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            mDaoMaster = new DaoMaster(helper.getWritableDatabase());
            if (mDaoSession == null){
                mDaoSession = mDaoMaster.newSession();
            }
        }

        mCommonOrderConfigDao = mDaoSession.getCommonOrderConfigDao();
        mCommonTypeDao = mDaoSession.getCommonTypeDao();
    }

    // -----------------------------通用工单配置数据库操作，华丽分割线-------------------------------start
    /**
     * 插入单条通用工单数据
     * @param entity {@link CommonOrderConfig}
     */
    public void addToCommonOrderConfigTable(CommonOrderConfig entity){
        mCommonOrderConfigDao.insert(entity);
    }

    /**
     * 批量插入通用工单配置
     * @param entities {@link CommonOrderConfig} 集合
     */
    public void addBatchToCommonOrderConfigTable(List<CommonOrderConfig> entities){
        mCommonOrderConfigDao.insertInTx(entities);
    }

    /**
     * 获的数据库中通用工单配置数据集合
     * @return {@link CommonOrderConfig} 集合
     */
    public List<CommonOrderConfig> getCommonOrderConfigList(){
        return mCommonOrderConfigDao.loadAll();
    }
    // -----------------------------通用工单配置数据库操作，华丽分割线-------------------------------end

    // -----------------------------城市自定义工单类型数据库操作，华丽分割线-------------------------------start
    /**
     * 向城市自定义工单类型数据表中插入错误接口信息
     * @param entity {@link CommonType}
     */
    public void addToCommonTypeTable(CommonType entity){
        mCommonTypeDao.insert(entity);
    }

    /**
     * 批量插入城市自定义工单类型
     * @param entities 集合 {@link CommonType}
     */
    public void addBatchToCommonTypeTable(List<CommonType> entities){
        mCommonTypeDao.insertInTx(entities);
    }

    /**
     * 多重查询城市自定义工单类型
     * @return 集合 {@link CommonType}
     */
    public List<CommonType> getCommonTypeList(){
        return mCommonTypeDao.loadAll();
    }

    // -----------------------------城市自定义工单类型数据库操作，华丽分割线-------------------------------end


    /**
     * 清空指定表
     * @param tableTag	DBHelper.class中各种TAG
     */
    public void clearTable(int tableTag){
        switch(tableTag){
            case COMMON_ORDER_CONFIG_TABLE_TAG:
                mCommonOrderConfigDao.deleteAll();
                break;
            case COMMONTYPE_TABLE_TAG:
                mCommonTypeDao.deleteAll();
                break;
        }
    }

    /**
     * 清除数据库
     */
    public void clearDB(){
        mCommonOrderConfigDao.deleteAll();
        mCommonTypeDao.deleteAll();
    }
}
