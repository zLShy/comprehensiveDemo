package com.shy.zlread.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangli on 2019/7/17.
 */

@Entity
public class TestGreendao {
    private Long id;
    private String name;
    private String sex;
    private String age;
    @Generated(hash = 787555285)
    public TestGreendao(Long id, String name, String sex, String age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }
    @Generated(hash = 1928615531)
    public TestGreendao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
}
