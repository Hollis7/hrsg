package com.hdb.hrsguard.dao.admin;

import com.hdb.hrsguard.entity.admin.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 医院部门信息处理层
 * @Author hollis
 */
@Repository
public interface DepartmentDao extends JpaRepository<Department,Long> {
    /**
     * 返回所有部门信息
     * @return
     */
    @Query(value="select *from hg_department order by dep_id desc ",nativeQuery = true)
    List<Department> findAll();

    /**
     * 根据部门名称返回部门信息
     * @param dep_name
     * @return
     */
    @Query(value = "select *from hg_department as hg_dep where dep_name=:dep_name",nativeQuery = true)
    Department findByDepname(@Param("dep_name")String dep_name);

    /**
     * 返回所有的部门主治关键词
     * @return
     */
    @Query(value = "select dep_major_key from hg_department",nativeQuery = true)
    List<String> findAllDepMajorKey();

    /**
     * 通过主治关键词返回部门信息
     * @param dep_major_key
     * @return
     */
    @Query(value = "select *from hg_department where dep_major_key=:dep_major_key",nativeQuery = true)
    Department findByDep_major_key(@Param("dep_major_key")String dep_major_key);

}
