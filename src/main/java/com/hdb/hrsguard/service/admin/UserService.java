package com.hdb.hrsguard.service.admin;


import com.hdb.hrsguard.dao.admin.UserDao;
import com.hdb.hrsguard.entity.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 用户管理service
 * @author Administrator
 *
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    public User find(Long id){
        return userDao.find(id);
    }

    /**
     * 按照用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }

    /**
     * 用户添加/编辑操作
     * @param user
     * @return
     */
    public User save(User user){
        return userDao.save(user);
    }

    /**
     * 按照用户id删除
     * @param id
     */
    public void delete(Long id){
        userDao.deleteById(id);
    }

    /**
     * 返回用户总数
     * @return
     */
    public long total(){
        return userDao.count();
    }
}
