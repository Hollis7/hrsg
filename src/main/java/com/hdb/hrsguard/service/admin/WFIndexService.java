package com.hdb.hrsguard.service.admin;

import com.hdb.hrsguard.dao.admin.WFIndexDao;
import com.hdb.hrsguard.entity.admin.WFIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author hollis
 */
@Service
public class WFIndexService {
    @Autowired
    WFIndexDao wfIndexDao;

    /**
     * 查找所有的索引表
     * @return
     */
    public List<WFIndex> findAll(){
        return wfIndexDao.findAll();
    }

    /**
     * 通过id查找某个索引
     * @param id
     * @return
     */
    public WFIndex findbyId(Long id){
        return wfIndexDao.findbyId(id);
    }

    /**
     * 存储一条索引
     * @param wfIndex
     * @return
     */
    public WFIndex save(WFIndex wfIndex){
        return wfIndexDao.save(wfIndex);
    }

    /**
     * 清空索引
     */
    public void deleteAll(){
        wfIndexDao.deleteAll();
    }

    public List<WFIndex> findByDoc_dep_id(Long dec_dep_id){
        return wfIndexDao.findByDoc_dep_id(dec_dep_id);
    }

}
