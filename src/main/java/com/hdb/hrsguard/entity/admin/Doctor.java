package com.hdb.hrsguard.entity.admin;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author hollis
 */
@Entity
@Table(name="hg_doctor")
@EntityListeners(AuditingEntityListener.class)
public class Doctor  implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final int DOC_SEX_MAN = 1;//性别男

    private static final int DOC_SEX_WOMAN = 2;//性别女

    private static final int DOC_SEX_UNKONW = 0;//性别未知
    @Column(name="doc_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name="doc_name",length = 20)
    private String doc_name;
    @Column(name="doc_gender",length=1)
    private int doc_gender=DOC_SEX_UNKONW;
    @Column(name = "doc_major",length = 50)
    private String doc_major;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doc_dep_id")
    private Department department;
    @Column(name ="doc_level",length = 20)
    private String doc_level;
    @Column(name = "doc_introduction",columnDefinition = "text")
    private String doc_introduction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public int getDoc_gender() {
        return doc_gender;
    }

    public void setDoc_gender(int doc_gender) {
        this.doc_gender = doc_gender;
    }

    public String getDoc_major() {
        return doc_major;
    }

    public void setDoc_major(String doc_major) {
        this.doc_major = doc_major;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDoc_level() {
        return doc_level;
    }

    public void setDoc_level(String doc_level) {
        this.doc_level = doc_level;
    }

    public String getDoc_introduction() {
        return doc_introduction;
    }

    public void setDoc_introduction(String doc_introduction) {
        this.doc_introduction = doc_introduction;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", doc_name='" + doc_name + '\'' +
                ", doc_gender=" + doc_gender +
                ", doc_major='" + doc_major + '\'' +
                ", department=" + department +
                ", doc_level='" + doc_level + '\'' +
                ", doc_introduction='" + doc_introduction + '\'' +
                '}';
    }
}
