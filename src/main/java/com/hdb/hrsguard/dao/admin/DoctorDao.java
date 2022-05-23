package com.hdb.hrsguard.dao.admin;

import com.hdb.hrsguard.entity.admin.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 医生信息处理层
 */
@Repository
public interface DoctorDao extends JpaRepository<Doctor,Long> {
    /**
     * 根据医生姓名查看医生信息
     * @param doc_name
     * @return
     */
    @Query(value = "select *from hg_doctor where doc_name=:doc_name",nativeQuery = true)
    Doctor findBYDoctorName(@Param("doc_name")String doc_name);

    @Query(value = "select *from hg_doctor where doc_dep_id=:id",nativeQuery = true)
    List<Doctor> findByDocDepId(@Param("id")Long id);
    @Query(value ="select *from hg_doctor",nativeQuery = true)
    List<Doctor> findAllDoctors();
    @Query(value = "select *from hg_doctor where doc_id=:doc_id",nativeQuery = true)
    Doctor findByDoctorId(@Param("doc_id")Long doc_id);


}
