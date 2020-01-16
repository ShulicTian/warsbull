package com.warsbull.system.service;

import com.warsbull.system.entity.Role;
import com.warsbull.system.entity.User;

import java.util.List;

/**
 * 用户角色相关接口
 *
 * @author ShulicTian
 * @date 2020/01/13
 */
public interface RoleService {

    public List<Role> getRoleByUser(User user);

}
