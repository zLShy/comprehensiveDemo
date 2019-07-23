package com.zl.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.shy.zlread.bean.User;
import com.shy.zlread.bean.TestGreendao;

import com.zl.greendao.gen.UserDao;
import com.zl.greendao.gen.TestGreendaoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig testGreendaoDaoConfig;

    private final UserDao userDao;
    private final TestGreendaoDao testGreendaoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        testGreendaoDaoConfig = daoConfigMap.get(TestGreendaoDao.class).clone();
        testGreendaoDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        testGreendaoDao = new TestGreendaoDao(testGreendaoDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(TestGreendao.class, testGreendaoDao);
    }
    
    public void clear() {
        userDaoConfig.clearIdentityScope();
        testGreendaoDaoConfig.clearIdentityScope();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public TestGreendaoDao getTestGreendaoDao() {
        return testGreendaoDao;
    }

}
