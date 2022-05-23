package com.hdb.hrsguard.entity.admin;


import com.hdb.hrsguard.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/*
*Description:后台用户实体类
*/
@Entity
@Table(name="hg_patient")
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity{
    private static final long serialVersionUID = 1L;

    private static final int USER_SEX_MAN = 1;//性别男

    private static final int USER_SEX_WOMAN = 2;//性别女

    private static final int USER_SEX_UNKONW = 0;//性别未知

    private static final int USER_AGE=0;//用户年龄默认0岁

    public static final int ADMIN_USER_STATUS_ENABLE = 1;//用户状态正常可用
    public static final int ADMIN_USER_STATUS_UNABLE = 0;//用户状态不可用

    @ValidateEntity(required=true,requiredLeng=true,minLength=4,maxLength=18,errorRequiredMsg="用户名不能为空!",errorMinLengthMsg="用户名长度需大于4!",errorMaxLengthMsg="用户名长度不能大于18!")
    @Column(name="username",nullable=false,length=18,unique=true)
    private String username;//用户名

    @ValidateEntity(required=true,requiredLeng=true,minLength=6,maxLength=32,errorRequiredMsg="密码不能为空！",errorMinLengthMsg="密码长度需大于6!",errorMaxLengthMsg="密码长度不能大于32!")
    @Column(name="password",nullable=false,length=32)
    private String password;//登录密码

    @ValidateEntity(required=false)
    @Column(name="status",length=1)
    private int status = ADMIN_USER_STATUS_ENABLE;//用户状态,默认可用

    @ValidateEntity(required=false)
    @Column(name="sex",length=1)
    private int sex = USER_SEX_UNKONW;//用户性别
    @ValidateEntity(required = false)
    @Column(name = "medical_record",columnDefinition = "text")
    private String medical_record;
    @ValidateEntity(required = false)
    @Column(name = "age")
    private int age;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMedical_record() {
        return medical_record;
    }

    public void  setMedical_record(String medical_record) {
        this.medical_record = medical_record;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", sex=" + sex +
                ", medical_record='" + medical_record + '\'' +
                ", age=" + age +
                '}';
    }
}
