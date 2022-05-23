package com.hdb.hrsguard.service.admin;


import com.hdb.hrsguard.dao.admin.DoctorDao;
import com.hdb.hrsguard.entity.admin.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DoctorService {
    @Autowired
    private DoctorDao doctorDao;

    /**
     * 通过医生名字查找医生
     * @param doc_name
     * @return
     */
    public Doctor findBYDoctorName(String doc_name){
        return doctorDao.findBYDoctorName(doc_name);
    }

    /**
     * 通过部门的id查找所有医生
     * @param dep_id
     * @return
     */
    public List<Doctor> findByDocDepId(Long dep_id){
        return  doctorDao.findByDocDepId(dep_id);
    }

    /**
     * 找到所有的医生
     * @return
     */
    public List<Doctor> findAllDoctors(){
        return doctorDao.findAllDoctors();
    }

    /**
     * 通过医生id查找医生
     * @param doc_id
     * @return
     */
    public Doctor findByDoctorId(Long doc_id){
        return doctorDao.findByDoctorId(doc_id);
    }


}
