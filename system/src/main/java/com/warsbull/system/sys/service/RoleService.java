package com.warsbull.system.sys.service;

import com.warsbull.system.sys.entity.Role;
import com.warsbull.system.sys.entity.User;

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
