package com.hdb.hrsguard.dao.admin;


import com.hdb.hrsguard.entity.admin.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends JpaRepository<Menu, Long> {
    @Query(value = "select * from hg_menu order by create_time desc",nativeQuery = true)
    List<Menu> findAll();
    @Query("select m from Menu m where m.id = :id")
    Menu find(@Param("id")Long id);

}

