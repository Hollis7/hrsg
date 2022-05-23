package com.hdb.hrsguard.entity.admin;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description 医生科室信息
 * @Author hollis
 */
@Entity
@Table(name="hg_department")
@EntityListeners(AuditingEntityListener.class)
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="dep_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;//科室编号
    @Column(name="dep_name",nullable=false,length=20)
    private String dep_name;//科室名称
    @Column(name="dep_introduce",nullable=false,columnDefinition = "text")
    private String dep_introduce;//科室介绍
    @Column(name="dep_major_key",nullable = false,length = 60)
    private String dep_major_key;//科室主治关键词
    @Column(name="dep_doctor",nullable = false,length = 100)
    private String dep_doctor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getDep_introduce() {
        return dep_introduce;
    }

    public void setDep_introduce(String dep_introduce) {
        this.dep_introduce = dep_introduce;
    }

    public String getDep_major_key() {
        return dep_major_key;
    }

    public void setDep_major_key(String dep_major_key) {
        this.dep_major_key = dep_major_key;
    }

    public String getDep_doctor() {
        return dep_doctor;
    }

    public void setDep_doctor(String dep_doctor) {
        this.dep_doctor = dep_doctor;
    }

    @Override
    public String toString() {
        return "DoctorDepartment{" +
                "dep_name='" + dep_name + '\'' +
                ", dep_introduce='" + dep_introduce + '\'' +
                '}';
    }
}
