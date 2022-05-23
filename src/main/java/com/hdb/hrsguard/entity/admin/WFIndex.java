package com.hdb.hrsguard.entity.admin;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * @Description 关键词密文-文件列表 索引表
 * @Author hollis
 */
@Entity
@Table(name="hg_wfindex")
@EntityListeners(AuditingEntityListener.class)
public class WFIndex extends BaseEntity{
    private static final long serialVersionUID = 1L;
    @Column(name="wf_w",nullable = false,length = 512)
    private byte[] w;
    @Column(name = "wf_w0",nullable = false,length = 512)
    private byte[]w0;
    @Column(name = "wf_u_gate",nullable = false,length = 512)
    private byte[] u_gate;
    @Column(name = "file_list",columnDefinition = "text")
    private String file_list;
    @Column(name = "w_len",nullable = false)
    private int w_len;
    @Column(name = "w0_len",nullable = false)
    private int w0_len;
    @Column(name = "u_gate_len",nullable = false)
    private int u_gate_len;
    @Column(name="doc_dep_id",nullable = false)
    private Long doc_dep_id;
    @Column(name="w_group",nullable = false)
    private int w_group;
    @Column(name = "w0_group",nullable = false)
    private int w0_group;
    @Column(name = "u_gate_group",nullable = false)
    private int u_gate_group;

    public byte[] getW() {
        return w;
    }

    public void setW(byte[] w) {
        this.w = w;
    }

    public byte[] getW0() {
        return w0;
    }

    public void setW0(byte[] w0) {
        this.w0 = w0;
    }

    public byte[] getU_gate() {
        return u_gate;
    }

    public void setU_gate(byte[] u_gate) {
        this.u_gate = u_gate;
    }

    public String getFile_list() {
        return file_list;
    }

    public void setFile_list(String file_list) {
        this.file_list = file_list;
    }

    public int getW_len() {
        return w_len;
    }

    public void setW_len(int w_len) {
        this.w_len = w_len;
    }

    public int getW0_len() {
        return w0_len;
    }

    public void setW0_len(int w0_len) {
        this.w0_len = w0_len;
    }

    public int getU_gate_len() {
        return u_gate_len;
    }

    public void setU_gate_len(int u_gate_len) {
        this.u_gate_len = u_gate_len;
    }

    public Long getDoc_dep_id() {
        return doc_dep_id;
    }

    public void setDoc_dep_id(Long doc_dep_id) {
        this.doc_dep_id = doc_dep_id;
    }

    public int getW_group() {
        return w_group;
    }

    public void setW_group(int w_group) {
        this.w_group = w_group;
    }

    public int getW0_group() {
        return w0_group;
    }

    public void setW0_group(int w0_group) {
        this.w0_group = w0_group;
    }

    public int getU_gate_group() {
        return u_gate_group;
    }

    public void setU_gate_group(int u_gate_group) {
        this.u_gate_group = u_gate_group;
    }
}
