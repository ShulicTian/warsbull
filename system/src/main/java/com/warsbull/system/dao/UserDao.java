package com.warsbull.system.dao;

import com.warsbull.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 系统用户持久层
 *
 * @author ShulicTian
 * @date 2020/01/15
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    @Query(value = "SELECT u FROM User u WHERE u.loginName = :userName AND u.delFlag = 0")
    User getByLoginName(@Param("userName") String userName);
}
