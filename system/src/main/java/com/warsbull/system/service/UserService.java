package com.warsbull.system.service;

import com.warsbull.system.entity.User;

/**
 * 用户相关操作接口
 *
 * @author ShulicTian
 * @date 2020/01/15
 */
public interface UserService {

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    User getUserByLoginName(String userName);

    /**
     * 根据ID获取用户信息
     *
     * @param id
     * @return
     */
    User get(String id);

    /**
     * 保存用户信息
     *
     * @param user
     */
    void save(User user);
}
