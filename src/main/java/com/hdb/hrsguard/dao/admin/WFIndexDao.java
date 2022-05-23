package com.hdb.hrsguard.dao.admin;

import com.hdb.hrsguard.entity.admin.WFIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author hollis
 */
@Repository
public interface WFIndexDao extends JpaRepository<WFIndex, Long> {
    /**
     *查找所有的索引
     * @return
     */
    @Query(value = "select * from hg_wfindex",nativeQuery = true)
    List<WFIndex> findAll();
    @Query(value = "select * from hg_wfindex where id=:id",nativeQuery = true)
    WFIndex findbyId(@Param("id")Long id);

    /**
     * 通过部门id删选医生
     * @param doc_dep_id
     * @return
     */
    @Query(value = "select * from hg_wfindex where doc_dep_id=:doc_dep_id",nativeQuery = true)
    List<WFIndex> findByDoc_dep_id(@Param("doc_dep_id")Long doc_dep_id);
}
