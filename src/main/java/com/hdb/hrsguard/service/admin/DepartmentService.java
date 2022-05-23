package com.hdb.hrsguard.service.admin;

import com.hdb.hrsguard.dao.admin.DepartmentDao;
import com.hdb.hrsguard.entity.admin.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author hollis
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 获取科室所有信息
     * @return
     */
    public List<Department> findAll(){
        return departmentDao.findAll();
    }

    /**
     * 通过部门名称返回部门信息
     * @param dep_name
     * @return
     */
    public Department findByDepname(String dep_name){
        return departmentDao.findByDepname(dep_name);
    }

    /**
     * 返回所有的主治关键词
     * @return
     */
    public List<String> findAllDepMajorKey(){
        return departmentDao.findAllDepMajorKey();
    }

    /**
     * 通过主治关键词返回部门信息
     * @param dep_major_key
     * @return
     */
    public Department findByDep_major_key(String dep_major_key){
        return departmentDao.findByDep_major_key(dep_major_key);
    }
}
