package com.shy.zlread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shy.zlread.bean.User;
import com.shy.zlread.utils.RandomValue;
import com.zl.greendao.gen.DaoSession;
import com.zl.greendao.gen.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.Random;

public class GreenDaoActivity extends AppCompatActivity {

    private TextView mResult;
    private Random mRandom;
    private static String TAG = "GreenDaoActivity";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greendao_layout);

        this.mResult = (TextView) findViewById(R.id.result_tv);


        mRandom = new Random();
    }

    public void insert(View view) {

        DaoSession daoSession = MyApplication.getDaoSession();
        for (int i = 0; i < 100; i++) {
            User student = new User();
            student.setStudentNo(i);
            int age = mRandom.nextInt(10) + 10;
            student.setAge(age);
            student.setTelPhone(RandomValue.getTel());
            String chineseName = RandomValue.getChineseName();
            student.setName(chineseName);
            if (i % 2 == 0) {
                student.setSex("男");
            } else {
                student.setSex("女");
            }

            student.setAddress(RandomValue.getRoad());
            student.setGrade(String.valueOf(age % 10) + "年纪");
            student.setSchoolName(RandomValue.getSchoolName());
            if (i == 50) {

                user = student;
            }
            long id = daoSession.insertOrReplace(student);
            Log.e(TAG, "id--->" + id);
        }

        mResult.setText(user.getSchoolName());
    }

    public void delate(View view) {

        MyApplication.getDaoSession().delete(user);

        mResult.setText("删除单个");
    }

    public void update(View view) {

        user.setSchoolName("安仁中学");
        MyApplication.getDaoSession().update(user);


    }

    private void selectBycondition() {
        QueryBuilder<User> qb = MyApplication.getDaoSession().queryBuilder(User.class);
        QueryBuilder<User> queryBuilder = qb.where(UserDao.Properties.SchoolName.eq("成都七中"));
        List<User> users = queryBuilder.list();
        mResult.setText(users.size() + "==size");
    }

    public void select(View view) {

//        List<User> users = MyApplication.getDaoSession().loadAll(User.class);
//        mResult.setText(users.get(50).getSchoolName());
        selectBycondition();
    }
}
