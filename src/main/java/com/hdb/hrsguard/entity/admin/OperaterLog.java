package com.hdb.hrsguard.entity.admin;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

//后台操作日志记录表
@Entity
@Table(name="hg_opetater_logs")
@EntityListeners(AuditingEntityListener.class)
public class OperaterLog extends BaseEntity{

    @Column(name="operater",nullable = false,length = 18)
    private String operater;//操作者
    @Column(name = "content",nullable = false,length = 1024)
    private String content;//操作内容

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
